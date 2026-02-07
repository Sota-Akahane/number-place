package com.example.domain;

import java.util.List;

/**
 * ヒントを表すレコード.
 *
 * @param techniqueName テクニック名
 * @param relatedCells 強調表示用の関連マス
 * @param action 実際に入れる次の一手
 * @param description ヒント文
 */
public record Hint(
        String techniqueName,
        List<Cell> relatedCells,
        Action action,
        String description
) {
}
