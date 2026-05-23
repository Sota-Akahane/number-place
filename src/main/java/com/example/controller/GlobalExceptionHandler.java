package com.example.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * アプリケーション全体の例外ハンドラ.
 * 個々のコントローラに try-catch を散らかさず、ここで横断的に処理する。
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * ドメイン層が投げた不正入力例外を捕捉する.
     * トップページにリダイレクトし、エラーメッセージを Flash 属性で表示する。
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", "入力が不正です: " + e.getMessage());
        return "redirect:/";
    }
}
