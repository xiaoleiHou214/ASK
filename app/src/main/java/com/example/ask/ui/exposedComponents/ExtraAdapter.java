package com.example.ask.ui.exposedComponents;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ask.R;

import java.util.List;

public class ExtraAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    String[] extra_keys;
    public ExtraAdapter(Context context, String[] extra_keys) {
        this.inflater=LayoutInflater.from(context);
        this.context=context;
        this.extra_keys = extra_keys;
    }

    @Override
    public int getCount() {
        return extra_keys.length;
    }

    @Override
    public Object getItem(int position) {
        return extra_keys[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView){
            convertView = inflater.inflate(R.layout.extra_item,null);
            holder =new ViewHolder(convertView,position);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(getItem(position).toString());
        return convertView;
    }

    class ViewHolder{
        TextView tv_name;
        EditText editText;
        public ViewHolder(View view,int pisition){
            tv_name = (TextView) view.findViewById(R.id.extra_key);
            editText= (EditText) view.findViewById(R.id.extra_value);
            editText.setTag(pisition);//存tag值
            editText.addTextChangedListener(new TextSwitcher(this));
        }
    }

    class TextSwitcher implements TextWatcher {
        private ViewHolder mHolder;

        public TextSwitcher(ViewHolder mHolder) {
            this.mHolder = mHolder;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int position = (int) mHolder.editText.getTag();//取tag值
            String extra_key = (String) getItem(position);
            String extra_value = s.toString();
            ((ExportedComponent)context).saveExtraData(extra_key, extra_value);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
