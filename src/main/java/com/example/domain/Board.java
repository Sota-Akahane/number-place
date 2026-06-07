package com.example.domain;

import java.util.*;
import java.util.stream.Stream;

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
                cells[row][col] = new Cell(row, col, 0, false);
            }
        }
    }

    /**
     * 指定したマスの数字を取得する.
     */
    public int getCellNumber(int row, int col) {
        return cells[row][col].getNumber();
    }

    /**
     * getCellNumber メソッドをオーバーライドした、引数が Cell の version.
     */
    public int getCellNumber(Cell cell) {
        return getCellNumber(cell.getRow(), cell.getCol());
    }

    /**
     * 指定したマスに数字を入れる.
     */
    public void place(int row, int col, int number) {
        cells[row][col].setNumber(number);
    }

    /**
     * place メソッドをオーバーライドした、引数が Cell の version.
     */
    public void place(Cell cell, int number) {
        place(cell.getRow(), cell.getCol(), number);
    }

    /**
     * 指定したセルを空にする.
     */
    public void clear(int row, int col) {
        cells[row][col].setNumber(0);
    }

    /**
     * clear メソッドをオーバーライドした、引数が Cell の version.
     */
    public void clear(Cell cell) {
        clear(cell.getRow(), cell.getCol());
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
     * ブロックの左上のマスから右下のマスを順に格納している。
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
     * 全 unit（行9 + 列9 + ブロック9）を返す.
     * 順序: 行→列→ブロックの順
     */
    public List<Unit> getAllUnits() {
        List<Unit> units = new ArrayList<>(27);

        for (int i = 0; i < 9; i++) {
            units.add(new Unit(UnitType.ROW, getRow(i)));
        }
        for (int i = 0; i < 9; i++) {
            units.add(new Unit(UnitType.COLUMN, getColumn(i)));
        }
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                units.add(new Unit(UnitType.BLOCK, getBlock(r, c)));
            }
        }
        return units;
    }

    /**
     * 盤面の全マスを左上から右下の順に Stream で返す.
     */
    public Stream<Cell> streamCells() {
        return Arrays.stream(cells).flatMap(Arrays::stream);
    }

    /**
     * 空のマス一覧を取得する.
     */
    public List<Cell> getEmptyCells() {
        return streamCells().filter(c -> c.getNumber() == 0).toList();
    }

    /**
     * 空のマスを1つ返す.
     */
    public Optional<Cell> findEmptyCell() {
        return streamCells().filter(c -> c.getNumber() == 0).findFirst();
    }

    /**
     * 既に埋まっているマス一覧を取得する.
     */
    public List<Cell> getFilledCells() {
        return streamCells().filter(c -> c.getNumber() != 0).toList();
    }

    /**
     * 文字列を Board に変換する.
     *
     * @param s 81文字の文字列
     * @throws IllegalArgumentException 入力が null / 長さ違い / 数字以外を含む場合
     */
    public static Board fromString(String s) {
        if (s == null) {
            throw new IllegalArgumentException("board string must not be null");
        }
        if (s.length() != 81) {
            throw new IllegalArgumentException(
                    "board string length must be 81, but was " + s.length());
        }

        Board board = new Board();
        for (int i = 0; i < 81; i++) {
            char ch = s.charAt(i);
            if (ch < '0' || ch > '9') {
                throw new IllegalArgumentException(
                        "board string must contain only digits 0-9, but found '"
                                + ch + "' at index " + i);
            }
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
     * 盤面のコピーを取る.
     * 盤面を変えたくない場合に使用する。
     * 例えば、一意解チェックは作問中の盤面本体でやりたくないため、このメソッドで取ったコピーで行う。
     */
    public Board copy() {
        Board board = new Board();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board.place(row, col, this.cells[row][col].getNumber());
            }
        }
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = getCellNumber(r, c);
                sb.append(v == 0 ? "." : v).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /** getter */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * はじめから埋まっているマスの given プロパティを変更する.
     * 作問時に一度だけ使用する。
     */
    public void markGivenCells() {
        for (Cell cell : getFilledCells()) {
            cell.setGiven(true);
        }
    }
}
