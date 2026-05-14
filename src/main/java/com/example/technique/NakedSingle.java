package com.example.technique;

import com.example.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * テクニック：Naked Single.
 */
public class NakedSingle implements Technique {

    @Override
    public Optional<Hint> find(Board board, CandidateState candidateState) {
        for (Cell cell : board.getEmptyCells()) {
            Set<Integer> candidates = candidateState.getCandidates(cell);
            if (candidates.size() == 1) {
                int number = candidates.iterator().next();
                return Optional.of(
                        new Hint(
                                TechniqueType.NAKED_SINGLE,
                                List.of(cell),
                                new PlaceAction(cell, number),
                                "このマスには" + number + "しか入りません。"
                        )
                );
            }
        }
        return Optional.empty();
    }

    @Override
    public TechniqueType type() {
        return TechniqueType.NAKED_SINGLE;
    }
}
