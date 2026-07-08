package com.example.solver;

import com.example.domain.Board;
import com.example.generator.FullGridGenerator;
import com.example.generator.PuzzleGenerator;
import com.example.generator.PuzzleValidator;
import com.example.technique.TechniqueFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicalSolverTest {

    @Test
    void solveLogicallyは生成された問題を解き切る() {
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(
                new FullGridGenerator(),
                new PuzzleValidator(TechniqueFactory.createAll())
        );
        Board puzzle = puzzleGenerator.generate();

        LogicalSolver logicalSolver = new LogicalSolver(TechniqueFactory.createAll());
        logicalSolver.solveLogically(puzzle);

        // PuzzleValidator が有効と判断した盤面なので、SOLVED になるはず
        assertEquals(Status.SOLVED, logicalSolver.summary().status());

        // 履歴が記録されている
        assertFalse(logicalSolver.history().isEmpty());
    }

    @Test
    void solveLogicallyは呼び出し元のBoardを変更しない() {
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(
                new FullGridGenerator(),
                new PuzzleValidator(TechniqueFactory.createAll())
        );
        Board puzzle = puzzleGenerator.generate();
        int originalEmpty = puzzle.getEmptyCells().size();

        LogicalSolver logicalSolver = new LogicalSolver(TechniqueFactory.createAll());
        logicalSolver.solveLogically(puzzle);

        assertEquals(originalEmpty, puzzle.getEmptyCells().size(), "呼び出し元の盤面は変更されない");
    }
}
