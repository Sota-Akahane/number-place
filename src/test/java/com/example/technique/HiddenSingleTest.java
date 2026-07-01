package com.example.technique;

import com.example.domain.Board;
import com.example.domain.Hint;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class HiddenSingleTest {

    @Test
    void 行内で数字を入れられるマスが1つだけのときヒントを返す() {
        // 0行目で 9 が入れられるマスが (0, 0) だけになるような盤面を作る
        Board board = new Board();
        for (int col = 1; col < 9; col++) {
            board.place(8, col, 9);
        }

        Optional<Hint> hint = new HiddenSingle().find(board);

        assertTrue(hint.isPresent());
        assertEquals(TechniqueType.HIDDEN_SINGLE, hint.get().techniqueType());
        assertEquals(0, hint.get().action().cell().getRow());
        assertEquals(0, hint.get().action().cell().getCol());
        assertEquals(9, hint.get().action().number());
    }

    @Test
    void Hidden_Singleが無い場合は空を返す() {
        Board board = new Board();

        Optional<Hint> hint = new HiddenSingle().find(board);

        assertFalse(hint.isPresent());
    }
}
