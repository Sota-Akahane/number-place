package com.example.generator;

import com.example.domain.Board;
import com.example.domain.BoardValidator;
import com.example.domain.Cell;
import com.example.technique.TechniqueFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PuzzleGeneratorTest {

    @Test
    void generateは有効な盤面を返す() {
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(
                new FullGridGenerator(),
                new PuzzleValidator(TechniqueFactory.createAll())
        );

        Board puzzle = puzzleGenerator.generate();

        // 1. 空マスが存在する（問題として成立する）
        assertFalse(puzzle.getEmptyCells().isEmpty(), "穴が空いている");
        assertFalse(puzzle.getEmptyCells().size() == 81, "完全に空ではない");

        // 2. 行・列・ブロックに重複がない（盤面として有効である）
        assertTrue(new BoardValidator(puzzle).isValid(), "重複のない有効な盤面");

        // 3. given フラグが正しく設定されている
        long givenCount = puzzle.getFilledCells().stream()
                .filter(Cell::isGiven)
                .count();
        assertEquals(puzzle.getFilledCells().size(), givenCount, "埋まっているマスは全て given");
    }
}
