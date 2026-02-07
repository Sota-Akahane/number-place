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
}
