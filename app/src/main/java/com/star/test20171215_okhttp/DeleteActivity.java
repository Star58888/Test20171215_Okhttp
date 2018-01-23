package com.star.test20171215_okhttp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DeleteActivity extends AppCompatActivity {

    EditText account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        account = (EditText)findViewById(R.id.account);
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
            Intent i = new Intent(DeleteActivity.this,UpdateActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.menu_add) {
            Intent i = new Intent(DeleteActivity.this,MainActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //getMethod method
    public void getMethod(View v){
        Log.d("test1","ttt");
        OkHttpClient client = new OkHttpClient();
        String param =  "account="+account.getText().toString();
        final Request request = new Request.Builder().url("http://192.168.58.09:8080/finalexam/delete.php?"+param).build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test2","fail");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(DeleteActivity.this,"連線失敗", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(DeleteActivity.this,"刪除成功", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Log.d("test5","1");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(DeleteActivity.this,"帳號不存在，刪除失敗", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
