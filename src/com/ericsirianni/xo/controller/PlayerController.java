package com.ericsirianni.xo.controller;

import com.ericsirianni.xo.model.Player;

public abstract class PlayerController {

    private Player player;

    public PlayerController init(Player player) {
        this.player = player;
        return this;
    }
    
    public abstract void doTurn();

}
