package cn.edu.jlu.animation;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by paworks on 18-1-15.
 */

public class AnimationStore extends Fragment{

    public static final int SHOW_RESPONSE = 0;
    private List<AnimationReceive> animationReceiveList = new ArrayList<>();
    List<AnimationReceive> animationList;
    AnimationAdapter1 adapter1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.animation_store, container, false);
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
                    animationList = gson.fromJson(response,
                            new TypeToken<List<AnimationReceive>>(){}.getType());
                    animationReceiveList.clear();
                    for(AnimationReceive animation : animationList) {
                        animationReceiveList.add(animation);
                    }
                    //animationReceiveList.addAll(animationList);
                    adapter1.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };




    @Override
    public void onActivityCreated(Bundle saveInstanceState) {
        super.onActivityCreated(saveInstanceState);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new AnimationAdapter1(getActivity(), getContext(), animationReceiveList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new SpaceItemDecoration(10));
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter1);

        sendRequestWithOkHttp();



    }


    private void sendRequestWithOkHttp(final String search_text) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://116.196.116.15:8000/animation/name_cn=" + search_text +"/")
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

    private void sendRequestWithOkHttp() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://116.196.116.15:8000/animation/this_season/")
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
