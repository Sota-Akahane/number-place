package com.example.generator;

import com.example.domain.Board;
import com.example.solver.BacktrackingSolver;
import com.example.solver.LogicalSolver;
import com.example.solver.Status;
import com.example.solver.UniquenessChecker;
import com.example.technique.Technique;

import java.util.List;

public class PuzzleValidator {
    private final List<Technique> techniques;

    public PuzzleValidator(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public boolean isValid(Board puzzle) {
        // 1. 論理で解けるか
        LogicalSolver logicalSolver = new LogicalSolver(techniques);
        logicalSolver.solveLogically(puzzle.copy());
        if (logicalSolver.summary().status() == Status.STUCK) {
            return false;
        }

        // 2. 解が一意か
        UniquenessChecker uniquenessChecker = new UniquenessChecker(puzzle);
        return uniquenessChecker.hasUniqueSolution();
    }
}
