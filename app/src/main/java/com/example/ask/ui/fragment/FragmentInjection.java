package com.example.ask.ui.fragment;

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

public class FragmentInjection extends Fragment {
    private ListView listView;
    private List<Message> msgList;
    private MsgAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_injection, container, false);

        listView=root.findViewById(R.id.list_item);
        msgList = MsgUtil.getList("FragmentInjection",getContext());
        adapter = new MsgAdapter(getActivity(),msgList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                String meg = msgList.get(position).getContent();
                switch (meg){
                    case "Fragment注入":
                        Intent intent = new Intent();
                        intent.setPackage("com.example.karantinain");
                        intent.setClassName("com.example.karantinain", "com.example.karantinain.Login.ForgotPassword.ResetPasswordActivity");
                        intent.putExtra("fname", "com.example.karantinain.Login.ForgotPassword.VerifikasiFragment");
                        startActivity(intent);
                        break;
                }
            }
        });
        return root;
    }
}
