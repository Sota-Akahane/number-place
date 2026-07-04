package com.example.domain;

/**
 * あるマスの候補数字を削除する一手を表す,
 */
public record RemoveCandidateAction(Cell cell, int number) implements Action{}
