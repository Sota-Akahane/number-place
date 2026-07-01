package com.example.solver;

import com.example.domain.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UniquenessCheckerTest {

    @Test
    void 完全に解けた盤面は解が一意() {
        // 有効な完成盤面
        String solution = "534678912"
                + "672195348"
                + "198342567"
                + "859761423"
                + "426853791"
                + "713924856"
                + "961537284"
                + "287419635"
                + "345286179";
        Board board = Board.fromString(solution);

        assertTrue(new UniquenessChecker(board).hasUniqueSolution());
    }

    @Test
    void 空盤面は解が複数あるため一意ではない() {
        Board board = new Board();

        assertFalse(new UniquenessChecker(board).hasUniqueSolution());
    }

    @Test
    void コンストラクタに渡したBoardは変更されない() {
        Board original = new Board();
        original.place(0, 0, 5);

        new UniquenessChecker(original).hasUniqueSolution();

        assertEquals(5, original.getCellNumber(0, 0));
        assertEquals(0, original.getCellNumber(0, 1));
    }
}
