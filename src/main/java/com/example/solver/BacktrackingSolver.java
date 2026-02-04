package com.example.solver;

import com.example.domain.Board;
import com.example.domain.Cell;

import java.util.Optional;

/**
 * バックトラック法を用いて盤面を解くSolver.
 */
public class BacktrackingSolver {
    /** 盤面 */
    private final Board board;

    /** コンストラクタ */
    public BacktrackingSolver(Board board) {
        this.board = board;
    }

    /**
     * バックトラック法で盤面を解く.
     */
    public boolean solve() {
        Optional<Cell> emptyCell = board.findEmptyCell();
        if (emptyCell.isEmpty()) {
            return true; // 完成！
        }

        Cell cell = emptyCell.get();

        for (int n = 1; n <= 9; n++) {
            board.place(cell, n);

            if (board.isValid(cell)) {
                if (solve()) {
                    return true;
                }
            }

            board.clear(cell);
        }

        return false;
    }
}
