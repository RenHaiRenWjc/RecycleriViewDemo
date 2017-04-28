package com.phonelink.recycleriviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PagingScrollUtil.OnPageChangeListener {

    private static final String TAG = "MainActivity";
    @BindView(R.id.my_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv)
    TextView mTextView;
    @BindView(R.id.my_indicatior)
    CircleIndicator mCircleIndicator;
    @BindView(R.id.my_rect_indicatior)
    RectIndicator mRectIndicator;
    List<String> mDatas = new ArrayList<>();
    PagingScrollUtil mPagingScrollUtil = new PagingScrollUtil();
    private MyAdapter mAdapter;
    private HorizontalPageLayoutManager horizhontalPageLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        mAdapter = new MyAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        horizhontalPageLayoutManager = new HorizontalPageLayoutManager(2, 4);
        mRecyclerView.setLayoutManager(horizhontalPageLayoutManager);
        //添加分页
        mPagingScrollUtil.setUpRecycleView(mRecyclerView);
        mPagingScrollUtil.updateLayoutManger();

        mPagingScrollUtil.setOnPageChangeListener(this);
        //矩形
        mRectIndicator.setNumber(mDatas.size() / 8 + (mDatas.size() % 8 == 0 ? 0 : 1));
        mPagingScrollUtil.setRectIndicator(mRectIndicator);
        //添加分页指示器--圆形
        mCircleIndicator.setNumber(mDatas.size() / 8 + (mDatas.size() % 8 == 0 ? 0 : 1));
        mPagingScrollUtil.setCircleIndicator(mCircleIndicator);

    }

    protected void initData() {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            mDatas.add("" + i);
        }
    }

    @Override
    public void onPageChange(int index) {
        mTextView.setText("第" + index + "页");
        mRectIndicator.setOffset(index);
        mCircleIndicator.setOffset(index);
    }


}
