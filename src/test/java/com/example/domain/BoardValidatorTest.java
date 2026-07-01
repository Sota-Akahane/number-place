package com.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardValidatorTest {

    @Test
    void 空のBoardは有効() {
        Board board = new Board();
        assertTrue(new BoardValidator(board).isValid());
    }

    @Test
    void 同じ行に同じ数字があると無効() {
        Board board = new Board();
        board.place(0, 0, 5);
        board.place(0, 5, 5);

        assertFalse(new BoardValidator(board).isValid());
    }

    @Test
    void 同じ列に同じ数字があると無効() {
        Board board = new Board();
        board.place(0, 0, 5);
        board.place(5, 0, 5);

        assertFalse(new BoardValidator(board).isValid());
    }

    @Test
    void 同じブロックに同じ数字があると無効() {
        Board board = new Board();
        board.place(0, 0, 5);
        board.place(1, 1, 5);

        assertFalse(new BoardValidator(board).isValid());
    }

    @Test
    void Cell単位のisValidは対象マスを含むユニットのみを見る() {
        Board board = new Board();
        board.place(0, 0, 5);
        board.place(5, 0, 5); // 1列目に数字の重複あり

        BoardValidator validator = new BoardValidator(board);

        Cell unrelatedCell = board.getCells()[8][8]; // 1列目の重複が関係ない9行9列目のマス
        assertTrue(validator.isValid(unrelatedCell));
    }
}
