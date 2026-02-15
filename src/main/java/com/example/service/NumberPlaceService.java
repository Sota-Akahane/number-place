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

    public NumberPlaceService(PuzzleGenerator puzzleGenerator) {
        this.puzzleGenerator = puzzleGenerator;
    }

    public Board generatePuzzle() {
        return puzzleGenerator.generate();
    }

}
