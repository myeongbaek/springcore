package com.example.springcore.prac;

public class Main {
    public static void main(String[] args) {
        CardNoEnum heartOne = new CardNoEnum(CardNoEnum.HEART, CardNoEnum.ONE);
        heartOne.cardInfo();

        CardEnum spadeThree = new CardEnum(CardEnum.Kind.SPADE, CardEnum.Value.THREE);
        spadeThree.cardInfo();
    }
}

class Card<T> {
    private T kind;
    private T num;

    public Card(T kind, T num){
        this.kind = kind;
        this.num = num;
    }

    public void cardInfo(){
        System.out.printf("Kind : %s, Number : %s\n", this.kind, this.num);
    }
}

class CardNoEnum extends Card{
    static final int CLOVER = 0;
    static final int HEART = 1;
    static final int DIAMOND = 2;
    static final int SPADE = 3;

    static final int ONE = 1;
    static final int TWO = 2;
    static final int THREE = 3;
    static final int FOUR = 4;

    public CardNoEnum(int kind, int num) {
        super(kind, num);
    }

}
class CardEnum extends Card{

    enum Kind { CLOVER, HEART, DIAMOND, SPADE}
    enum Value { ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}

    public CardEnum(Kind kind, Value value){
        super(kind, value);
    }
}

