package com.example.ask.ui.exposedComponents;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;
import com.example.ask.entity.Message;
import com.example.ask.ui.worldFile.FileRead;
import com.example.ask.ui.worldFile.FileWrite;
import com.example.ask.util.MsgUtil;

import java.util.List;

public class ExposedComponents extends Fragment {
    private ListView listView;
    private List<Message> msgList;
    private MsgAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.exposed_components, container, false);
        listView = root.findViewById(R.id.list_item);
        msgList = MsgUtil.getList("ExposedComponents", getContext());
        adapter = new MsgAdapter(getActivity(), msgList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String meg = msgList.get(position).getContent();
                switch (meg) {
                    case "Activity暴露":
                        Intent intent2 = new Intent();
                        intent2.setClass(getContext(), ExportedComponent.class);
                        startActivity(intent2);
                        break;
                    case "Service暴露":
                        Intent intent3 = new Intent();
                        intent3.setClass(getContext(), ExportedComponent.class);
                        startActivity(intent3);
                        break;
                    case "BroadcastReceiver暴露":
                        Intent intent4 = new Intent();
                        intent4.setClass(getContext(), ExportedComponent.class);
                        startActivity(intent4);
                        break;
                    case "ContentProvider暴露":
                        Intent intent6 = new Intent();
                        intent6.setClass(getContext(), ExportedProviderActivity.class);
                        startActivity(intent6);
                        break;
                }
            }
        });
        return root;
    }
}
