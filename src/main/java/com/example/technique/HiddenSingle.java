package com.example.technique;

import com.example.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * テクニック：Hidden Single.
 */
public class HiddenSingle implements Technique {

    @Override
    public Optional<Hint> find(Board board, CandidateState candidateState) {
        for (int row = 0; row < 9; row++) {
            for (int number = 1; number <= 9; number++) {
                Optional<Hint> hint = findHiddenSingleInUnit(
                        board,
                        Arrays.asList(board.getRow(row)),
                        number,
                        "この行"
                );
                if (hint.isPresent()) {
                    return hint;
                }
            }
        }

        for (int col = 0; col < 9; col++) {
            for (int number = 1; number <= 9; number++) {
                Optional<Hint> hint = findHiddenSingleInUnit(
                        board,
                        Arrays.asList(board.getColumn(col)),
                        number,
                        "この列"
                );
                if (hint.isPresent()) {
                    return hint;
                }
            }
        }

        for (int startRow = 0; startRow < 9; startRow += 3) {
            for (int startCol = 0; startCol < 9; startCol += 3) {
                for (int number = 1; number <= 9; number++) {
                    Optional<Hint> hint = findHiddenSingleInUnit(
                            board,
                            Arrays.asList(board.getBlock(startRow, startCol)),
                            number,
                            "このブロック"
                    );
                    if (hint.isPresent()) {
                        return hint;
                    }
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
     * 行、列、ブロックの各単位で Hidden Single を見つけるためのヘルパーメソッド.
     */
    private Optional<Hint> findHiddenSingleInUnit(Board board, List<Cell> cells, int number, String unitName) {
        List<Cell> candidates = new ArrayList<>();

        for (Cell cell : cells) {
            if (cell.getNumber() == 0 && board.getCandidates(cell).contains(number)) {
                candidates.add(cell);
            }
        }

        if (candidates.size() == 1) {
            Cell target = candidates.getFirst();
            return Optional.of(
                    new Hint(
                            TechniqueType.HIDDEN_SINGLE,
                            List.of(target),
                            new PlaceAction(target, number),
                            unitName + "では" + number + "はここにしか入りません。"
                    )
            );
        }
        return Optional.empty();
    }
}
