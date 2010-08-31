package com.ericsirianni.xo.view;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.View;

import com.ericsirianni.xo.model.Board;
import com.ericsirianni.xo.model.Square;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class BoardView extends View {

    static final Paint PAINT = new Paint();
    
    static {
        PAINT.setColor(Color.WHITE);
        PAINT.setStrokeWidth(10);
        PAINT.setStyle(Style.STROKE);
    }
    
    @Inject
    private Board board;;
    
    @Inject
    private Provider<SquareView> squareViewProvider;
    
    private Map<Square, SquareView> squareViews;

    private Matrix matrix;
    private Matrix inverseMatrix;
    
    @Inject
    public BoardView(Context context) {
        super(context);
        this.squareViews = new HashMap<Square, SquareView>();
        this.matrix = new Matrix();
        this.inverseMatrix = new Matrix();
    }
    
    public void init() {
        this.setMinimumHeight(200);
        this.setMinimumWidth(200);
        
        for (Square square : this.board.getSquareSet()) {
            this.squareViews.put(square, this.squareViewProvider.get().withSquare(square));
        }
    }
    
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed) {
            this.matrix.reset();
            this.matrix.preScale(
                (float) (right - left) / (this.board.getNumColumns() * SquareView.SIZE), 
                (float) (bottom - top) / (this.board.getNumRows() * SquareView.SIZE)
            );
            Log.d("xo", "matrix = " + this.matrix);
            this.matrix.invert(this.inverseMatrix);
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("xo", "BoardView.onDraw()");

        canvas.drawColor(Color.BLACK);

        Rect clipBounds = canvas.getClipBounds();
        Log.d("xo", "clipBounds = " + clipBounds);
        
        canvas.save();
        canvas.concat(this.matrix);
        
        for (int i = 1; i < this.board.getNumRows(); i++) {
            canvas.drawLine(0, i * SquareView.SIZE, this.board.getNumColumns() * SquareView.SIZE, i * SquareView.SIZE, PAINT) ;    
        }
        
        for (int i = 1; i < this.board.getNumColumns(); i++) {
            canvas.drawLine(i * SquareView.SIZE, 0, i * SquareView.SIZE, this.board.getNumRows() * SquareView.SIZE, PAINT) ;    
        }
        
        for (SquareView squareView : this.squareViews.values()) {
            squareView.draw(canvas);
        }

        canvas.restore();
    }

    public SquareView lookupSquareViewBySquare(Square square) {
        return this.squareViews.get(square);
    }
    
    public SquareView lookupSquareViewByCoordinates(float x, float y) {
        float[] coord = new float[] { x, y };
        this.inverseMatrix.mapPoints(coord);
        for (SquareView squareView : this.squareViews.values()) {
            if (squareView.isInBounds(coord[0], coord[1])) {
                return squareView;
            }
        }
        throw new IllegalStateException(String.format("Coordinate out of bounds: (%f,%f)", x, y));
    }
    
    void invalidateNormalized(Rect normalizedRect) {
        RectF transformedRectF = new RectF(normalizedRect);
        this.matrix.mapRect(transformedRectF);
        
        Rect transformedRect = new Rect();
        transformedRectF.roundOut(transformedRect);
        
        Log.d("xo", "Invalidating " + transformedRect);
        this.invalidate(transformedRect);
    }
}
