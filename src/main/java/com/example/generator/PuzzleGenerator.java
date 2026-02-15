package com.example.generator;

import com.example.domain.Board;
import com.example.domain.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleGenerator {
    private final FullGridGenerator fullGridGenerator;
    private final PuzzleValidator puzzleValidator;

    public PuzzleGenerator(FullGridGenerator fullGridGenerator, PuzzleValidator puzzleValidator) {
        this.fullGridGenerator = fullGridGenerator;
        this.puzzleValidator = puzzleValidator;
    }

    public Board generate() {
        Board solution = fullGridGenerator.generate();
        Board puzzle = solution.copy();

        digHoles(puzzle);

        return puzzle;
    }

    private void digHoles(Board puzzle) {
        List<Cell> cells = new ArrayList<>(puzzle.getFilledCells());
        Collections.shuffle(cells);

        for (Cell cell : cells) {
            int backup = puzzle.getCellNumber(cell);
            puzzle.clear(cell);

            if (!puzzleValidator.isValid(puzzle)) {
                puzzle.place(cell, backup);
            }
        }
    }
}
