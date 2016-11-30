package com.linewow.xhyy.actionbardemo2;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by LXR on 2016/10/10.
 */
public class PhotoFrag extends Fragment implements BaseQuickAdapter.RequestLoadMoreListener {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private String TAG="PhotoFrag";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(),R.layout.fragment_photo,null);
        recyclerView= (RecyclerView) view.findViewById(R.id.photo_recycler);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initList();
    }

    private void initList() {
        List<String>list=new ArrayList<>();
        for(int i=0;i<2;i++){
            list.add("abc"+i);
        }
        GridLayoutManager manager=new GridLayoutManager(getActivity(),3);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        myAdapter=new MyAdapter(list);
        myAdapter.isFirstOnly(false);
        myAdapter.setOnLoadMoreListener(PhotoFrag.this);

        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Log.e("PhotoFrag","发送了");
                EventBus.getDefault().post("123","photo");
            }
        });

    }

    @Override
    public void onLoadMoreRequested() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(100);
            }
        }).start();

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<String>list=new ArrayList<String>();
            for(int i=0;i<10;i++){
                list.add("item"+i);
            }
            myAdapter.addData(list);
        }
    };
}
