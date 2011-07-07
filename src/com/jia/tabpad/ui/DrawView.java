package com.jia.tabpad.ui;

import java.io.*;
import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.jia.tabpad.main.*;


public class DrawView extends View implements OnTouchListener, Serializable {
    Paint paint;
    Point currentPoint;
    Point previousPoint;
    Stroke currentStroke;
    Bitmap bitmap;
    int minX = 100000;
    int minY = 100000;
    int maxX = 0;
    int maxY = 0;

    ArrayList<Stroke> drawing = new ArrayList<Stroke>();

    public DrawView(Context context, AttributeSet set) {
        super(context, set);
        init();
    }

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, Paint paint) {
        super(context);
        init();
        this.paint = paint;
    }


    public void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);

        this.setOnTouchListener(this);
        this.setBackgroundColor(Config.DEFAULT_BG_COLOR);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //basically draw all the strokes;
        for (Stroke s : drawing) {
            s.draw(canvas);
        }
    }

    private Paint getEraserPaint() {
        Paint p = new Paint(ApplicationState.paint);
        p.setStrokeWidth(50);
        p.setColor(Config.DEFAULT_BG_COLOR);
        return p;
    }

    public boolean onTouch(View view, MotionEvent event) {
        this.requestFocus();

        //this is meant to be a scrolling action, not a draw
        if (ApplicationState.canvasMode == ApplicationState.CanvasMode.SCROLLING)
            return true;

        //This is the start of a stroke
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (ApplicationState.canvasMode == ApplicationState.CanvasMode.ERASING) {
                Paint p = getEraserPaint();
                currentPoint = new Point(x, y, p);
            } else
                currentPoint = new Point(x, y, ApplicationState.paint);
            previousPoint = currentPoint;
            currentStroke = new Stroke();
            drawing.add(currentStroke);
        }
        //This is moving from the previous point to the current point
        else if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            if (ApplicationState.canvasMode == ApplicationState.CanvasMode.ERASING) {
                Paint p = getEraserPaint();
                currentPoint = new Point(x, y, p);
            } else
                currentPoint = new Point(x, y, ApplicationState.paint);

            Line l = new Line(previousPoint, currentPoint);
            currentStroke.addLine(l);
        }

        invalidate();
        previousPoint = currentPoint;
        return true;
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(previousPoint);
        out.writeObject(currentPoint);
        out.writeObject(currentStroke);
        out.writeObject(drawing);
    }

    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        previousPoint = (Point) in.readObject();
        currentPoint = (Point) in.readObject();
        currentStroke = (Stroke) in.readObject();
        drawing = (ArrayList<Stroke>) in.readObject();
        invalidate();
    }

    public void undoLastAction() {
        if (!drawing.isEmpty()) {
            drawing.remove(drawing.size() - 1);
            invalidate();
        }
    }

    /**
     * Calculate the stats of this image, minx, miny, etc.
     */
    private void calculateCanvasParams() {
        for (Stroke s : drawing) {
            for (Line l : s.lines) {
                if (l.start.x < minX)
                    minX = Math.round(l.start.x);
                if (l.end.x < minX)
                    minX = Math.round(l.end.x);

                if (l.start.y < minY)
                    minY = Math.round(l.start.y);
                if (l.end.y < minY)
                    minY = Math.round(l.end.y);      
                
                if (l.start.x > maxX)
                    maxX = Math.round(l.start.x);
                if (l.end.x > maxX)
                    maxX = Math.round(l.end.x);   
                
                if (l.start.y > maxY)
                    maxY = Math.round(l.start.y);
                if (l.end.y > maxY)
                    maxY = Math.round(l.end.y);                 
            }
        }
    }

    public Bitmap getCanvasAsImage() {

        //there is nothing to draw yet
        if (drawing.size() < 1)
            return null;

        //figure out the bounds of our drawing, so we don't create an image too big.
        calculateCanvasParams();
        int padding = 10;
        int width = maxX - minX;
        int height = maxY - minY;
        int xOffset = 5 + minX * -1;
        int yOffset = 5 + minY * -1;

        //create a canvas with bounds tight enough just to fit our drawing
        Canvas singleUseCanvas = new Canvas();
        bitmap = Bitmap.createBitmap(width + padding, height + padding, Bitmap.Config.ARGB_8888);
        singleUseCanvas.setBitmap(bitmap);

        singleUseCanvas.drawBitmap(bitmap, 0,0,ApplicationState.paint);
        singleUseCanvas.drawColor(Config.DEFAULT_BG_COLOR);
        for (Stroke s : drawing) {
            s.draw(singleUseCanvas, xOffset,  yOffset);
        }

        return bitmap;
    }
}
