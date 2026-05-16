package com.example.domain;

/**
 * ナンプレの単位（行・列・ブロック）を表すレコード.
 * @param type ユニットの種別
 * @param cells そのユニットに属する9マス
 */
public record Unit(UnitType type, Cell[] cells) {
}
