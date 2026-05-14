package com.example.generator;

import com.example.domain.Board;
import com.example.technique.TechniqueFactory;
import org.junit.jupiter.api.Test;

public class PuzzleGeneratorTest {

    @Test
    void generate_puzzle() {
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(
                new FullGridGenerator(),
                new PuzzleValidator(TechniqueFactory.createAll())
        );

        Board puzzle = puzzleGenerator.generate();

        System.out.println(puzzle);
    }
}
