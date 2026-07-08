package com.example.domain;

import java.util.HashSet;
import java.util.Set;

public class CandidateCalculator {
    private final Board board;

    public CandidateCalculator(Board board) {
        this.board = board;
    }

    /**
     * 指定マスの候補数字を取得する.
     * 既に埋まっているマスの場合は空集合を返す。
     */
    public Set<Integer> getCandidates(Cell cell) {
        if (cell.getNumber() != 0) {
            return Set.of(); // 既に埋まっている
        }

        Set<Integer> used = new HashSet<>();
        for (Cell c : board.getRow(cell.getRow())) {
            used.add(c.getNumber());
        }
        for (Cell c : board.getColumn(cell.getCol())) {
            used.add(c.getNumber());
        }
        for (Cell c : board.getBlock(cell.getRow(), cell.getCol())) {
            used.add(c.getNumber());
        }

        Set<Integer> candidates = new HashSet<>();
        for (int n = 1; n <= 9; n++) {
            if (!used.contains(n)) {
                candidates.add(n);
            }
        }

        return candidates;
    }
}
