package com.linewow.xhyy.actionbardemo2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LXR on 2016/10/10.
 */
public class ContentFrag extends Fragment {
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(),R.layout.fragment_content,null);
        recyclerView= (RecyclerView) view.findViewById(R.id.content_recycler1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(ContentFrag.this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        List<String> list=new ArrayList<>();
        for(int i=0;i<2;i++){
            list.add("abc"+i);
        }
        myAdapter=new MyAdapter(list);
        recyclerView.setAdapter(myAdapter);
    }
}
