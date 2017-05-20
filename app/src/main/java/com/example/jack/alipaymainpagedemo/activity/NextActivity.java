package com.example.jack.alipaymainpagedemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;


import com.example.jack.alipaymainpagedemo.R;
import com.example.jack.alipaymainpagedemo.adapter.RecyclerAdapter;
import com.example.jack.alipaymainpagedemo.common.DividerGridItemDecoration;
import com.example.jack.alipaymainpagedemo.entity.Item;
import com.example.jack.alipaymainpagedemo.helper.MyItemTouchCallback;
import com.example.jack.alipaymainpagedemo.helper.OnRecyclerItemClickListener;
import com.example.jack.alipaymainpagedemo.utils.ACache;
import com.example.jack.alipaymainpagedemo.utils.VibratorUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NextActivity extends AppCompatActivity implements MyItemTouchCallback.OnDragListener {

    @BindView(R.id.recylerview)
    RecyclerView recylerview;
    @BindView(R.id.recylerviewtwo)
    RecyclerView recylerviewtwo;
    private List<Item> results = new ArrayList<Item>();
    private ItemTouchHelper itemTouchHelper;
    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Item> itemst = new ArrayList<>();
    private List<Item> resultst = new ArrayList<Item>();
    RecyclerAdapter adapter;
    RecyclerAdapter adapterT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next);
        ButterKnife.bind(this);

        /////////初始化数据，如果缓存中有就使用缓存中的
        items = (ArrayList<Item>) ACache.get(this).getAsObject("items");
        if (items != null)
            results.addAll(items);
        adapter = new RecyclerAdapter(R.layout.item_grid, results);
        recylerview.setAdapter(adapter);
        recylerview.setLayoutManager(new GridLayoutManager(this, 4));
        recylerview.addItemDecoration(new DividerGridItemDecoration(this));

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(adapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(recylerview);

        recylerview.addOnItemTouchListener(new OnRecyclerItemClickListener(recylerview) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                if (vh.getLayoutPosition() != results.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                    VibratorUtil.Vibrate(NextActivity.this, 70);   //震动70ms
                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Item item = results.get(vh.getLayoutPosition());
                results.remove(item);
                adapter.addList(results);
                resultst.add(item);
                adapterT.addList(resultst);
                //存入缓存
                ACache.get(NextActivity.this).put("items", (ArrayList<Item>) results);
                //存入缓存
                ACache.get(NextActivity.this).put("itemst", (ArrayList<Item>) resultst);
//                Toast.makeText(NextActivity.this, item.getId() + " " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        itemst = (ArrayList<Item>) ACache.get(this).getAsObject("itemst");
        if (itemst != null)
            resultst.addAll(itemst);
        else {
            resultst.add(new Item(0, "收款", R.drawable.takeout_ic_category_brand));
            resultst.add(new Item(1, "转账", R.drawable.takeout_ic_category_flower));
            resultst.add(new Item(2, "余额宝", R.drawable.takeout_ic_category_fruit));
            resultst.add(new Item(3, "手机充值", R.drawable.takeout_ic_category_medicine));
            resultst.add(new Item(4, "医疗", R.drawable.takeout_ic_category_motorcycle));
            resultst.add(new Item(5, "彩票", R.drawable.takeout_ic_category_public));
            resultst.add(new Item(6, "电影", R.drawable.takeout_ic_category_store));
            resultst.add(new Item(7, "游戏", R.drawable.takeout_ic_category_sweet));

        }
        adapterT = new RecyclerAdapter(R.layout.item_grid, resultst);
        recylerviewtwo.setAdapter(adapterT);
        recylerviewtwo.setLayoutManager(new GridLayoutManager(this, 4));
        recylerviewtwo.addItemDecoration(new DividerGridItemDecoration(this));
        recylerviewtwo.addOnItemTouchListener(new OnRecyclerItemClickListener(recylerviewtwo) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
//                if (vh.getLayoutPosition() != results.size() - 1) {
//                    itemTouchHelper.startDrag(vh);
//                    VibratorUtil.Vibrate(NextActivity.this, 70);   //震动70ms
//                }
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                Item item = resultst.get(vh.getLayoutPosition());
                resultst.remove(item);
                adapterT.addList(resultst);
                results.add(item);
                adapter.addList(results);
                //存入缓存
                ACache.get(NextActivity.this).put("itemst", (ArrayList<Item>) resultst);
                //存入缓存
                ACache.get(NextActivity.this).put("items", (ArrayList<Item>) results);

//                Toast.makeText(NextActivity.this, item.getId() + " " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onFinishDrag() {
        Toast.makeText(NextActivity.this, "回执完成", Toast.LENGTH_SHORT).show();
        //存入缓存
        ACache.get(this).put("items", (ArrayList<Item>) results);
    }
}
