package com.example.solver;

import com.example.domain.Action;
import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.technique.Technique;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LogicalSolver {
    private final List<Technique> techniques;
    private final List<Hint> history = new ArrayList<>();
    private int maxDifficulty = 0;
    private Status status = Status.SOLVED;

    public LogicalSolver(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public Optional<Hint> apply(Board board) {
        for (Technique technique : techniques) {
            Optional<Hint> hint = technique.find(board);
            if (hint.isPresent()) {
                Action action = hint.get().action();
                board.place(action.cell(), action.number());
                history.add(hint.get());

                int difficulty = hint.get().techniqueType().getDifficulty();
                maxDifficulty = Math.max(maxDifficulty, difficulty);
                return hint;
            }
        }
        return Optional.empty();
    }

    public List<Hint> history() {
        return List.copyOf(history);
    }

    public void solveLogically(Board board) {
        while (true) {
            Optional<Hint> hint = apply(board);

            if (hint.isEmpty()) {
                if (!board.getEmptyCells().isEmpty()) {
                    status = Status.STUCK;
                }
                break;
            }
        }
    }

    public DifficultyLabel difficultyLabel() {
        if (maxDifficulty <= 1) {
            return DifficultyLabel.EASY;
        }
        if (maxDifficulty == 2) {
            return DifficultyLabel.MEDIUM;
        }
        return DifficultyLabel.HARD;
    }

    public SolveSummary summary() {
        return new SolveSummary(
                status,
                difficultyLabel(),
                history.stream().map(Hint::techniqueType).collect(Collectors.toSet()),
                history().size()
        );
    }

    public Optional<Hint> nextHint(Board board) {
        for (Technique technique : techniques) {
            Optional<Hint> hint = technique.find(board);
            if (hint.isPresent()) {
                return hint;
            }
        }
        return Optional.empty();
    }
}
