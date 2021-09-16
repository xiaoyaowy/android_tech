package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.GoodsConnect;
import com.example.myapplication.entity.TechGoods;

import java.util.List;

public class GoodsConnectAdapter extends ArrayAdapter<GoodsConnect> {

    private int resourceId;

    public GoodsConnectAdapter(Context context, int textViewResourceId, List<GoodsConnect> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        GoodsConnect goodsConnect=getItem(position); //获取当前项的Fruit实例

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        GoodsConnectAdapter.ViewHolder viewHolder;
        if (convertView == null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new GoodsConnectAdapter.ViewHolder();
            viewHolder.wuliao = view.findViewById(R.id.gwuliao);
            viewHolder.description = view.findViewById(R.id.gdescription);
            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(GoodsConnectAdapter.ViewHolder) view.getTag();
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.wuliao.setText(goodsConnect.getWuliao());
        viewHolder.description.setText(goodsConnect.getDescription());
        return view;
    }

    class ViewHolder{
        TextView wuliao;
        TextView description;
//        TextView buy;
    }


}
