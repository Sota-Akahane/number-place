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
 * 問題を人間と同様の論理で解く Solver.
 */
public class LogicalSolver {
    /** ヒントを探すクラス */
    private final HintFinder hintFinder;
    /** 難易度を評価するクラス */
    private final DifficultyEvaluator difficultyEvaluator = new DifficultyEvaluator();
    /** 解いた履歴 */
    private final List<Hint> history = new ArrayList<>();
    /** ステータス */
    private Status status = Status.SOLVED;

    /** コンストラクタ */
    public LogicalSolver(List<Technique> techniques) {
        this.hintFinder = new HintFinder(techniques);
    }

    /**
     * 盤面を変えずに次のヒントを返す.
     */
    public Optional<Hint> nextHint(Board board) {
        return hintFinder.findNextHint(board);
    }

    /**
     * 1ステップだけ盤面を進める.
     */
    public Optional<Hint> step(Board board) {
        Optional<Hint> hint = hintFinder.findNextHint(board);
        if (hint.isPresent()) {
            Action action = hint.get().action();
            board.place(action.cell(), action.number());
            history.add(hint.get());
        }
        return hint;
    }

    /**
     * 問題を最後まで論理的に解く.
     * 呼び出し側の盤面は書き換えず、内部でコピーを取って作業する。
     */
    public void solveLogically(Board board) {
        Board working = board.copy();
        while (true) {
            Optional<Hint> hint = step(working);

            if (hint.isEmpty()) {
                if (!working.getEmptyCells().isEmpty()) {
                    status = Status.STUCK;
                }
                break;
            }
        }
    }

    public List<Hint> history() {
        return List.copyOf(history);
    }

    public SolveSummary summary() {
        return new SolveSummary(
                status,
                difficultyEvaluator.evaluate(history),
                history.stream().map(Hint::techniqueType).collect(Collectors.toSet()),
                history().size()
        );
    }
}
