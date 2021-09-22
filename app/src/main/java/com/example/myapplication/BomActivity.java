package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.zyapi.pos.PrinterDevice;
import android.zyapi.pos.utils.BitmapTools;

import com.example.admin.dashu_barcode.BarcodeCreater;
import com.example.myapplication.Adapter.TechGoodsAdapter;
import com.example.myapplication.entity.TechGoods;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BomActivity extends AppCompatActivity {

    String data;

    Button button;

    int bomNext;

    ListView listView;

    TechGoodsAdapter adapter;

    List<TechGoods> techGoodsList = new ArrayList<>();

    TechGoods send_print = new TechGoods();

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
                Intent intent = new Intent(BomActivity.this,PrintActivity.class);
                intent.putExtra("wuliao",send_print.getWuliao());
                intent.putExtra("description",send_print.getDescription());
                intent.putExtra("content",send_print.getContent());
                intent.putExtra("contentDesc",send_print.getContentDesc());
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TechGoods techGoods = techGoodsList.get(position);
//                Intent intent = new Intent(BomActivity.this,BomActivity1.class);
                String tempContent = techGoods.getContent();
                String tempContentDesc = techGoods.getContentDesc();
//                String tempWuliao = techGoods.getWuliao();
//                String tempPicture = techGoods.getPicture();
                adapter.setSelectedPosition(position);
                adapter.notifyDataSetInvalidated();
                requestBomNext(tempContent);
                SystemClock.sleep(1500);
                if (bomNext == 2){
                    //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                    AlertDialog.Builder builder = new AlertDialog.Builder(BomActivity.this);
                    //    设置Title的内容
                    builder.setTitle("提示");
                    //    设置Content来显示一个信息
                    builder.setMessage("该物料无BOM");
                    //    设置一个PositiveButton
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
//                            send_print.setWuliao(tempWuliao);
//                            send_print.setPicture(tempPicture);
                            send_print.setContent(tempContent);
                            send_print.setDescription(tempContentDesc);

                        }
                    });
                    //    显示出该对话框
                    builder.show();
                }else {
                    requestBom(tempContent);
                }

//                requestBom(temp);
//                Toast.makeText(MainActivity.this,goodsConnect.getWuliao(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 返回上一个ac 搜索界面
    public void search(View view){
        requestBom(data);
    }

    // 判断是否还有下个bom
    public int testNext(String nextData) {
        requestBomNext(nextData);
        int a = bomNext;
        return a;
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
                            .url("http://192.168.4.85:8081/test/get/" + data)
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

    private void requestBomNext(String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.4.85:8081/test/get/" + data)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    bomNext = responseData.length();
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
//            if (jsonArray.length() == 0){
//
//            }
            techGoodsList.clear();
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
                listView.invalidate();
            }
        });
    }


}