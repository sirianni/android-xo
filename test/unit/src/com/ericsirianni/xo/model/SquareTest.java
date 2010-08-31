package com.ericsirianni.xo.model;

import junit.framework.TestCase;

import org.junit.Test;

import com.ericsirianni.xo.model.Square.Value;

public class SquareTest extends TestCase {

    @Test
    public void testSetValue() {
        Square square = new Square(0, 0);
        square.setValue(Value.X);
        assertEquals(Value.X, square.getValue());
    }
    
}
