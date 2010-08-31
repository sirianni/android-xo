package com.ericsirianni.xo.controller;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.ericsirianni.xo.view.BoardView;
import com.ericsirianni.xo.view.SquareView;
import com.google.inject.Inject;

class HumanPlayerController extends PlayerController {

    @Inject
    private BoardView boardView;
    
    @Inject
    private GameController gameController;
    
    public void doTurn() {
        this.boardView.setOnTouchListener(new BoardViewOnTouchListener());
    }
    
    class BoardViewOnTouchListener implements OnTouchListener {
    
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            Log.d("xo", "PlayerController.onTouch(): Action=" + event.getAction());

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    return true;
                case MotionEvent.ACTION_UP:
                    boardView.setOnTouchListener(null);
                    
                    SquareView selectedSquareView = boardView.lookupSquareViewByCoordinates(event.getX(), event.getY());
                    
                    gameController.onTurnComplete(selectedSquareView.getSquare());
                    
                    return true;
            }

            return false;
            
        }
    }

}
