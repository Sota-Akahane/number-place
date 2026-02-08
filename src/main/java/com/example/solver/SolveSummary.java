package com.example.solver;

import com.example.technique.Technique;
import com.example.technique.TechniqueType;

import java.util.Set;

public record SolveSummary(
        DifficultyLabel difficultyLabel,
        Set<TechniqueType> useTechniques,
        int steps
) {
}
