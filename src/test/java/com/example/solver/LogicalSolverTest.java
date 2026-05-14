package com.example.solver;

import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.generator.FullGridGenerator;
import com.example.generator.PuzzleGenerator;
import com.example.generator.PuzzleValidator;
import com.example.technique.HiddenSingle;
import com.example.technique.NakedSingle;
import com.example.technique.Technique;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LogicalSolverTest {

    @Test
    void solve_logically_test() {
        List<Technique> techniques = List.of(
                new NakedSingle(),
                new HiddenSingle()
        );
        LogicalSolver logicalSolver = new LogicalSolver(techniques);
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(
                new FullGridGenerator(),
                new PuzzleValidator(techniques)
        );

        Board puzzle = puzzleGenerator.generate();
        logicalSolver.solveLogically(puzzle.copy());

        System.out.println(puzzle);
        System.out.println("=====================");
        printHistory(logicalSolver.history());
    }

    public static void printHistory(List<Hint> history) {
        int i = 1;
        for (Hint h : history) {
            System.out.printf(
                    "%2d. %-15s (%d,%d) = %d%n",
                    i++,
                    h.techniqueType().name(),
                    h.action().cell().getRow(),
                    h.action().cell().getCol(),
                    h.action().number()
            );
        }
    }

}
