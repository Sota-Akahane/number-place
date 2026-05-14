package com.example.technique;

import java.util.List;

/**
 * Technique のインスタンスを一元的に生成する Factory.
 */
public class TechniqueFactory {
    /** ユーティリティクラスなのでインスタンス化禁止 */
    private TechniqueFactory() {}

    /**
     * 全テクニックを難易度の昇順で返す.
     */
    public static List<Technique> createAll() {
        return List.of(
                new NakedSingle(),
                new HiddenSingle()
        );
    }
}
