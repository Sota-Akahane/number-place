package com.example.solver;

import com.example.domain.*;
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
        CandidateState candidateState = new CandidateState(board);
        return hintFinder.findNextHint(board, candidateState);
    }

    /**
     * 1ステップだけ盤面を進める.
     */
    public Optional<Hint> step(Board board, CandidateState candidateState) {
        Optional<Hint> hint = hintFinder.findNextHint(board, candidateState);
        if (hint.isEmpty()) {
            return Optional.empty();
        }

        applyAction(board, candidateState, hint.get().action());
        history.add(hint.get());
        return hint;
    }

    /**
     * 問題を最後まで論理的に解く.
     * 呼び出し側の盤面は書き換えず、内部でコピーを取って作業する。
     */
    public void solveLogically(Board board) {
        Board working = board.copy();
        CandidateState candidateState = new CandidateState(board);
        while (true) {
            Optional<Hint> hint = step(working, candidateState);
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

    /**
     * Action を種類ごとに適用する.
     * - PlaceAction(数字確定系の一手): 盤面を書き換え、候補数字の状態を再構築
     * - RemoveCandidateAction(候補数字削除系の一手): 該当マスの候補数字だけを更新
     */
    private void applyAction(Board board, CandidateState candidateState, Action action) {
        switch (action) {
            case PlaceAction p -> {
                board.place(p.cell(), p.number());
                candidateState.rebuild(board);
            }
            case RemoveCandidateAction r -> {
                candidateState.removeCandidate(r.cell(), r.number());
            }
        }
    }
}
