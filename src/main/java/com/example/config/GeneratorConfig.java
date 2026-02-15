package com.example.config;

import com.example.generator.FullGridGenerator;
import com.example.generator.PuzzleGenerator;
import com.example.generator.PuzzleValidator;
import com.example.technique.HiddenSingle;
import com.example.technique.NakedSingle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GeneratorConfig {

    @Bean
    public FullGridGenerator fullGridGenerator() {
        return new FullGridGenerator();
    }

    @Bean
    public PuzzleValidator puzzleValidator() {
        return new PuzzleValidator(List.of(new NakedSingle(), new HiddenSingle()));
    }

    @Bean
    public PuzzleGenerator puzzleGenerator(FullGridGenerator fullGridGenerator, PuzzleValidator puzzleValidator) {
        return new PuzzleGenerator(fullGridGenerator, puzzleValidator);
    }
}
