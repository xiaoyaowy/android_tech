package com.example.myapplication.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.TechGoods;

import java.util.List;

public class TechGoodsAdapter extends ArrayAdapter<TechGoods> {

    private int resourceId;

    private int selectedPosition = -1;

    public TechGoodsAdapter(Context context, int textViewResourceId, List<TechGoods> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        TechGoods techGoods=getItem(position); //获取当前项的Fruit实例

        // 加个判断，以免ListView每次滚动时都要重新加载布局，以提高运行效率
        View view;
        ViewHolder viewHolder;
        if (convertView == null){

            // 避免ListView每次滚动时都要重新加载布局，以提高运行效率
            view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

            // 避免每次调用getView()时都要重新获取控件实例
            viewHolder=new ViewHolder();
            viewHolder.factory = view.findViewById(R.id.factory);
            viewHolder.wuliao = view.findViewById(R.id.wuliao);
            viewHolder.picture = view.findViewById(R.id.picture);
            viewHolder.description = view.findViewById(R.id.description);
            viewHolder.content = view.findViewById(R.id.content);
            viewHolder.contentDesc = view.findViewById(R.id.contentDesc);
            //viewHolder.buy = view.findViewById(R.id.buy);

            // 将ViewHolder存储在View中（即将控件的实例存储在其中）
            view.setTag(viewHolder);
        } else{
            view=convertView;
            viewHolder=(ViewHolder) view.getTag();
        }

        if (selectedPosition == position) {
            viewHolder.factory.setBackgroundColor(Color.parseColor("#6bbbec"));
            viewHolder.wuliao.setBackgroundColor(Color.parseColor("#6bbbec"));
            viewHolder.picture.setBackgroundColor(Color.parseColor("#6bbbec"));
            viewHolder.description.setBackgroundColor(Color.parseColor("#6bbbec"));
            viewHolder.content.setBackgroundColor(Color.parseColor("#6bbbec"));
            viewHolder.contentDesc.setBackgroundColor(Color.parseColor("#6bbbec"));
        } else {
            viewHolder.factory.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.wuliao.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.picture.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.description.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.content.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.contentDesc.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        // 获取控件实例，并调用set...方法使其显示出来
        viewHolder.wuliao.setText(techGoods.getWuliao());
        viewHolder.description.setText(techGoods.getContent());
        viewHolder.contentDesc.setText(techGoods.getContentDesc());
//        viewHolder.buy.setText(techGoods.getBuy());
        return view;
    }

    class ViewHolder{

        TextView factory;
        TextView wuliao;
        TextView picture;
        TextView description;
        TextView content;
        TextView contentDesc;
        TextView buy;
    }

}
