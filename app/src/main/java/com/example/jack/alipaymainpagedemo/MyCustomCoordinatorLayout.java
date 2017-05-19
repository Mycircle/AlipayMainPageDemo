package com.example.jack.alipaymainpagedemo;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/5/19.
 */

public class MyCustomCoordinatorLayout extends CoordinatorLayout {

    float OldY;
    private static final String TAG = "MyCustomCoordinatorLayo";
    public MyCustomCoordinatorLayout(Context context) {
        super(context);
    }

    public MyCustomCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE){     //下滑不拦截
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                OldY = ev.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();
                float distance = y - OldY;
                Log.d(TAG, "onTouchEvent: distance = "+distance);
//                if (distance > 200){
//                    Toast.makeText(getContext(), "下面该刷新了", Toast.LENGTH_SHORT).show();
//                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
}
