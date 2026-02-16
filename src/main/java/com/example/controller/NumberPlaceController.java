package com.example.controller;

import com.example.domain.Board;
import com.example.domain.Hint;
import com.example.generator.PuzzleGenerator;
import com.example.service.NumberPlaceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * ナンプレに関する処理を制御するコントローラ.
 */
@Controller
public class NumberPlaceController {
    private final NumberPlaceService numberPlaceService;

    /** コンストラクタ */
    public NumberPlaceController(NumberPlaceService numberPlaceService) {
        this.numberPlaceService = numberPlaceService;
    }

    /**
     * 初期表示.
     * 問題を生成して表示する。
     */
    @GetMapping("/")
    public String index(Model model) {
        Board puzzle = numberPlaceService.generatePuzzle();
        model.addAttribute("puzzle", puzzle);
        return "index";
    }

    /**
     * ヒントを表示する.
     */
    @PostMapping("/hint")
    public String hint(String board, Model model) {
        Board puzzle = Board.fromString(board);

        Optional<Hint> hint = numberPlaceService.getHint(puzzle);

        model.addAttribute("hint", hint.orElse(null));
        model.addAttribute("puzzle", puzzle);

        return "index";
    }
}
