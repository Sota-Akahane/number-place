package com.example.service;

import com.example.domain.Board;
import com.example.generator.PuzzleGenerator;
import com.example.solver.LogicalSolver;
import com.example.technique.HiddenSingle;
import com.example.technique.NakedSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class NumberPlaceService {
    private final PuzzleGenerator puzzleGenerator;
    private final LogicalSolver logicalSolver;

    public NumberPlaceService(PuzzleGenerator puzzleGenerator, LogicalSolver logicalSolver) {
        this.puzzleGenerator = puzzleGenerator;
        this.logicalSolver = new LogicalSolver(List.of(
                new NakedSingle(),
                new HiddenSingle()
        ));
    }

    public Board generatePuzzle() {
        return puzzleGenerator.generate();
    }

}
