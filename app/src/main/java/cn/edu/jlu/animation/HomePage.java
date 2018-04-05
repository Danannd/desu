package cn.edu.jlu.animation;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by paworks on 18-1-15.
 */

public class HomePage extends Fragment {

    public static final int SHOW_RESPONSE = 0;
    private List<News> newsList = new ArrayList<>();
    List<News> newsListGet;
    NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepage, container, false);
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    Gson gson = new Gson();
                    newsListGet = gson.fromJson(response,
                            new TypeToken<List<News>>(){}.getType());
                    newsList.clear();
                    for(News news : newsListGet) {
                        newsList.add(news);
                    }
                    newsAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };





    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.news_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        newsAdapter = new NewsAdapter(getContext(), newsList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(20));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(newsAdapter);
        sendRequestWithOkHttp();

    }

    private void sendRequestWithOkHttp() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://116.196.116.15:8000/news/")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();


                    Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    message.obj = responseData;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }


}
