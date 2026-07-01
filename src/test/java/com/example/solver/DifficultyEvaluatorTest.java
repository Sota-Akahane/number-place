package com.example.solver;

import com.example.domain.Action;
import com.example.domain.Cell;
import com.example.domain.Hint;
import com.example.technique.TechniqueType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DifficultyEvaluatorTest {

    private final DifficultyEvaluator evaluator = new DifficultyEvaluator();

    @Test
    void 履歴が空ならEASY() {
        assertEquals(DifficultyLabel.EASY, evaluator.evaluate(List.of()));
    }

    @Test
    void Naked_SingleだけならEASY() {
        Hint hint = generateHint(TechniqueType.NAKED_SINGLE);

        assertEquals(DifficultyLabel.EASY, evaluator.evaluate(List.of(hint)));
    }

    @Test
    void Hidden_Singleを含むとMEDIUM() {
        Hint nakedSingle = generateHint(TechniqueType.NAKED_SINGLE);
        Hint hiddenSingle = generateHint(TechniqueType.HIDDEN_SINGLE);

        assertEquals(DifficultyLabel.MEDIUM, evaluator.evaluate(List.of(nakedSingle, hiddenSingle)));
    }

    /** テスト用 Hint 作成メソッド */
    private Hint generateHint(TechniqueType type) {
        Cell cell = new Cell(0, 0, 0);
        Action action = new Action(cell, 1);
        return new Hint(type, List.of(cell), action, "");
    }
}
