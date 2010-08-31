package com.ericsirianni.xo.view;

import static com.ericsirianni.xo.view.BoardView.PAINT;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.ericsirianni.xo.model.Square;
import com.google.inject.Inject;

public class SquareView {
    
    static final int SIZE = 100;
    static final int HALF_SIZE = SIZE / 2;

    private Square square;
    
    @Inject
    private BoardView boardView;

    private Rect bounds;

    SquareView withSquare(Square square) {
        this.square = square;
        this.bounds = new Rect(
            this.square.getX() * SIZE,
            this.square.getY() * SIZE,
            (this.square.getX() + 1) * SIZE,
            (this.square.getY() + 1) * SIZE
        );
        
        return this;
    }

    public Square getSquare() {
        return this.square;
    }
    
    void draw(Canvas canvas) {
        if (this.square.getValue() == null) {
            return;
        }
        
        canvas.save();
        canvas.translate(this.bounds.centerX(), this.bounds.centerY());
        canvas.scale(0.8f, 0.8f);
        
        switch (this.square.getValue()) {
            case X:
                this.drawX(canvas);
                break;
            case O:
                this.drawO(canvas);
                break;
        }
        
        canvas.restore();
    }

    private void drawX(Canvas canvas) {
        canvas.drawLine(-1 * HALF_SIZE, -1 * HALF_SIZE, HALF_SIZE, HALF_SIZE, PAINT) ;
        canvas.drawLine(-1 * HALF_SIZE, HALF_SIZE, HALF_SIZE, -1 * HALF_SIZE, PAINT);   
    }
    
    private void drawO(Canvas canvas) {
        canvas.drawCircle(0, 0, HALF_SIZE, PAINT);
    }

    public boolean isInBounds(float x, float y) {
        return this.bounds.contains((int) x, (int) y);
    }

    public void invalidate() {
        Log.d("xo", "SquareView.invalidate()");
        this.boardView.invalidateNormalized(this.bounds);
    }
    
}
