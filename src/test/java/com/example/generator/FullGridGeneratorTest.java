package com.example.generator;

import com.example.domain.Board;
import com.example.domain.BoardValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FullGridGeneratorTest {

    @Test
    void generate_full_grid() {
        FullGridGenerator generator = new FullGridGenerator();
        Board board = generator.generate();
        BoardValidator validator = new BoardValidator(board);
        boolean result = validator.isValid();

        System.out.println(board);
        assertTrue(result);
    }
}
