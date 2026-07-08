package com.example.domain;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CandidateCalculatorTest {

    @Test
    void 空のBoardのマスは1から9全てが候補() {
        Board board = new Board();
        Cell cell = board.getCells()[0][0];

        Set<Integer> candidates = new CandidateCalculator(board).getCandidates(cell);

        assertEquals(Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9), candidates);
    }

    @Test
    void 同じ行に5があれば5は候補から外れる() {
        Board board = new Board();
        board.place(0, 5, 5);
        Cell cell = board.getCells()[0][0];

        Set<Integer> candidates = new CandidateCalculator(board).getCandidates(cell);

        assertFalse(candidates.contains(5));
    }

    @Test
    void 同じ列に5があれば5は候補から外れる() {
        Board board = new Board();
        board.place(5, 0, 5);
        Cell cell = board.getCells()[0][0];

        Set<Integer> candidates = new CandidateCalculator(board).getCandidates(cell);

        assertFalse(candidates.contains(5));
    }

    @Test
    void 同じブロックに5があれば5は候補から外れる() {
        Board board = new Board();
        board.place(1, 1, 5);
        Cell cell = board.getCells()[0][0];

        Set<Integer> candidates = new CandidateCalculator(board).getCandidates(cell);

        assertFalse(candidates.contains(5));
    }

    @Test
    void 既に埋まっているマスに対しては空集合を返す() {
        Board board = new Board();
        board.place(0, 0, 5);
        Cell cell = board.getCells()[0][0];

        Set<Integer> candidates = new CandidateCalculator(board).getCandidates(cell);

        assertTrue(candidates.isEmpty());
    }
}
