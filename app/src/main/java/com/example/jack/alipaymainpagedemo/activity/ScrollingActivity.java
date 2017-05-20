package com.example.jack.alipaymainpagedemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.jack.alipaymainpagedemo.R;
import com.example.jack.alipaymainpagedemo.adapter.RecyclerAdapter;
import com.example.jack.alipaymainpagedemo.common.DividerGridItemDecoration;
import com.example.jack.alipaymainpagedemo.entity.Item;
import com.example.jack.alipaymainpagedemo.helper.OnRecyclerItemClickListener;
import com.example.jack.alipaymainpagedemo.utils.ACache;
import com.example.jack.alipaymainpagedemo.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试是否可以上传
 */
public class ScrollingActivity extends AppCompatActivity implements OnRefreshListener {


    protected CoordinatorLayout mCoordinateMain;
//    protected MyCustomCoordinatorLayout mCoordinateMain;
    protected SwipeToLoadLayout mSwipeToLoadLayout;
    private AppBarLayout mAppBarLayout = null;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private View mToolbar1 = null;
    private View mToolbar2 = null;

    private ImageView mZhangdan = null;
    private TextView mZhangdan_txt = null;
    private ImageView mTongxunlu = null;
    private ImageView mJiahao = null;

    private ImageView mZhangdan2 = null;
    private ImageView mShaoyishao = null;
    private ImageView mSearch = null;
    private ImageView mZhaoxiang = null;

    private RecyclerView myRecyclerView;

    private static final String TAG = "ScrollingActivity";
    private List<Item> results = new ArrayList<Item>();
    RecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_scrolling);
//        myRecyclerView = (RecyclerView) findViewById(R.id.myRecyclerView);
        myRecyclerView = (RecyclerView) findViewById(R.id.swipe_target);
//        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.color1984D1));
        ArrayList<Item> items = (ArrayList<Item>) ACache.get(this).getAsObject("items");
        if (items != null)
            results.addAll(items);
        results.add(new Item(results.size(), "更多", R.drawable.takeout_ic_more));
        adapter = new RecyclerAdapter(R.layout.item_grid, results);

        myRecyclerView.setAdapter(adapter);
        myRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        myRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));

//        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter).setOnDragListener(this));
//        itemTouchHelper.attachToRecyclerView(recylerview);

        myRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(myRecyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != results.size() - 1) {
//                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(ScrollingActivity.this, 70);   //震动70ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Item item = results.get(vh.getLayoutPosition());
                if(item.getName().equals("更多")){
                    startActivity(new Intent(ScrollingActivity.this,NextActivity.class));
                }else
                    Toast.makeText(ScrollingActivity.this, item.getId() + " " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
//        myRecyclerView.setAdapter(new ToolbarAdapter(this));

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mToolbar1 = (View) findViewById(R.id.toolbar1);
        mToolbar2 = (View) findViewById(R.id.toolbar2);

        mZhangdan = (ImageView) findViewById(R.id.img_zhangdan);
        mZhangdan_txt = (TextView) findViewById(R.id.img_zhangdan_txt);
        mTongxunlu = (ImageView) findViewById(R.id.tongxunlu);
        mJiahao = (ImageView) findViewById(R.id.jiahao);

        mZhangdan2 = (ImageView) findViewById(R.id.img_shaomiao);
        mShaoyishao = (ImageView) findViewById(R.id.img_fukuang);
        mSearch = (ImageView) findViewById(R.id.img_search);
        mZhaoxiang = (ImageView) findViewById(R.id.img_zhaoxiang);
        initView();
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                Log.d(TAG, "onOffsetChanged: vertcaloffset = "+verticalOffset+"ScrollRange = "+appBarLayout.getTotalScrollRange());

                if (verticalOffset == 0) {
                    //张开（完全张开）
                    mToolbar1.setVisibility(View.VISIBLE);
                    mToolbar2.setVisibility(View.GONE);
                    setToolbar1Alpha(255);
                    mSwipeToLoadLayout.setRefreshEnabled(true);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    //收缩
                    mToolbar1.setVisibility(View.GONE);
                    mToolbar2.setVisibility(View.VISIBLE);
                    setToolbar2Alpha(255);
                    mSwipeToLoadLayout.setRefreshEnabled(false);
                } else {
                    mSwipeToLoadLayout.setRefreshEnabled(false);
                    int alpha = 255 - Math.abs(verticalOffset);
                    if (alpha < 0) {
                        Log.e("alpha", alpha + "");
                        //收缩toolbar
                        mToolbar1.setVisibility(View.GONE);
                        mToolbar2.setVisibility(View.VISIBLE);
                        float distance = (Math.abs(alpha) *(255/(float)(appBarLayout.getTotalScrollRange()-255)));
                        int Ds = (int)distance;     //使渐变效果更平缓，避免透明度突然增大引起闪屏
                        setToolbar2Alpha(Ds);
                    } else {                //当滑动距离小于255时(可以说下拉也可以是上滑）toobar1执行透明度渐变，
                        //张开toolbar
                        mToolbar1.setVisibility(View.VISIBLE);
                        mToolbar2.setVisibility(View.GONE);
                        setToolbar1Alpha(alpha);
                    }
                }
            }
        });
    }

    //设置展开时各控件的透明度
    public void setToolbar1Alpha(int alpha) {
        mZhangdan.getDrawable().setAlpha(alpha);
        mZhangdan_txt.setTextColor(Color.argb(alpha, 255, 255, 255));
        mTongxunlu.getDrawable().setAlpha(alpha);
        mJiahao.getDrawable().setAlpha(alpha);
    }

    //设置闭合时各控件的透明度
    public void setToolbar2Alpha(int alpha) {
        mZhangdan2.getDrawable().setAlpha(alpha);
        mShaoyishao.getDrawable().setAlpha(alpha);
        mSearch.getDrawable().setAlpha(alpha);
        mZhaoxiang.getDrawable().setAlpha(alpha);
    }

    private void initView() {
        mJiahao = (ImageView) findViewById(R.id.jiahao);
        mTongxunlu = (ImageView) findViewById(R.id.tongxunlu);
        mCoordinateMain = (CoordinatorLayout) findViewById(R.id.coordinate_main);
//        mCoordinateMain = (MyCustomCoordinatorLayout) findViewById(R.id.coordinate_main);
        mSwipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        mSwipeToLoadLayout.setRefreshEnabled(false);
        mSwipeToLoadLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mSwipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSwipeToLoadLayout.setRefreshing(false);
            }
        }, 2000);
    }
    @Override
    protected void onResume() {
        super.onResume();
        results.clear();
        ArrayList<Item> items = (ArrayList<Item>) ACache.get(this).getAsObject("items");
        if (items != null)
            results.addAll(items);
        results.add(new Item(results.size(), "更多", R.drawable.takeout_ic_more));adapter.addList(results);
    }
}
