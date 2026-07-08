package com.example.domain;

import java.util.Objects;

/**
 * マスを表すドメイン.
 */
public class Cell {
    private final int row;
    private final int col;
    private int number;
    private boolean given;

    public Cell(int row, int col, int number, boolean given) {
        this.row = row;
        this.col = col;
        this.number = number;
        this.given = given;
    }

    public Cell(int row, int col, int number) {
        this(row, col, number, false);
    }

    public boolean isGiven() {
        return given;
    }

    public void setGiven(boolean given) {
        this.given = given;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "(" + (row + 1) + "," + (col + 1) + ")";
    }
}
