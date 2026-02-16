package com.example.solver;

import com.example.domain.Action;
import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.technique.Technique;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 問題を人間と同様に論理で解く Solver.
 */
public class LogicalSolver {
    /** 使用できるテクニック */
    private final List<Technique> techniques;
    /** 解いた履歴 */
    private final List<Hint> history = new ArrayList<>();
    /** 問題の難易度指標 */
    private int maxDifficulty = 0;
    /** ステータス */
    private Status status = Status.SOLVED;

    /** コンストラクタ */
    public LogicalSolver(List<Technique> techniques) {
        this.techniques = techniques;
    }

    /**
     * テクニックを順に試していき、数字が埋まったらそのテクニックをヒントとして返す.
     *
     */
    public Optional<Hint> apply(Board board) {
        for (Technique technique : techniques) {
            Optional<Hint> hint = technique.find(board);
            if (hint.isPresent()) {
                Action action = hint.get().action();
                board.place(action.cell(), action.number());
                history.add(hint.get());

                int difficulty = hint.get().techniqueType().getDifficulty();
                maxDifficulty = Math.max(maxDifficulty, difficulty);
                return hint;
            }
        }
        return Optional.empty();
    }

    public List<Hint> history() {
        return List.copyOf(history);
    }

    /**
     * 問題を最後まで論理的に解く.
     */
    public void solveLogically(Board board) {
        while (true) {
            Optional<Hint> hint = apply(board);

            if (hint.isEmpty()) {
                if (!board.getEmptyCells().isEmpty()) {
                    status = Status.STUCK;
                }
                break;
            }
        }
    }

    /**
     * 難易度指標から問題の難易度を判定して返す.
     * TODO: 難易度の判定方法を見直す。
     */
    public DifficultyLabel difficultyLabel() {
        if (maxDifficulty <= 1) {
            return DifficultyLabel.EASY;
        }
        if (maxDifficulty == 2) {
            return DifficultyLabel.MEDIUM;
        }
        return DifficultyLabel.HARD;
    }

    public SolveSummary summary() {
        return new SolveSummary(
                status,
                difficultyLabel(),
                history.stream().map(Hint::techniqueType).collect(Collectors.toSet()),
                history().size()
        );
    }

    /**
     * 盤面を変えることなく、次に使用できるテクニックをヒントとして返す.
     */
    public Optional<Hint> nextHint(Board board) {
        for (Technique technique : techniques) {
            Optional<Hint> hint = technique.find(board);
            if (hint.isPresent()) {
                return hint;
            }
        }
        return Optional.empty();
    }
}
