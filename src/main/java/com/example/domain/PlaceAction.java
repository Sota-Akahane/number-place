package com.example.domain;

/**
 * あるマスに数字を確定させる一手を表す.
 */
public record PlaceAction(Cell cell, int number) implements Action {}
