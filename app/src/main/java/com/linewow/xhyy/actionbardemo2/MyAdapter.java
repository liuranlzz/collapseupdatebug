package com.linewow.xhyy.actionbardemo2;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by LXR on 2016/10/10.
 */
public class MyAdapter extends BaseQuickAdapter<String> {
    public MyAdapter(List<String> data) {
        super(R.layout.item_photo,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        ImageView imageView=baseViewHolder.getView(R.id.photo_image);
        imageView.setImageResource(R.mipmap.icon_nihao);
        baseViewHolder.setText(R.id.photo_tv,s);
    }


}
