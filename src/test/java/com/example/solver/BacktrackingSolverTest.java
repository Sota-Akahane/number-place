package com.example.solver;

import com.example.domain.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BacktrackingSolverTest {
    @Test
    void solve_simple_sudoku() {
        Board b = Board.fromString("530070000" +
                "600195000" +
                "098000060" +
                "800060003" +
                "400803001" +
                "700020006" +
                "060000280" +
                "000419005" +
                "000080079"
        );

        BacktrackingSolver solver = new BacktrackingSolver(b);
        assertTrue(solver.solve());
        assertTrue(b.isValid());
    }
}
