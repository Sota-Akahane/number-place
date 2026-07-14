package com.example.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 全 Controller で共通の Model 属性を注入する.
 */
@ControllerAdvice
public class CommonModelAttributes {
    private static final long ISSUE_EPOCH_OFFSET = 20000L;
    private static final DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy年 M月d日 EEEE", Locale.JAPAN);

    /** 日付（例: 2000年 1月1日 月曜日） */
    @ModelAttribute("date")
    public String currentDate() {
        return LocalDate.now().format(DATE_TIME_FORMATTER);
    }

    /**
     * 号数.
     * TODO: 永続化した数値と連動させる。現在はエポック日からのオフセット。
     */
    @ModelAttribute("issueNumber")
    public String issueNumber() {
        long issue = LocalDate.now().toEpochDay() - ISSUE_EPOCH_OFFSET;
        return String.format("%,d", issue);
    }
}
