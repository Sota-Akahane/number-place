package com.example.generator;

import com.example.domain.Board;
import com.example.domain.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 問題を生成するクラス.
 */
public class PuzzleGenerator {
    /** 完全解盤面を生成するクラス */
    private final FullGridGenerator fullGridGenerator;
    /** 生成した問題をチェックするクラス */
    private final PuzzleValidator puzzleValidator;

    /** コンストラクタ */
    public PuzzleGenerator(FullGridGenerator fullGridGenerator, PuzzleValidator puzzleValidator) {
        this.fullGridGenerator = fullGridGenerator;
        this.puzzleValidator = puzzleValidator;
    }

    /**
     * 問題を生成する.
     */
    public Board generate() {
        Board solution = fullGridGenerator.generate();
        Board puzzle = solution.copy();

        digHoles(puzzle);

        puzzle.markGivenCells();

        return puzzle;
    }

    /**
     * 完全解盤面からランダムに穴をあける.
     */
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
