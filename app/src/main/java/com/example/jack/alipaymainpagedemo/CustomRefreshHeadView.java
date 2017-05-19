package com.example.jack.alipaymainpagedemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * Created by Administrator on 2017/5/18ad.
 * shen
 */

public class CustomRefreshHeadView extends TextView implements SwipeRefreshTrigger, SwipeTrigger {
    public CustomRefreshHeadView(Context context) {
        super(context);
    }

    public CustomRefreshHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRefreshHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onRefresh() {
        setText("正在拼命加载数据...");
    }

    @Override
    public void onPrepare() {

    }

    @Override
    public void onSwipe(int i, boolean b) {
        setText("释放刷新");

    }

    @Override
    public void onRelease() {


    }

    @Override
    public void complete() {
        setText("刷新成功");
    }

    @Override
    public void onReset() {

    }
}
