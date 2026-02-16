package com.example.technique;

import com.example.domain.Board;
import com.example.domain.Hint;

import java.util.Optional;

/**
 * 全てのテクニックが持つべきメソッドをまとめたインターフェース.
 */
public interface Technique {
    Optional<Hint> find(Board board);
    TechniqueType type();
}
