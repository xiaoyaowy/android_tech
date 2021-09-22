package com.example.myapplication;

import static android.os.Build.VERSION.SDK_INT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myapplication.Adapter.GoodsConnectAdapter;
import com.example.myapplication.Adapter.TechGoodsAdapter;
import com.example.myapplication.entity.GoodsConnect;
import com.example.myapplication.entity.TechGoods;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{

    String result;
    private SearchView searchView;
    private ListView listView;
    private List<TechGoods> techGoodsList = new ArrayList<>();
    private List<GoodsConnect> goodsConnectList = new ArrayList<>();
    private GoodsConnectAdapter adapter;

//    File cacheFile = new File(getExternalCacheDir().toString(),"cache");
//    //缓存大小为10M
//    int cacheSize = 10 * 1024 * 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView=findViewById(R.id.searchview);
        listView = findViewById(R.id.list_view);


//        if (SDK_INT < Build.VERSION_CODES.P) {
//            return;
//        }

//        try {
//            Method forName = Class.class.getDeclaredMethod("forName", String.class);
//            Method getDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
//            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(null, "dalvik.system.VMRuntime");
//            Method getRuntime = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "getRuntime", null);
//            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(vmRuntimeClass, "setHiddenApiExemptions", new Class[]{String[].class});
//            Object sVmRuntime = getRuntime.invoke(null);
//            setHiddenApiExemptions.invoke(sVmRuntime, new Object[]{new String[]{"L"}});
//        } catch (Throwable e) {
//            Log.e("[error]", "reflect bootstrap failed:", e);
//        }

//        设置该SearchView默认是否自动缩小为图标
        searchView.setIconifiedByDefault(false);
//        设置该SearchView显示搜索图标
        searchView.setSubmitButtonEnabled(true);
//        设置该SearchView内默认显示的搜索文字
        searchView.setQueryHint("查找");
//        为SearchView组件设置事件的监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //            单击搜索按钮时激发该方法
            @Override
            public boolean onQueryTextSubmit(String queryText) {
                if (queryText.length() > 0) {
                    goodsConnectList.clear();
                    initList(queryText);
                }
                return true;
            }
            //            用户输入时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
//                如果newText不是长度为0的字符串
//                if (TextUtils.isEmpty(newText)){
////                    清除ListView的过滤
//                    list.clearTextFilter();
//                }else {
////                    使用用户输入的内容对ListView的列表项进行过滤
//                    list.setFilterText(newText);
//                }
                return false;
            }
        });
//        sendRequest.setOnClickListener(this);
//        TechGoodsAdapter adapter = new TechGoodsAdapter(MainActivity.this,R.layout.techgoods_item,techGoodsList);
//        System.out.println(adapter + "==============================");
//        ListView listView = findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
//
//        listView.setTextFilterEnabled(true);
//        System.out.println(listView + "==============================");
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TechGoods techGoods=techGoodsList.get(position);
//                Toast.makeText(MainActivity.this,techGoods.getWuliao(),Toast.LENGTH_SHORT).show();
//            }
//        });

//        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GoodsConnect goodsConnect = goodsConnectList.get(position);
                Intent intent = new Intent(MainActivity.this,BomActivity.class);
                String data = goodsConnect.getWuliao();
                intent.putExtra("bom",data);
                startActivity(intent);
//                Toast.makeText(MainActivity.this,goodsConnect.getWuliao(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initList(String queryText){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.4.85:8081/code/get/" + queryText)
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONCodes(responseData);
                    adapter = new GoodsConnectAdapter(MainActivity.this,R.layout.goodsconnect,goodsConnectList);
                    refresh(adapter);
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

//    @Override
//    public void onClick(View v){
//        if (v.getId() == R.id.send_request){
//            sendRequestWidthOkHttp();
//        }
//    }

    public void initGoods(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.80.62:8081/test/get/210000303094")
                            .build();
                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                    System.out.println(response.body() + "body==========================");
                    String responseData = response.body().string();
                    parseJSON(responseData);
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

    private void sendRequestWidthOkHttp() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.80.62:8081/test/get/210000303094")
                            .build();
                    Response response = client.newCall(request).execute();
                    System.out.println(response);
                    System.out.println(response.body() + "body==========================");
                    String responseData = response.body().string();
                    parseJSON(responseData);
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

    private void parseJSONCodes(String responseData){
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String wuliao = jsonObject.getString("wuliao");
                String description = jsonObject.getString("description");
                GoodsConnect goodsConnect = new GoodsConnect(wuliao,description);
                goodsConnectList.add(goodsConnect);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void refresh(GoodsConnectAdapter adapter){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(adapter);
                listView.invalidate();
            }
        });
    }
}