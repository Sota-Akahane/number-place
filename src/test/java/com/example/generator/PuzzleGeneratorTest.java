package com.example.generator;

import com.example.domain.Board;
import com.example.technique.HiddenSingle;
import com.example.technique.NakedSingle;
import com.example.technique.Technique;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PuzzleGeneratorTest {

    @Test
    void generate_puzzle() {
        List<Technique> techniques = List.of(
                new NakedSingle(),
                new HiddenSingle()
        );
        PuzzleGenerator puzzleGenerator = new PuzzleGenerator(
                new FullGridGenerator(),
                new PuzzleValidator(techniques)
        );

        Board puzzle = puzzleGenerator.generate();

        System.out.println(puzzle);
    }
}
