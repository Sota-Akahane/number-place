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

@Controller
public class NumberPlaceController {
    private final NumberPlaceService numberPlaceService;

    public NumberPlaceController(NumberPlaceService numberPlaceService) {
        this.numberPlaceService = numberPlaceService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Board puzzle = numberPlaceService.generatePuzzle();
        model.addAttribute("puzzle", puzzle);
        return "index";
    }

    @PostMapping("/hint")
    public String hint(String board, Model model) {
        Board puzzle = Board.fromString(board);

        Optional<Hint> hint = numberPlaceService.getHint(puzzle);

        model.addAttribute("hint", hint.orElse(null));
        model.addAttribute("puzzle", puzzle);

        return "index";
    }
}
