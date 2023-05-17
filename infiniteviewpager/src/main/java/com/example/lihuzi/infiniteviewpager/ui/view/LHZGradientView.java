package com.example.lihuzi.infiniteviewpager.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.infiniteviewpager.R;


public class LHZGradientView extends View {
    public LHZGradientView(Context context) {
        super(context);
    }

    public LHZGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LHZGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取View的宽高
        int width = getWidth();
        int height = getHeight();

        int colorStart = getResources().getColor(R.color.end_color);
        int color1 = Color.GRAY;
        int colorEnd = getResources().getColor(R.color.start_color);

        Paint paint = new Paint();
//        LinearGradient backGradient = new LinearGradient(0, 0, width, height, new int[]{colorStart, color1, colorEnd}, null, Shader.TileMode.CLAMP);
        LinearGradient backGradient = new LinearGradient(0, 0, width, height, new int[]{colorStart, colorEnd}, null, Shader.TileMode.CLAMP);
//        LinearGradient backGradient = new LinearGradient(0, 0, 0, height, new int[]{colorStart, color1 ,colorEnd}, null, Shader.TileMode.CLAMP);
        paint.setShader(backGradient);
        canvas.drawRect(0, 0, width, height, paint);
    }
}
