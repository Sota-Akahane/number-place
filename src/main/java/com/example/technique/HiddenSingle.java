package com.example.technique;

import com.example.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * テクニック：Hidden Single.
 */
public class HiddenSingle implements Technique {

    @Override
    public Optional<Hint> find(Board board) {
        for (Unit unit : board.getAllUnits()) {
            for (int number = 1; number <= 9; number++) {
                Optional<Hint> hint = findInUnit(board, unit, number);
                if (hint.isPresent()) {
                    return hint;
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public TechniqueType type() {
        return TechniqueType.HIDDEN_SINGLE;
    }

    /**
     * 1つの unit に対して Hidden Single を探す.
     */
    private Optional<Hint> findInUnit(Board board, Unit unit, int number) {
        List<Cell> candidates = new ArrayList<>();

        for (Cell cell : unit.cells()) {
            if (cell.getNumber() == 0 && board.getCandidates(cell).contains(number)) {
                candidates.add(cell);
            }
        }

        if (candidates.size() != 1) {
            return Optional.empty();
        }

        Cell target = candidates.getFirst();
        return Optional.of(
                new Hint(
                        TechniqueType.HIDDEN_SINGLE,
                        List.of(target),
                        new Action(target, number),
                        getUnitLabel(unit.type()) + "では" + number + "はここにしか入りません。"
                )
        );
    }

    /**
     * ユニット種別に対応する表示ラベルを取得する.
     */
    private String getUnitLabel(UnitType type) {
        return switch (type) {
            case ROW -> "この行";
            case COLUMN -> "この列";
            case BLOCK -> "このブロック";
        };
    }
}
