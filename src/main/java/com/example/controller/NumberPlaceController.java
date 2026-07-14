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
     * トップ画面.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("streak", 12);
        model.addAttribute("averageTime", "09:12");
        model.addAttribute("solvedCount", 148);
        return "home";
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
