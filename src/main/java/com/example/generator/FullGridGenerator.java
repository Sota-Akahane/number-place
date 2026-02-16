package com.example.generator;

import com.example.domain.Board;
import com.example.domain.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * 完全解盤面を生成するクラス.
 */
public class FullGridGenerator {

    /**
     * 完全解盤面を生成する.
     */
    public Board generate() {
        Board board = new Board();
        fill(board);
        return board;
    }

    /**
     * マスを埋める.
     */
    public boolean fill(Board board) {
        Optional<Cell> cellOpt = board.findEmptyCell().stream().findFirst();

        if (cellOpt.isEmpty()) {
            return true;
        }

        Cell cell = cellOpt.get();
        List<Integer> numbers = shuffledNumbers();

        for (int n : numbers) {
            board.place(cell, n);
            if (board.isValid(cell)) {
                if (fill(board)) {
                    return true;
                }

            }
            board.clear(cell);
        }
        return false;
    }

    /**
     * 1~9の数字をランダム順にして返す.
     */
    private List<Integer> shuffledNumbers() {
        List<Integer> numbers = new ArrayList<>(IntStream.rangeClosed(1, 9).boxed().toList());
        Collections.shuffle(numbers);
        return numbers;
    }
}
