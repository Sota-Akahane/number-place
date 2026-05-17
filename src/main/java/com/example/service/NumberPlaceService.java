package com.example.service;

import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.generator.PuzzleGenerator;
import com.example.solver.HintFinder;
import com.example.technique.TechniqueFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        HintFinder hintFinder = new HintFinder(TechniqueFactory.createAll());
        return hintFinder.findNextHint(board);
    }
}
