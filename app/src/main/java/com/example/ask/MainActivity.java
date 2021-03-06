package com.example.ask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.ask.broadcast.AskBroadcastReceiver;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String goExploit = intent.getStringExtra("goExploit");
            switch (goExploit){
                case "全局文件可读":
                    Toast.makeText(MainActivity.this,"全局文件可读",Toast.LENGTH_LONG);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        System.out.println(R.string.file_world_readable);
        if (getResources().getString(R.string.file_world_readable).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_storage).setVisible(true);
        }
        if (getResources().getString(R.string.file_world_writable).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_storage).setVisible(true);
        }
        if (getResources().getString(R.string.preference_world_readable).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_shared_preferences).setVisible(true);
        }
        if (getResources().getString(R.string.preference_world_writable).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_shared_preferences).setVisible(true);
        }
        if (getResources().getString(R.string.external_storage).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_storage).setVisible(true);
        }
        if (getResources().getString(R.string.fragment_injection).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_fragment_injection).setVisible(true);
        }
        if (getResources().getString(R.string.exported_activity).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_exposed_component).setVisible(true);
        }
        if (getResources().getString(R.string.exported_service).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_exposed_component).setVisible(true);
        }
        if (getResources().getString(R.string.exported_provider).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_exposed_component).setVisible(true);
        }
        if (getResources().getString(R.string.exported_receiver).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_exposed_component).setVisible(true);
        }
        if (getResources().getString(R.string.webview).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_webview).setVisible(true);
        }
        if (getResources().getString(R.string.intent_scheme_url).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_intent_scheme_url).setVisible(true);
        }
        if (getResources().getString(R.string.dynamic_register_receiver).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_dynamic_Broadcast).setVisible(true);
        }
        if (getResources().getString(R.string.allow_backup).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_allow_backup).setVisible(true);
        }
        if (getResources().getString(R.string.debuggable).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_debuggable).setVisible(true);
        }
        if (getResources().getString(R.string.implicit_intent).equals("1")){
            navigationView.getMenu().findItem(R.id.nav_implicit_intent_hijacking).setVisible(true);
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_storage, R.id.nav_world_file,
                R.id.nav_shared_preferences, R.id.nav_fragment_injection,
                R.id.nav_exposed_component, R.id.nav_implicit_intent_hijacking,
                R.id.nav_webview, R.id.nav_intent_scheme_url,
                R.id.nav_dynamic_Broadcast, R.id.nav_allow_backup,
                R.id.nav_debuggable, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ask.goExploit");
        registerReceiver(receiver,intentFilter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
