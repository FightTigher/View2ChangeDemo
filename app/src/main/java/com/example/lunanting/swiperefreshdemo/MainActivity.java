package com.example.lunanting.swiperefreshdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private List<ItemBean> mDatas;
    private HomeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        Button button1 = (Button) findViewById(R.id.btn_1);
        Button button3 = (Button) findViewById(R.id.btn_3);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new HomeAdapter();
        mRecyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
//        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
//        alphaAdapter.setDuration(1000);

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaAdapter.setFirstOnly(false);
        alphaAdapter.setDuration(500);
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));

        mRecyclerView.getItemAnimator().setAddDuration(100);
        mRecyclerView.getItemAnimator().setRemoveDuration(1000);
        mRecyclerView.getItemAnimator().setMoveDuration(100);
        mRecyclerView.getItemAnimator().setChangeDuration(100);
        mRecyclerView.setAdapter(alphaAdapter);

        button1.setOnClickListener(this);
        button3.setOnClickListener(this);
    }


    protected void initData() {
        mDatas = new ArrayList<ItemBean>();
        for (int i = 'A'; i < 'z'; i++) {
            ItemBean itemBean = new ItemBean("" + (char) i, true);
            mDatas.add(itemBean);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.VERTICAL));
                changeItem(mDatas, true);
                break;
            case R.id.btn_3:
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                        StaggeredGridLayoutManager.VERTICAL));
                changeItem(mDatas, false);
                break;
        }
    }

    public void changeItem(List<ItemBean> mDatas, boolean b) {
        for (int i = 0; i < mDatas.size(); i++) {
            mDatas.get(i).setItem(b);
            mAdapter.notifyItemChanged(i);
        }
    }

    public void remove(int position) {
        mDatas.remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    public void add(String text, int position) {
        mAdapter.notifyItemInserted(position);
    }

    enum ITEM_TYPE {
        ITEM_TYPE_1,
        ITEM_TYPE_3,
    }

    class HomeAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == ITEM_TYPE.ITEM_TYPE_1.ordinal()) {
                MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                        MainActivity.this).inflate(R.layout.item_home, parent,
                        false));
                return holder;
            } else {
                ITEMViewHolder holder = new ITEMViewHolder(LayoutInflater.from(
                        MainActivity.this).inflate(R.layout.item_home, parent,
                        false));
                return holder;
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).tv.setText(mDatas.get(position).getString());
            } else if (holder instanceof ITEMViewHolder) {
                ((ITEMViewHolder) holder).tv.setText(mDatas.get(position).getString() + "1");
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (mDatas.get(position).isItem()) {
                return ITEM_TYPE.ITEM_TYPE_1.ordinal();
            } else {
                return ITEM_TYPE.ITEM_TYPE_3.ordinal();
            }
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }

        class ITEMViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public ITEMViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }


    }

}
