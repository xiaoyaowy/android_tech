package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Adapter.GoodsConnectAdapter;
import com.example.myapplication.Adapter.TechGoodsAdapter;
import com.example.myapplication.entity.TechGoods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BomActivity extends AppCompatActivity {

    String data;

    Button button;

    ListView listView;

    TechGoodsAdapter adapter;

    List<TechGoods> techGoodsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bom);
        button = findViewById(R.id.rbtn);
        listView = findViewById(R.id.blist_view);
        Intent intent = getIntent();
        data = intent.getStringExtra("bom");
        requestBom(data);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestBom(data);
            }
        });
    }

    // 返回上一个ac 搜索界面
    public void search(View view){
        System.out.println(111);
        System.out.println(data);
        requestBom(data);
    }

    private void requestBom(String data) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.80.62:8081/test/get/" + data)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSON(responseData);
                    adapter = new TechGoodsAdapter(BomActivity.this,R.layout.techgoods_item,techGoodsList);
                    refreshBom(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (reader != null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void parseJSON(String jsonData){
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String factory = jsonObject.getString("factory");
                String wuliao = jsonObject.getString("wuliao");
                String picture = jsonObject.getString("picture");
                String description = jsonObject.getString("description");
                String content = jsonObject.getString("content");
                String contentDesc = jsonObject.getString("contentDesc");
//                String buy = jsonObject.getString("buy");
                TechGoods goodsjson = new TechGoods(factory,wuliao,picture,description,content,contentDesc);
                techGoodsList.add(goodsjson);
                System.out.println(techGoodsList + "==============================================");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refreshBom(TechGoodsAdapter adapter){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
                System.out.println("进入run ====================================================");
                listView.invalidate();
            }
        });
    }


}