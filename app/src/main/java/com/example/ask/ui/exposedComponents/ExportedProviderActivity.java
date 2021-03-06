package com.example.ask.ui.exposedComponents;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.ask.R;
import com.example.ask.util.FileUtil;
import com.example.ask.util.ResourceUtil;

import static com.example.ask.broadcast.AskBroadcastReceiver.category_id;

public class ExportedProviderActivity extends Activity {
    TextView target_uri;
    EditText column_name;
    EditText column_value;
    Button insert;
    TextView target_provider_name;
    TextView target_package_name;
    String target_provider;
    String target_package;
    String[] uris;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exported_provider_activity);
        target_uri = (TextView) findViewById(R.id.target_uri);
        target_package_name = (TextView) findViewById(R.id.target_package_name);
        target_provider_name = (TextView) findViewById(R.id.target_provider_name);
        column_name = (EditText) findViewById(R.id.column_name);
        column_value = (EditText) findViewById(R.id.column_value);
        insert = (Button) findViewById(R.id.insert);
        String target_provider_name_label = category_id + "_tag_name";
        String target_package_name_label = category_id + "_package_name";
        String target_uri_label = category_id + "_uris";
        target_provider = ResourceUtil.getStringByName(ExportedProviderActivity.this, target_provider_name_label);
        target_package = ResourceUtil.getStringByName(ExportedProviderActivity.this, target_package_name_label);
        uris = ResourceUtil.getStringArrayByName(ExportedProviderActivity.this, target_uri_label);

        if(target_package != null ){
            target_package_name.setText(target_package);
            FileUtil.saveResultToFile("????????????????????????" + target_package, ExportedProviderActivity.this);
        }
        if(target_provider != null ){
            target_provider_name.setText(target_provider);
            FileUtil.saveResultToFile("??????Content Provider????????????" + target_provider, ExportedProviderActivity.this);
        }
        if(uris != null ){
            target_uri.setText(uris[0]);
            FileUtil.saveResultToFile("????????????Content Provider???URI???" + uris[0], ExportedProviderActivity.this);
        }else {
            FileUtil.saveResultToFile("???????????????????????????Content Provider???URI???????????????", ExportedProviderActivity.this);
        }

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uris == null){
                    Toast.makeText(ExportedProviderActivity.this,"?????????????????????Content Provider???URI???????????????", Toast.LENGTH_SHORT).show();
                    FileUtil.saveResultToFile("?????????????????????Content Provider???URI???????????????", ExportedProviderActivity.this);
                }else if(column_name.getText().toString() == null || column_value.getText().toString() == null){
                    Toast.makeText(ExportedProviderActivity.this,"????????????????????????", Toast.LENGTH_SHORT).show();
                    FileUtil.saveResultToFile("??????????????????????????????????????????", ExportedProviderActivity.this);
                }else {
                    ContentValues values = new ContentValues();
                    values.clear();
                    values.put(column_name.getText().toString(), column_value.getText().toString());
                    Uri final_uri = Uri.parse(uris[0]);
                    getContentResolver().insert(final_uri, values);
                    FileUtil.saveResultToFile("?????????Content Provider???????????????", ExportedProviderActivity.this);
                    FileUtil.saveResultToFile("?????????????????????????????????????????????????????????????????????????????????????????????????????????", ExportedProviderActivity.this);
                    FileUtil.saveResultToFile("???????????????????????????????????????????????????", ExportedProviderActivity.this);
                }
            }
        });
    }
}
