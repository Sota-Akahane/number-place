package com.example.service;

import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.generator.PuzzleGenerator;
import com.example.solver.LogicalSolver;
import com.example.technique.HiddenSingle;
import com.example.technique.NakedSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * ナンプレに関連する処理を実行するサービス.
 */
@Service
@Transactional
public class NumberPlaceService {
    private final PuzzleGenerator puzzleGenerator;

    /** コンストラクタ */
    public NumberPlaceService(PuzzleGenerator puzzleGenerator) {
        this.puzzleGenerator = puzzleGenerator;
    }

    /**
     * 問題を生成する.
     */
    public Board generatePuzzle() {
        return puzzleGenerator.generate();
    }

    /**
     * ヒントを取得する.
     */
    public Optional<Hint> getHint(Board board) {
        LogicalSolver logicalSolver = new LogicalSolver(List.of(new HiddenSingle(), new NakedSingle()));
        return logicalSolver.nextHint(board);
    }
}
