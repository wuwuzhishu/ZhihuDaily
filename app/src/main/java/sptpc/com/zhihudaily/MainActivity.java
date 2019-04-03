package sptpc.com.zhihudaily;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import sptpc.com.zhihudaily.adapter.NewsAdapter;
import sptpc.com.zhihudaily.model.News;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private ListView listView;
    private NewsAdapter newsAdapter ;
    private List<News.StoriesBean> storiesBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        listView = (ListView)findViewById(R.id.listview);
        storiesBeanList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this,R.layout.list_item_news,storiesBeanList);
        listView.setAdapter(newsAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = "https://news-at.zhihu.com/api/4/news/latest";
//                okhttpAndParseData(url);
                volleryAndParseData(url);
            }
        });


    }

    /*利用Vollery访问服务器端数据*/
    public void volleryAndParseData(String url){
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                storiesBeanList.clear();

                //解析数据
                try {
                    //接口中date属性为字符串，所以用getString或optString
//                    String date = jsonObject.getString("date");
                    String date = jsonObject.optString("date");
                    //接口中stories属性为[]，所以用数组JSONArray
//                    JSONArray storyArray = jsonObject.getJSONArray("stories");
                    JSONArray storyArray = jsonObject.optJSONArray("stories");
                    for (int i=0;i<storyArray.length();i++){
                        //接口中数组里面每个都是{}，所以是对象JSONObject
                        JSONObject storyObject = (JSONObject) storyArray.get(i);
                        String title = storyObject.getString("title");
                        //接口中数组每个对象里面image属性又是[]，所以是数组JSONArray
                        JSONArray imgJsonArray =  storyObject.getJSONArray("images");
                        ArrayList<String> imgList = new ArrayList<>();
                        for (int j=0;j<imgJsonArray.length();j++){
                            imgList.add(imgJsonArray.get(j).toString());
                        }
                        //实体类，Gson产生的实体类，用的是内部类。。。注意：考试时候如果不是内部类，直接用当前实体类即可
                        News.StoriesBean storiesBean = new News.StoriesBean();
                        storiesBean.setTitle(title);
                        storiesBean.setImages(imgList);
                        storiesBeanList.add(storiesBean);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.notifyDataSetChanged();
                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mQueue.add(jsonObjectRequest);
    }

    /*利用okhttp访问服务器端数据*/
    public void okhttpAndParseData(String url){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).get().build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                //返回的是一个Response，所以需要转换为字符串
                String str  = response.body().string();
                try {
                    //再把字符串转换为JSONOobject
                    JSONObject jsonObject = new JSONObject(str);
                    String date = jsonObject.optString("date");
//                    JSONArray storyArray = jsonObject.getJSONArray("stories");
                    JSONArray storyArray = jsonObject.optJSONArray("stories");
                    for (int i=0;i<storyArray.length();i++){
                        JSONObject storyObject = (JSONObject) storyArray.get(i);
                        String title = storyObject.getString("title");
                        JSONArray imgJsonArray =  storyObject.getJSONArray("images");
//                        String imgUrl = (String)imgJsonArray.get(0);
//                        Bitmap bitmap = BitmapFactory.decodeStream(new URL(imgUrl).openStream());
                        News.StoriesBean storiesBean = new News.StoriesBean();
                        storiesBean.setTitle(title);
//                        storiesBean.setImageBitmap(bitmap);
                        storiesBeanList.add(storiesBean);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

    }
}
