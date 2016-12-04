package undev.bg;

import android.content.Context;
import android.graphics.Color;
import android.widget.Button;

/**
 * Created by mhy on 2016-12-05.
 */

public class BingoBoardButton extends Button {


    int width, height;
    public BingoBoardButton(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        setBackgroundColor(Color.GRAY);
    }
    public BingoBoardButton(Context context, int height) {
        super(context);
        this.width = this.getWidth();
        this.height = height;
        setBackgroundColor(Color.GRAY);
    }

    @Override
    public void onMeasure(int x, int y){
        setMeasuredDimension(this.width, this.height);
    }
}
