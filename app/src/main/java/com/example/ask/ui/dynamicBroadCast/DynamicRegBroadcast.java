package com.example.ask.ui.dynamicBroadCast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;
import com.example.ask.entity.Message;
import com.example.ask.ui.preference.PreferenceRead;
import com.example.ask.ui.preference.PreferenceWrite;
import com.example.ask.util.MsgUtil;

import java.util.List;

public class DynamicRegBroadcast extends Fragment {
    private ListView listView;
    private List<Message> msgList;
    private MsgAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dynamic_reg_broadcast, container, false);

        listView = root.findViewById(R.id.list_item);
        msgList = MsgUtil.getList("DynamicRegBroadcast", getContext());
        adapter = new MsgAdapter(getActivity(), msgList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String meg = msgList.get(position).getContent();
                switch (meg) {
                    case "发送广播":
                        Intent intent = new Intent(getActivity(), DynamicRegBroadcastActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

        return root;
    }
}
