package com.example.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    void 初期状態は全マス0() {
        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                assertEquals(0, board.getCellNumber(row, col));
            }
        }
    }

    @Test
    void placeとclearが機能する() {
        Board board = new Board();
        board.place(3, 5, 7);
        assertEquals(7, board.getCellNumber(3, 5));

        board.clear(3, 5);
        assertEquals(0, board.getCellNumber(3, 5));
    }

    @Test
    void fromStringはnullで例外を投げる() {
        assertThrows(IllegalArgumentException.class, () -> Board.fromString(null));
    }

    @Test
    void fromStringは81文字以外で例外を投げる() {
        assertThrows(IllegalArgumentException.class, () -> Board.fromString("123"));
        assertThrows(IllegalArgumentException.class, () -> Board.fromString("0".repeat(82)));
    }

    @Test
    void fromStringは数字以外を含むと例外を投げる() {
        String invalidString = "0".repeat(80) + "a";
        assertThrows(IllegalArgumentException.class, () -> Board.fromString(invalidString));
    }

    @Test
    void fromStringは正しい入力でBoardを生成する() {
        String s = "530070000"
                + "600195000"
                + "098000060"
                + "800060003"
                + "400803001"
                + "700020006"
                + "060000280"
                + "000419005"
                + "000080079";
        Board board = Board.fromString(s);
        assertEquals(5, board.getCellNumber(0, 0));
        assertEquals(3, board.getCellNumber(0, 1));
        assertEquals(0, board.getCellNumber(0, 2));
        assertEquals(9, board.getCellNumber(8, 8));
    }

    @Test
    void copyは独立したインスタンスを返す() {
        Board original = new Board();
        original.place(0, 0, 5);

        Board copy = original.copy();
        copy.place(0, 0, 9);

        assertEquals(5, original.getCellNumber(0, 0));
        assertEquals(9, copy.getCellNumber(0, 0));
    }

    @Test
    void markGivenCellsは埋まっているマスだけをgivenにする() {
        Board board = new Board();
        board.place(0, 0, 5);
        board.markGivenCells();

        assertTrue(board.getCells()[0][0].isGiven());
        assertFalse(board.getCells()[0][1].isGiven());
    }

    @Test
    void getEmptyCellsは空マスだけを返す() {
        Board board = new Board();
        board.place(0, 0, 1);

        assertEquals(80, board.getEmptyCells().size());
        assertFalse(board.getEmptyCells().contains(board.getCells()[0][0]));
    }

    @Test
    void findEmptyCellは最初の空マスを返す() {
        Board board = new Board();
        board.place(0, 0, 1);

        assertTrue(board.findEmptyCell().isPresent());
        assertEquals(board.getCells()[0][1], board.findEmptyCell().get());
    }

    @Test
    void getAllUnitsは27ユニットを返す() {
        Board board = new Board();
        List<Unit> units = board.getAllUnits();

        assertEquals(27, units.size());
        assertEquals(9, units.stream().filter(u -> u.type() == UnitType.ROW).count());
        assertEquals(9, units.stream().filter(u -> u.type() == UnitType.COLUMN).count());
        assertEquals(9, units.stream().filter(u -> u.type() == UnitType.BLOCK).count());
    }
}
