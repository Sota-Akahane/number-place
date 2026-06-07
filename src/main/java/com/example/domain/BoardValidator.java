package com.example.domain;

/**
 * 盤面の validation(行・列・ブロックの重複チェック) を担うクラス.
 */
public class BoardValidator {
    private final Board board;

    public BoardValidator(Board board) {
        this.board = board;
    }

    /**
     * 行・列・ブロックの全てで数字の重複がないことをチェックする.
     *
     * @return 重複なし:ture, 重複あり:false
     */
    public boolean isValid() {
        for (int i = 0; i < 9; i++) {
            if (!noDuplicate(board.getRow(i))) {
                return false;
            }
            if (!noDuplicate(board.getColumn(i))) {
                return false;
            }
        }
        for (int row = 0; row < 9; row += 3) {
            for (int col = 0; col < 9; col += 3) {
                if (!noDuplicate(board.getBlock(row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 指定マスを含む行・列・ブロックの重複チェック.
     */
    public boolean isValid(Cell cell) {
        return noDuplicate(board.getRow(cell.getRow()))
                && noDuplicate(board.getColumn(cell.getCol()))
                && noDuplicate(board.getBlock(cell.getRow(), cell.getCol()));
    }

    private boolean noDuplicate(Cell[] cells) {
        boolean[] seen = new boolean[10];
        for (Cell cell : cells) {
            if (cell.getNumber() == 0) {
                continue;
            }
            if (seen[cell.getNumber()]) {
                return false;
            }
            seen[cell.getNumber()] = true;
        }
        return true;
    }
}
