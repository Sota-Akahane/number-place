package com.example.solver;

import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.generator.FullGridGenerator;
import com.example.generator.PuzzleGenerator;
import com.example.generator.PuzzleValidator;
import com.example.technique.TechniqueFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LogicalSolverTest {

    @Test
    void solve_logically_test() {
        LogicalSolver logicalSolver = new LogicalSolver(TechniqueFactory.createAll());
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(
                new FullGridGenerator(),
                new PuzzleValidator(TechniqueFactory.createAll())
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
