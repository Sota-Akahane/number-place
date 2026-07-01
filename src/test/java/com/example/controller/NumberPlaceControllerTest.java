package com.example.controller;

import com.example.domain.Action;
import com.example.domain.Board;
import com.example.domain.Cell;
import com.example.domain.Hint;
import com.example.service.NumberPlaceService;
import com.example.technique.TechniqueType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NumberPlaceController.class)
public class NumberPlaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NumberPlaceService service;

    @Test
    void GETルートは問題を生成してindexテンプレートを返す() throws Exception {
        when(service.generatePuzzle()).thenReturn(new Board());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("puzzle"));
    }

    @Test
    void POST_hint_はヒントが見つかればモデルに渡す() throws Exception {
        String validBoard = "0".repeat(81);
        Hint stubHint = stubHint();
        when(service.getHint(any(Board.class))).thenReturn(Optional.of(stubHint));

        mockMvc.perform(post("/hint").param("board", validBoard))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("hint", stubHint));
    }

    @Test
    void POST_hint_は不正な盤面ならルートにリダイレクトする() throws Exception {
        mockMvc.perform(post("/hint").param("board", "invalid"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    /** テスト用 Hint スタブを生成する */
    private Hint stubHint() {
        Cell cell = new Cell(0, 0, 0);
        return new Hint(
                TechniqueType.NAKED_SINGLE,
                List.of(cell),
                new Action(cell, 9),
                "stub"
        );
    }
}
