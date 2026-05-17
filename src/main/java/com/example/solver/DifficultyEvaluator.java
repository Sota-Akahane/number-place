package com.example.solver;

import com.example.domain.Hint;

import java.util.List;

/**
 * 履歴から問題の難易度を評価するクラス.
 * TODO: 現状は最大難易度だけで評価。ステップ数など、他の指標も加味することを検討。
 */
public class DifficultyEvaluator {
    public DifficultyLabel evaluate(List<Hint> history) {
        int max = history.stream()
                .mapToInt(h -> h.techniqueType().getDifficulty())
                .max()
                .orElse(0);

        if (max <= 1) {
            return DifficultyLabel.EASY;
        }
        if (max == 2) {
            return DifficultyLabel.MEDIUM;
        }
        return DifficultyLabel.HARD;
    }
}
