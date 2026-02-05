package com.example.solver;

import com.example.domain.Board;
import com.example.domain.Cell;

import java.util.Optional;

public class UniquenessChecker {
    private final Board board;
    private int solutionCount = 0;

    public UniquenessChecker(Board board) {
        this.board = board;
    }

    public boolean hasUniqueSolution() {
        search();
        return solutionCount == 1;
    }

    public void search() {
        if (solutionCount > 2) {
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
