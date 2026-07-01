package com.example.solver;

import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.technique.HiddenSingle;
import com.example.technique.NakedSingle;
import com.example.technique.TechniqueType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class HintFinderTest {

    @Test
    void テクニックリストが空の場合は空を返す() {
        Board board = new Board();
        HintFinder finder = new HintFinder(List.of());

        assertFalse(finder.findNextHint(board).isPresent());
    }

    @Test
    void テクニックは渡された順で試される() {
        // (0, 0) の候補数字が 9 のみなので、Naked Single が見つかる盤面
        Board board = new Board();
        for (int col = 1; col < 9; col++) {
            board.place(0, col, col);
        }

        HintFinder finder = new HintFinder(List.of(new NakedSingle(), new HiddenSingle()));
        Optional<Hint> hint = finder.findNextHint(board);

        assertTrue(hint.isPresent());
        assertEquals(TechniqueType.NAKED_SINGLE, hint.get().techniqueType());
    }
}
