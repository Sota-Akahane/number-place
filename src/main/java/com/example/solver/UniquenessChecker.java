package com.example.solver;

import com.example.domain.Board;
import com.example.domain.Cell;

import java.util.Optional;

/**
 * 解の一意性をチェックするクラス.
 */
public class UniquenessChecker {
    /** 盤面 */
    private final Board board;
    /** 解の個数 */
    private int solutionCount = 0;

    /** コンストラクタ */
    public UniquenessChecker(Board board) {
        // 探索中に place/clear で盤面を書き換えるため、コピーを保持する。
        this.board = board.copy();
    }

    /**
     * 解が1つかどうかをチェックする.
     *
     * @return 解が1個:true、0個または2個以上:false
     */
    public boolean hasUniqueSolution() {
        search();
        return solutionCount == 1;
    }

    /**
     * 解を探す.
     */
    private void search() {
        if (solutionCount >= 2) {
            return;
        }

        Optional<Cell> emptyCell = board.findEmptyCell();

        // 解が1つ見つかった
        if (emptyCell.isEmpty()) {
            solutionCount++;
            return;
        }

        Cell cell = emptyCell.get();

        for (int n = 1; n <= 9; n++) {
            board.place(cell, n);

            if (board.isValid(cell)) {
                search();
            }

            board.clear(cell);

            if (solutionCount > 2) {
                return;
            }
        }
    }
}
