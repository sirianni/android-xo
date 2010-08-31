package com.ericsirianni.xo.model;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Singleton;

@Singleton
public class Board {

    private Square[][] squares;
    private Set<Square> squareSet;

    private int numRows = 3;
    private int numColumns = 3;
    
    public Board() {
        this.squares = new Square[this.numColumns][this.numRows];
        this.squareSet = new HashSet<Square>();
        
        for (int x = 0; x < squares.length; x++) {
            for (int y = 0; y < squares[x].length; y++) {
                Square square = new Square(x, y);
                this.squares[x][y] = square;
                this.squareSet.add(square);
            }
        }
    }

    public void reset() {
        for (Square square : this.squareSet) {
            square.reset();
        }
    }
    
    public int getNumRows() {
        return this.numRows;
    }
    
    public int getNumColumns() {
        return this.numColumns;
    }
    
    public Square getSquare(int x, int y) {
        return this.squares[x][y];
    }

    public Square[][] getSquares() {
        return this.squares;
    }
    
    public Set<Square> getSquareSet() {
        return this.squareSet;
    }

}
