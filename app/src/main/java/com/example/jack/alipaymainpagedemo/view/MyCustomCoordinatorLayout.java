package com.example.jack.alipaymainpagedemo.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/5/19.
 */

public class MyCustomCoordinatorLayout extends CoordinatorLayout {


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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float OldY = 0;
        float OldAbsY = 0;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                 OldY = ev.getY();
                 OldAbsY = ev.getRawY();
                Log.d(TAG, "dispatchTouchEvent: y = "+OldY+"  absY = "+OldAbsY);
                return super.dispatchTouchEvent(ev);
            case MotionEvent.ACTION_MOVE:
                float nowY = ev.getY();
                float nowAbsY = ev.getRawY();
                float distance = nowY - OldY;
                float AbsDistance = nowAbsY -OldAbsY;
                Log.d(TAG, "dispatchTouchEvent: distance = "+distance+ "AbsDistance = "+AbsDistance);
                return super.dispatchTouchEvent(ev);

        }
        return super.dispatchTouchEvent(ev);
    }

}
