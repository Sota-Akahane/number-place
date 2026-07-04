package com.example.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 候補数字の状態管理用のクラス.
 */
public class CandidateState {
    private final Map<Cell, Set<Integer>> map = new HashMap<>();

    /** コンストラクタ */
    public CandidateState(Board board) {
        rebuild(board);
    }

    /**
     * あるマスの候補数字を取得する.
     */
    public Set<Integer> getCandidates(Cell cell) {
        return map.getOrDefault(cell, Set.of());
    }

    /**
     * あるマスの候補数字から、指定した数字を削除する.
     */
    public void removeCandidate(Cell cell, int number) {
        Set<Integer> set = map.get(cell);
        if (set != null) {
            set.remove(number);
        }
    }

    /**
     * 指定した数字があるマスの候補数字かどうかを判定する.
     * TODO: 要否を検討する。
     */
    public boolean contains(Cell cell, int number) {
        return map.containsKey(cell) && map.get(cell).contains(number);
    }

    /**
     * 候補数字の状態を素の計算から再構築する.
     * 数字確定時など、盤面が変わったタイミングで呼ぶ。
     */
    public void rebuild(Board board) {
        map.clear();
        CandidateCalculator calculator = new CandidateCalculator(board);
        for (Cell cell : board.getEmptyCells()) {
            map.put(cell, new HashSet<>(calculator.getCandidates(cell)));
        }
    }
}
