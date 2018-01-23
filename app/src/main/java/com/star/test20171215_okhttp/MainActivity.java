package com.star.test20171215_okhttp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText account, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        account = (EditText)findViewById(R.id.account);
        password = (EditText)findViewById(R.id.password);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_update) {
            Intent i = new Intent(MainActivity.this,UpdateActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.menu_delete) {
            Intent i = new Intent(MainActivity.this,DeleteActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //getMethod method
    public void getMethod(View v){
        Log.d("test1","ttt");
        OkHttpClient client = new OkHttpClient();
        String param =  "account="+account.getText().toString()+
                "&password="+password.getText().toString();
        final Request request = new Request.Builder().url("http://192.168.58.11:8080/exam/add.php?"+param).build();
//        Request request = new Request.Builder()
//                .url("http://192.168.58.14:8080/finalexam/add.php?"+param)
//                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test2","fail");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"連線失敗",Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("test3","ok");
                String json = response.body().string();
                Log.d("test4",json);
                int flag = json.compareTo("0");
                if (flag == 0){
                    Log.d("test5","0");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"新增失敗，帳號已存在",Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Log.d("test5","1");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,"新增成功",Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
