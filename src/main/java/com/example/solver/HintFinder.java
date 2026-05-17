package com.example.solver;

import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.technique.Technique;

import java.util.List;
import java.util.Optional;

/**
 * テクニックを順に試して、最初に見つかったヒントを返すクラス.
 */
public class HintFinder {
    private final List<Technique> techniques;

    public HintFinder(List<Technique> techniques) {
        this.techniques = techniques;
    }

    /**
     * 次のヒントを返す.
     */
    public Optional<Hint> findNextHint(Board board) {
        for (Technique technique : techniques) {
            Optional<Hint> hint = technique.find(board);
            if (hint.isPresent()) {
                return hint;
            }
        }
        return Optional.empty();
    }
}
