package com.example.lihuzi.infiniteviewpager.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.infiniteviewpager.R;


/**
 * Created by cocav on 2018/3/30.
 */

public class DLFloatViewGroup extends RelativeLayout {
    private View _floatView;
    private int parentLeft;
    private int parentTop;
    private int parentRight;
    private int parentBottom;

    private int childHalfWidth;
    private int childHalfHeight;
    private ImageView _iv;
    private TextView _close;
    private float _density;

    public DLFloatViewGroup(Context context) {
        super(context);
        init();
    }

    public DLFloatViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DLFloatViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        _density = getResources().getDisplayMetrics().density;
        setVisibility(GONE);
    }

    public void initFloatView(View v) {
        _floatView = v;
        addView(_floatView);
        this.setVisibility(VISIBLE);
    }

    public void initFloatView(View v, int left, int top, int right, int bottom) {
        initFloatView(v);
        LayoutParams params = new LayoutParams(right - left, bottom + top);
        params.setMargins(left, top, 0, 0);
        _floatView.setLayoutParams(params);
    }

    public void initDLFloatView(String imgUrl, final String linkUrl) {
        if (_floatView == null) {
            View floatView = LayoutInflater.from(getContext()).inflate(R.layout.layout_float_view, this, false);
            //TODO click
//            _close.setOnClickListener(new OnClickListener()
//            {
//                @Override
//                public void onClick(View v)
//                {
//                    setVisibility(GONE);
//                }
//            });
            initFloatView(floatView);
        }
    }

    private boolean initParentRect;

    private void initParentRect() {
        if (!initParentRect) {
            int[] location = new int[2];
            getLocationOnScreen(location);
            parentLeft = location[0];
            parentTop = location[1];
            parentRight = parentLeft + getWidth();
            parentBottom = parentTop + getHeight();
            initParentRect = true;
        }
    }

    private boolean initFloatRect;

    private void initFloatRect() {
        if (!initFloatRect) {
            childHalfWidth = _floatView.getWidth() / 2;
            childHalfHeight = _floatView.getHeight() / 2;
            initFloatRect = true;
        }
    }

    private float downX;
    private float downY;

    private boolean movePoint(float x, float y) {
        if (Math.abs(downX - x) <= 3 * _density && Math.abs(downY - y) <= 3 * _density) {
            //点击
            return false;
        } else {
            //移动
            return true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (_floatView == null) {
            return false;
        }
        float x = ev.getRawX();
        float y = ev.getRawY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                System.err.println("action down ,x:" + x + ", y:" + y);
            }
            break;
            case MotionEvent.ACTION_MOVE: {
                System.err.println("action move ,x:" + x + ", y:" + y);
            }
            break;
            case MotionEvent.ACTION_UP: {
                System.err.println("action up ,x:" + x + ", y:" + y);
            }
            break;
        }
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
            initFloatRect();
            int[] fingerLocation = new int[2];
            _floatView.getLocationOnScreen(fingerLocation);
            if (x > fingerLocation[0] && x < fingerLocation[0] + childHalfWidth + childHalfWidth && y > fingerLocation[1] && y < fingerLocation[1] + childHalfHeight + childHalfHeight) {
                downX = x;
                downY = y;
                sendInterceptBroadcast(true);
                return true;
            } else {
                sendInterceptBroadcast(false);
                return false;
            }
        }
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_MOVE: {
                if (!movePoint(x, y)) {
                    return true;
                }
                float[] point = calPoint(x, y);
                x = point[0];
                y = point[1];
                LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.setMargins((int) (x - childHalfWidth), (int) (y - childHalfHeight), 0, 0);
                _floatView.setLayoutParams(params);
            }
            break;
            case MotionEvent.ACTION_UP: {
                if (movePoint(x, y)) {
                    float[] point = calPoint(x, y);
                    x = point[0];
                    y = point[1];
                    LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.setMargins((int) (x - childHalfWidth), (int) (y - childHalfHeight), 0, 0);
                    _floatView.setLayoutParams(params);
                    sendInterceptBroadcast(false);
                } else {
//                    if (imgTouchEvent(ev))
//                    {
//
//                    }
//                    else if (closeTouchEvent(ev))
//                    {
//
//                    }
                }
            }
            break;
        }
        return true;
    }

    private float[] calPoint(float x, float y) {
        initParentRect();
        if (x < parentLeft + childHalfWidth) {
            x = parentLeft + childHalfWidth;
        }
        if (x > parentRight - childHalfWidth) {
            x = parentRight - childHalfWidth;
        }
        if (y < parentTop + childHalfHeight) {
            y = parentTop + childHalfHeight;
        }
        if (y > parentBottom - childHalfHeight) {
            y = parentBottom - childHalfHeight;
        }
        x -= parentLeft;
        y -= parentTop;
        return new float[]{x, y};
    }

    private boolean closeTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        float y = ev.getRawY();
        int[] fingerLocation = new int[2];
        _close.getLocationOnScreen(fingerLocation);
        int halfWidth = _close.getWidth() / 2;
        int halfHeigth = _close.getWidth() / 2;
        if (x > fingerLocation[0] && x < fingerLocation[0] + halfWidth + halfWidth && y > fingerLocation[1] && y < fingerLocation[1] + halfHeigth + halfHeigth) {
            _close.performClick();
            return true;
        } else {
            return false;
        }
    }

    private boolean imgTouchEvent(MotionEvent ev) {
        float x = ev.getRawX();
        float y = ev.getRawY();
        int[] fingerLocation = new int[2];
        _iv.getLocationOnScreen(fingerLocation);
        int halfWidth = _iv.getWidth() / 2;
        int halfHeigth = _iv.getWidth() / 2;
        if (x > fingerLocation[0] && x < fingerLocation[0] + halfWidth + halfWidth && y > fingerLocation[1] && y < fingerLocation[1] + halfHeigth + halfHeigth) {
            _iv.performClick();
            return true;
        } else {
            return false;
        }
    }

    private void sendInterceptBroadcast(boolean intercept) {
        /**
         * viewpager自带手势拦截，这里需要强制拦截
         */
        getParent().requestDisallowInterceptTouchEvent(intercept);
    }
}
