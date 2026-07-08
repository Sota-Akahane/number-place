package com.example.domain;

import com.example.technique.TechniqueType;

import java.util.List;

/**
 * ヒントを表すレコード.
 *
 * @param techniqueType テクニック
 * @param relatedCells 強調表示用の関連マス
 * @param action 実際に入れる次の一手
 * @param description ヒント文
 */
public record Hint(
        TechniqueType techniqueType,
        List<Cell> relatedCells,
        Action action,
        String description
) {
    /**
     * 指定座標が関連マスに含まれるかどうかを判定する.
     */
    public boolean isRelated(int row, int col) {
        for (Cell cell : relatedCells) {
            if (cell.getRow() == row && cell.getCol() == col) {
                return true;
            }
        }
        return false;
    }

    /**
     * 指定座標が次の一手のマスかどうかを判定する.
     */
    public boolean isAction(int row, int col) {
        Cell cell = action.cell();
        return cell.getRow() == row && cell.getCol() == col;
    }
}
