package com.example.technique;

import com.example.domain.Board;
import com.example.domain.Hint;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class NakedSingleTest {

    @Test
    void 候補が1つしかないマスをヒントとして返す() {
        // (0, 0) の候補数字が 9 のみになるような盤面を作る
        Board board = new Board();
        for (int col  = 1; col < 9; col++) {
            board.place(0, col, col);
        }

        Optional<Hint> hint = new NakedSingle().find(board);

        assertTrue(hint.isPresent());
        assertEquals(TechniqueType.NAKED_SINGLE, hint.get().techniqueType());
        assertEquals(0, hint.get().action().cell().getRow());
        assertEquals(0, hint.get().action().cell().getCol());
        assertEquals(9, hint.get().action().number());
    }

    @Test
    void Naked_Singleが無い場合は空を返す() {
        // 空盤面では全マスで 1~9 が候補
        Board board = new Board();

        Optional<Hint> hint = new NakedSingle().find(board);

        assertFalse(hint.isPresent());
    }
}
