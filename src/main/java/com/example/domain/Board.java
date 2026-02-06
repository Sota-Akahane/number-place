package com.example.domain;

import java.util.*;

/**
 * ナンプレの盤面を表すドメイン.
 */
public class Board {
    /** 盤面上のマス */
    private final Cell[][] cells;

    /** コンストラクタ */
    public Board() {
        cells = new Cell[9][9];
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new Cell(row, col, 0);
            }
        }
    }

    /**
     * 指定したセルの数字を取得する.
     */
    public int getCellNumber(int row, int col) {
        return cells[row][col].number();
    }

    /**
     * 指定したセルに数字を入れる.
     */
    public void place(int row, int col, int number) {
        cells[row][col] = new Cell(row, col, number);
    }

    /**
     * place メソッドをオーバーロードした、引数が Cell の version.
     */
    public void place(Cell cell, int number) {
        place(cell.row(), cell.col(), number);
    }

    /**
     * 指定したセルを空にする.
     */
    public void clear(int row, int col) {
        cells[row][col] = new Cell(row, col, 0);
    }

    /**
     * clear メソッドをオーバーロードした、引数が Cell の version.
     */
    public void clear(Cell cell) {
        place(cell.row(), cell.col(), 0);
    }

    /**
     * 特定の行に属するマスを取得する.
     */
    public Cell[] getRow(int row) {
        return cells[row];
    }

    /**
     * 特定の列に属するマスを取得する.
     */
    public Cell[] getColumn(int col) {
        Cell[] column = new Cell[9];

        for (int row = 0; row < 9; row++) {
            column[row] = cells[row][col];
        }

        return column;
    }

    /**
     * 特定のブロックに属するマスを取得する.
     * ブロックの左上のセルから右下のセルを順に格納している。
     */
    public Cell[] getBlock(int row, int col) {
        Cell[] block = new Cell[9];
        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;

        int idx = 0;
        for (int r = startRow; r < startRow + 3; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                block[idx++] = cells[r][c];
            }
        }

        return block;
    }

    /**
     * 空のマス一覧を取得する.
     */
    public List<Cell> getEmptyCells() {
        List<Cell> emptyCellsList = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (cells[row][col].number() == 0) {
                    emptyCellsList.add(cells[row][col]);
                }
            }
        }
        return emptyCellsList;
    }

    /**
     * 空のマスを1つ返す.
     */
    public Optional<Cell> findEmptyCell() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (cells[row][col].number() == 0) {
                    return Optional.of(new Cell(row, col, 0));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 行、列、ブロックで数字の重複がない（有効な盤面である）ことをチェックする.
     *
     * @return 重複なし:true, 重複あり:false
     */
    public boolean isValid() {
        for (int i = 0; i < 9; i++) {
            if (!noDuplicate(getRow(i))) {
                return false;
            }

            if (!noDuplicate(getColumn(i))) {
                return false;
            }
        }

        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!noDuplicate(getBlock(row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * セル単位の重複チェック.
     */
    public boolean isValid(Cell cell) {
        return noDuplicate(getRow(cell.row()))
                && noDuplicate(getColumn(cell.col()))
                && noDuplicate(getBlock(cell.row(), cell.col()));
    }

    /**
     * 重複がないことをチェックするヘルパーメソッド.
     *
     * @param cells 行、列、ブロックに含まれるマスの配列
     */
    private boolean noDuplicate(Cell[] cells) {
        boolean[] seen = new boolean[10];
        for (Cell cell : cells) {
            if (cell.number() == 0) {
                continue;
            }

            if (seen[cell.number()]) {
                return false;
            }

            seen[cell.number()] = true;
        }
        return true;
    }

    /**
     * 初期化用メソッド.
     * 文字列で書いた問題を Board に変換する。
     */
    public static Board fromString(String s) {
        if (s.length() != 81) {
            throw new IllegalArgumentException("length must be 81");
        }

        Board board = new Board();

        for (int i = 0; i < 81; i++) {
            char ch = s.charAt(i);
            if (ch != '0') {
                int value = ch - '0';
                int row = i / 9;
                int col = i % 9;
                board.place(row, col, value);
            }
        }

        return board;
    }

    /**
     * あるマスの候補数字を取得する.
     */
    public Set<Integer> getCandidates(Cell cell) {
        if (cell.number() != 0) {
            return Set.of(); // 既に埋まっている
        }

        Set<Integer> used = new HashSet<>();

        for (Cell c : getRow(cell.row())) {
            used.add(c.number());
        }
        for (Cell c : getColumn(cell.col())) {
            used.add(c.number());
        }
        for (Cell c : getBlock(cell.row(), cell.col())) {
            used.add(c.number());
        }

        Set<Integer> candidates = new HashSet<>();
        for (int n = 1; n <= 9; n++) {
            if (!used.contains(n)) {
                candidates.add(n);
            }
        }

        return candidates;
    }

    /**
     * 盤面のコピーを取る.
     * 一意解チェックは作問中の盤面本体でやりたくないため、このメソッドで取ったコピーで行う。
     */
    public Board copy() {
        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board.place(row, col, this.cells[row][col].number());
            }
        }
        return board;
    }
}
