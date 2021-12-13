package ro.csie.en.dam10;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Map;
import java.util.Random;

public class MovieChart extends View {

    private Map<String, Integer> source;
    private Context context;
    private Random random;
    private Paint paint;

    public MovieChart(Context context, Map<String, Integer> movieStats) {
        super(context);
        this.source = movieStats;
        this.context = context;
        random = new Random();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //it is used for computing the height of the columns
        int maxValue = getMaxValue();
        //calculate how many columns we can fit into the width of the screen
        float colWidth = getWidth() / source.size();
        drawValues(canvas, maxValue, colWidth);
    }

    private void drawValues(Canvas canvas, int maxValue, float colWidth) {
        int currColumn = 0;
        for(String label: source.keySet())
        {
            int value = source.get(label);
            int color = generateColor();
            paint.setColor(color);
            drawColumn(canvas, maxValue, colWidth, currColumn, value);
            drawLabel(canvas, colWidth, currColumn, label, value);
            currColumn++;
        }
    }

    private void drawLabel(Canvas canvas, float colWidth, int currColumn, String label, int value) {
        paint.setColor(Color.BLACK);
        paint.setTextSize((float)0.3 * colWidth);
        float x = (float)((currColumn + 0.5) * colWidth);
        float y = (float)(0.9 * getHeight());
        canvas.rotate(270, x, y);
        canvas.drawText(label + "-" + value, x, y,paint);
        canvas.rotate(-270, x,y);
    }

    private void drawColumn(Canvas canvas, int maxValue, float colWidth, int currColumn, int value) {

        float x1 = currColumn * colWidth;
        float y1 = (1 - (float)(value) / maxValue) * getHeight();
        float x2 = x1 + colWidth;
        float y2 = getHeight();
        canvas.drawRect(x1, y1, x2, y2, paint);
    }

    private int generateColor() {
        return Color.argb(100, random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    private int getMaxValue() {
        int max = 0;
        for(Integer value: source.values())
            max = max < value ? value: max;
        return max;
    }
}