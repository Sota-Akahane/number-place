package com.example.solver;

import com.example.domain.Action;
import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.technique.Technique;

import java.util.List;
import java.util.Optional;

public class LogicalSolver {
    private final List<Technique> techniques;

    public LogicalSolver(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public Optional<Hint> apply(Board board) {
        for (Technique technique : techniques) {
            Optional<Hint> hint = technique.find(board);
            if (hint.isPresent()) {
                Action action = hint.get().action();
                board.place(action.cell(), action.number());
                return hint;
            }
        }
        return Optional.empty();
    }
}
