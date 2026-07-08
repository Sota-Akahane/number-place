package com.example.config;

import com.example.generator.FullGridGenerator;
import com.example.generator.PuzzleGenerator;
import com.example.generator.PuzzleValidator;
import com.example.technique.TechniqueFactory;
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
        return new PuzzleValidator(TechniqueFactory.createAll());
    }

    @Bean
    public PuzzleGenerator puzzleGenerator(FullGridGenerator fullGridGenerator, PuzzleValidator puzzleValidator) {
        return new PuzzleGenerator(fullGridGenerator, puzzleValidator);
    }
}
