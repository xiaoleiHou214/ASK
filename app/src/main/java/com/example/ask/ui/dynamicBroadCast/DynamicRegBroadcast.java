package com.example.ask.ui.dynamicBroadCast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ask.R;

public class DynamicRegBroadcast extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dynamic_reg_broadcast, container, false);
        Button sendBroadcastBtn = root.findViewById(R.id.send_broadcast);
        sendBroadcastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("edu.ksu.cs.action.EMAIL");
                intent.putExtra("email", "rookie@malicious.com");
                intent.putExtra("text", "I can send email without any permissions");
                //Activity.sendBroadcast(intent);

            }
        });
        return root;
    }
}
