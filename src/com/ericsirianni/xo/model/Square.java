package com.ericsirianni.xo.model;

public class Square {

    public enum Value {
        X,
        O;
    }

    private int x;
    private int y;
    
    private Value value;

    Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void reset() {
        this.value = null;
    }
    
    public Value getValue() {
        return this.value;
    }
    
    public void setValue(Value value) {
        this.value = value;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }

}
