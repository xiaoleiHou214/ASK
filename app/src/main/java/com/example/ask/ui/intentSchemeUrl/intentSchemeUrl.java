package com.example.ask.ui.intentSchemeUrl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ask.R;
import com.example.ask.util.FileUtil;

public class intentSchemeUrl extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.intent_scheme_url, container, false);
        Button button = root.findViewById(R.id.exploit_intent_scheme_url);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action");
                Uri content_url = Uri.parse("http://10.0.2.2:5000/intentSchemeUrl");
                //Uri content_url = Uri.parse("\"intent:#Intent;action=com.example.addcontact;S.name=liuao;S.phoneNum=123222223;end\"");
                intent.setData(content_url);
                getActivity().startActivity(intent);
            }
        });

        return root;
    }
}
