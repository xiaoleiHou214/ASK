package com.example.ask.ui.worldFile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ask.R;
import com.example.ask.adapter.MsgAdapter;
import com.example.ask.entity.Message;
import com.example.ask.util.MsgUtil;

import java.util.List;


public class WorldFile extends Fragment {

    private ListView listView;
    private List<Message> msgList;
    private MsgAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_world_file, container, false);

        listView = root.findViewById(R.id.list_item);
        msgList = MsgUtil.getList("WorldFile", getContext());
        adapter = new MsgAdapter(getActivity(), msgList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String meg = msgList.get(position).getContent();
                switch (meg) {
                    case "全局文件可读":
                        Intent intent = new Intent(getActivity(), com.example.ask.ui.worldFile.FileRead.class);
                        startActivity(intent);
                        break;
                    case "全局文件可写":
                        Intent intent1 = new Intent(getActivity(), FileWrite.class);
                        startActivity(intent1);
                        break;
                }
            }
        });

        return root;
    }

}
