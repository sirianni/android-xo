package com.ericsirianni.xo.model;

import com.ericsirianni.xo.model.Square.Value;

public class Player {

    private Value squareValue;
    
    public Player withSquareValue(Value squareValue) {
        this.squareValue = squareValue;
        return this;
    }
    
    public Value getSquareValue() {
        return this.squareValue;
    }


}
