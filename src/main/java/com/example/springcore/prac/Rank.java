package com.example.springcore.prac;

public enum Rank {
    THREE(3, 4_000),
    FOUR(4, 10_000),
    FIVE(5, 30_000);

    private final int money;
    private final int match;
    private int count; // 사용지양

    Rank(int match, int money) {  // Default 생성자는 private 으로 설정되어 있음.
        this.match = match;
        this.money = money;
    }
}
