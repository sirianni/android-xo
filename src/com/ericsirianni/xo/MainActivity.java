package com.ericsirianni.xo;

import roboguice.activity.GuiceActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ericsirianni.xo.controller.GameController;
import com.ericsirianni.xo.view.BoardView;
import com.google.inject.Inject;

public class MainActivity extends GuiceActivity {
    
    public static final int RESET_DIALOG_ID = 0;
    
    @InjectView(R.id.boardFrame)
    private FrameLayout boardFrame;
    
    @InjectView(R.id.statusView)
    private TextView statusView;
    
    @Inject
    private BoardView boardView;

    @Inject
    private GameController gameController;
    
    public MainActivity() {
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.main);
    
        this.statusView.setText("Select a square");
        
        this.boardView.init();
        
        this.boardFrame.addView(this.boardView);
        
        this.gameController.init(this);
    }
    
}