package com.ericsirianni.xo.controller;

import java.util.Random;

import com.ericsirianni.xo.model.Board;
import com.ericsirianni.xo.model.Game;
import com.ericsirianni.xo.model.Square;

public class RandomCPUStrategy implements CPUStrategy {

    @Override
    public Square selectSquare(Game game) {
        Board board = game.getBoard();
        
        Random random = new Random();        
        
        while(true) {
            Square randomSquare = board.getSquare(
                random.nextInt(board.getNumColumns()), 
                random.nextInt(board.getNumRows())
            );
            
            if (randomSquare.getValue() == null) {
                return randomSquare;
            }
        }

    }

}
