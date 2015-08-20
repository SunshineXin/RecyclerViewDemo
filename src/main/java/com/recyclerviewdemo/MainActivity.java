package com.recyclerviewdemo;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;

    private RecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private TextView mPositionTV, mCountTV, mStateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPositionTV = (TextView) findViewById(R.id.position);
        mCountTV = (TextView) findViewById(R.id.count);
        mStateTV = (TextView) findViewById(R.id.state);

        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeLayout.setOnRefreshListener(this);
//        mSwipeLayout.setColorScheme(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);

        mAdapter =  new RecyclerViewAdapter(getData(40));
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // 创建一个线性布局管理器
        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager.setOrientation(LinearLayout.HORIZONTAL);//将item设置为横向
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClikListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
        mAdapter.setOnItemLongClickListener(new RecyclerViewAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this, "long click : " + mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mPositionTV.setText("First:" + mLayoutManager.findFirstVisibleItemPosition());
                mCountTV.setText("Counts:" + mRecyclerView.getChildCount());
                updateState(newState);
            }
        });

//        mRecyclerView.setItemAnimator();
    }

    private List<String> getData(int count){
        if(count < 1){
            return null;
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++){
            list.add("item : " + i);
        }

        return list;
    }

    private void updateState(int scrollState) {
        String stateName = "Undefined";
        switch(scrollState) {
            case SCROLL_STATE_IDLE:
                stateName = "Idle";
                break;

            case SCROLL_STATE_DRAGGING:
                stateName = "Dragging";
                break;

            case SCROLL_STATE_SETTLING:
                stateName = "Flinging";
                break;
        }

        mStateTV.setText(stateName);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        }, 5000);
    }
}
