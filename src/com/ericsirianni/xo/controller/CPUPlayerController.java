package com.ericsirianni.xo.controller;

import android.os.AsyncTask;

import com.ericsirianni.xo.model.Game;
import com.ericsirianni.xo.model.Square;
import com.google.inject.Inject;

class CPUPlayerController extends PlayerController {
    
    @Inject
    private GameController gameController;

    @Inject
    private Game game;
    
    @Inject
    private CPUStrategy cpuStrategy;
    
    @Override
    public void doTurn() {
        new AsyncTask<Void, Void, Square>() {

            @Override
            protected Square doInBackground(Void... params) {
                return cpuStrategy.selectSquare(game);
            }
            
            @Override
            protected void onPostExecute(Square result) {
                gameController.onTurnComplete(result);
            }
            
        }.execute();
        
    }

}
