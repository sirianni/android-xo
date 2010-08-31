package com.ericsirianni.xo.controller;

 import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.ericsirianni.xo.MainActivity;
import com.ericsirianni.xo.model.Game;
import com.ericsirianni.xo.model.Player;
import com.ericsirianni.xo.model.Square;
import com.ericsirianni.xo.view.BoardView;
import com.ericsirianni.xo.view.SquareView;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class GameController {

    @Inject
    private Game game;
    
    @Inject
    private Provider<HumanPlayerController> humanPlayerControllerProvider;
    
    @Inject
    private Provider<CPUPlayerController> cpuPlayerControllerProvider;
    
    @Inject
    private BoardView boardView;

    private AlertDialog resetDialog;

    private Map<Player, PlayerController> playerControllers = Maps.newHashMap();
    
    public void init(MainActivity mainActivity) {
        this.playerControllers.put(this.game.getXPlayer(), this.humanPlayerControllerProvider.get().init(this.game.getXPlayer()));
        this.playerControllers.put(this.game.getOPlayer(), this.cpuPlayerControllerProvider.get().init(this.game.getOPlayer()));
        
        this.resetDialog = new AlertDialog.Builder(mainActivity)
            .setCancelable(false)
            .setPositiveButton("Reset Game", new ResetButtonOnClickListener())
            .create();
        this.resetDialog.setOwnerActivity(mainActivity);
        
        this.playerControllers.get(this.game.getCurrentPlayer()).doTurn();
    }
    
    class ResetButtonOnClickListener implements OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            game.reset();
            dialog.dismiss();
            boardView.invalidate();
            playerControllers.get(game.getCurrentPlayer()).doTurn();
        }
        
    }
    
    void onTurnComplete(Square selectedSquare) {
        SquareView selectedSquareView = boardView.lookupSquareViewBySquare(selectedSquare);
        
        selectedSquare.setValue(this.game.getCurrentPlayer().getSquareValue());
        selectedSquareView.invalidate();
        
        if (this.game.checkOver()) {
            resetDialog.setMessage(String.format("Game Over - %s Wins!", this.game.getWinner().getSquareValue()));
            resetDialog.show();
        } else {
            this.game.nextTurn();
            this.playerControllers.get(this.game.getCurrentPlayer()).doTurn();
        }
    }
}
