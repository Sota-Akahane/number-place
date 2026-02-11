package com.example.generator;

import com.example.domain.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FullGridGeneratorTest {

    @Test
    void generate_full_grid() {
        FullGridGenerator generator = new FullGridGenerator();
        Board board = generator.generate();
        boolean result = board.isValid();

        System.out.println(board);
        assertTrue(result);
    }
}
