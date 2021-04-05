package com.example.ask.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ask.R;

public class FragmentInjection extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_injection, container, false);
        Button InjectFrag = (Button) root.findViewById(R.id.inject_frag);
        InjectFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: 修改替换为R.string.packageName
                Intent intent = new Intent();
                intent.setPackage("com.example.fragmentinjectionsecure");
                intent.setClassName("com.example.fragmentinjectionsecure", "com.example.fragmentinjectionsecure.MainActivity");
                intent.putExtra("fname", "com.example.fragmentinjectionsecure.EmailFragment");
                startActivity(intent);
            }
        });
        return root;
    }
}
