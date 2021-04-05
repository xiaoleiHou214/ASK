package com.example.ask.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ask.R;
import com.example.ask.entity.Message;

import java.util.List;

public class MsgAdapter extends BaseAdapter {
    private Context mContext;

    private LayoutInflater mInflater;
    private List<Message> mDatas;

    public MsgAdapter(Context context, List<Message> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_msg, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.mTvContent = convertView.findViewById(R.id.textview_content);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Message message = mDatas.get(position);
        viewHolder.mTvContent.setText(message.getContent());
        return convertView;
    }

    public static class ViewHolder {
        TextView mTvContent;
    }
}
