package com.example.solver;

import com.example.domain.*;
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
     * テクニックを順に試していき、使えるものが見つかったらそのテクニックをヒントとして返す.
     */
    public Optional<Hint> apply(Board board, CandidateState candidateState) {
        for (Technique technique : techniques) {
            Optional<Hint> hint = technique.find(board, candidateState);
            if (hint.isPresent()) {
                Action action = hint.get().action();
                applyAction(board, candidateState, action);
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
            CandidateState candidateState = new CandidateState(board);
            Optional<Hint> hint = apply(board, candidateState);

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
            CandidateState candidateState = new CandidateState(board);
            Optional<Hint> hint = technique.find(board, candidateState);
            if (hint.isPresent()) {
                return hint;
            }
        }
        return Optional.empty();
    }

    /**
     * 次の一手を適用する.
     * 数字確定系のテクニックの場合は、適用後に CandidateState を再構築する。
     */
    private void applyAction(Board board, CandidateState candidateState, Action action) {
        if (action instanceof PlaceAction a) {
            board.place(a.cell(), a.number());
            candidateState.rebuild(board);
        } else if (action instanceof RemoveCandidateAction a) {
            candidateState.removeCandidate(a.cell(), a.number());
        }
    }
}
