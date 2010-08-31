package com.ericsirianni.xo.model;

import java.util.Set;

import com.ericsirianni.xo.model.Square.Value;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class Game {

    private Player xPlayer;
    private Player oPlayer;
    private Player currentPlayer;
    private Player winner;
    
    private boolean isOver;
    
    @Inject
    private Board board;
    
    Game() {
        this.xPlayer = new Player().withSquareValue(Value.X);
        this.oPlayer = new Player().withSquareValue(Value.O);
        this.currentPlayer = this.xPlayer;
    }

    public void reset() {
        this.currentPlayer = this.xPlayer;
        this.isOver = false;
        
        this.board.reset();
    }
    
    public Board getBoard() {
        return this.board;
    }
    
    public Player getXPlayer() {
        return this.xPlayer;
    }
    
    public Player getOPlayer() {
        return this.oPlayer;
    }
    
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Player getWinner() {
        return this.winner;
    }
    
    public void nextTurn() {
        this.currentPlayer = this.currentPlayer == this.xPlayer ? this.oPlayer : this.xPlayer;
    }

    public Set<Player> getPlayers() {
        return ImmutableSet.of(this.xPlayer, this.oPlayer);
    }
    
    public boolean isOver() {
        return this.isOver;
    }
    
    public boolean checkOver() {
        if (this.isOver) {
            return true;
        }
        
        Square[][] squares = this.board.getSquares();

        // Check columns
        outer:
            for (int x = 0; x < squares.length; x++) {
                Value previous = null;
                for (int y = 0; y < squares[x].length; y++) {
                    Value current = squares[x][y].getValue();
                    if (current == null || (previous != null && current != previous)) {
                        continue outer;
                    }
                    previous = current;
                }
                this.isOver = true;
                this.winner = previous == Value.X ? this.xPlayer : this.oPlayer;
                
                return true;
            }

        // Check rows
        outer:
            for (int y = 0; y < squares.length; y++) {
                Value previous = null;
                for (int x = 0; x < squares[y].length; x++) {
                    Value current = squares[x][y].getValue();
                    if (current == null || (previous != null && current != previous)) {
                        continue outer;
                    }
                    previous = current;
                }
                this.isOver = true;
                this.winner = previous == Value.X ? this.xPlayer : this.oPlayer;
                
                return true;
            }

        // Check diagonals
        
        return false;
    }
    
}
