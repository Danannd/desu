package cn.edu.jlu.animation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.edu.jlu.animation.adapter.AnimationDetailBangumiCharacterAdapter;
import cn.edu.jlu.animation.adapter.AnimationDetailBnagumiStaffAdapter;
import cn.edu.jlu.animation.data.AnimationDetailBangumiCharacter;
import cn.edu.jlu.animation.data.AnimationDetailBangumiStaff;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailFragmentActivity extends AppCompatActivity {

    public static final int SHOW_RESPONSE = 0;
    public static final int GET_SCORE = 1;
    public static final int GET_CHARACTER = 2;
    public static final int GET_STAFF = 3;

    AnimationReceive animationReceive;
    Score animationScore;
    private RecyclerView mCastList;
    private List<AnimationDetailBangumiCharacter> castList = new ArrayList<>();
    private List<AnimationDetailBangumiCharacter> newCastList = new ArrayList<>();

    private RecyclerView mStaffList;
    private List<AnimationDetailBangumiStaff> staffList = new ArrayList<>();
    private List<AnimationDetailBangumiStaff> newStaffList = new ArrayList<>();


    AnimationDetailBangumiCharacterAdapter castAdapter;
    AnimationDetailBnagumiStaffAdapter staffAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_detail_content);
        String ani_id_str = getIntent().getStringExtra("name_cn");
        mCastList = (RecyclerView) findViewById(R.id.detail_cast_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        mCastList.setLayoutManager(layoutManager);
        castAdapter = new AnimationDetailBangumiCharacterAdapter(this, this, castList);
        mCastList.setItemAnimator(new DefaultItemAnimator());
        mCastList.setAdapter(castAdapter);

        mStaffList = (RecyclerView) findViewById(R.id.detail_staff_recyclerview);
        LinearLayoutManager layoutManagerStaff = new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false);
        mStaffList.setLayoutManager(layoutManagerStaff);
        staffAdapter = new AnimationDetailBnagumiStaffAdapter(this, this, staffList);
        mStaffList.setItemAnimator(new DefaultItemAnimator());
        mStaffList.setAdapter(staffAdapter);



        sendRequestWithOkHttp(ani_id_str);
        getScoreWithOkHttp(ani_id_str);
        getCharacterWithOkHttp(ani_id_str);
        getStaffWithOkHttp(ani_id_str);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getScoreWithOkHttp(final String score_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client =  new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://116.196.116.15:8000/score/ani_id=" + score_id + "/")
                            .build();
                    Response response = client.newCall(request).execute();
                    String respnseData = response.body().string();

                    Message message = new Message();
                    message.what = GET_SCORE;
                    message.obj = respnseData;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    private void getCharacterWithOkHttp(final String ani_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client =  new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://116.196.116.15:8000/animation/detail/character/ani_id=" + ani_id + "/")
                            .build();
                    Response response = client.newCall(request).execute();
                    String respnseData = response.body().string();

                    Message message = new Message();
                    message.what = GET_CHARACTER;
                    message.obj = respnseData;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }


    private void getStaffWithOkHttp(final String ani_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client =  new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://116.196.116.15:8000/animation/detail/staff/ani_id=" + ani_id + "/")
                            .build();
                    Response response = client.newCall(request).execute();
                    String respnseData = response.body().string();

                    Message message = new Message();
                    message.what = GET_STAFF;
                    message.obj = respnseData;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }



    private void sendRequestWithOkHttp(final String search_text) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://116.196.116.15:8000/animation/ani_id=" + search_text + "/")
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



    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    Gson gson = new Gson();
                    animationReceive = gson.fromJson(response,
                            new TypeToken<AnimationReceive>(){}.getType());


                    Toolbar toolbar = (Toolbar) findViewById(R.id.coordinator_toolbar);
                    CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                            findViewById(R.id.collapsing_toolbar);
                    ImageView animeImageView = (ImageView) findViewById(R.id.animate_view);
                    String url = "http://116.196.116.15:8000/media/picture_l/" +
                            animationReceive.getAni_id() + ".jpg";
                    Glide.with(getApplicationContext()).load(url).into(animeImageView);
                    TextView animeTextView = (TextView) findViewById(R.id.animate_content_view);

                    setSupportActionBar(toolbar);
                    ActionBar actionBar = getSupportActionBar();
                    if(actionBar != null) {
                        actionBar.setDisplayHomeAsUpEnabled(true);
                    }
                    collapsingToolbarLayout.setTitle(animationReceive.getName_cn());
                    animeTextView.setText(animationReceive.getInformation());
                    break;

                case GET_SCORE:
                    String score_response = (String) msg.obj;
                    Gson score_gson = new Gson();
                    animationScore = score_gson.fromJson(score_response,
                            new TypeToken<Score>() {
                            }.getType());

                    TextView viewBgm_score_f = (TextView) findViewById(R.id.bgm_score_f);
                    TextView viewBgm_score = (TextView) findViewById(R.id.bgm_score);
                    displayScore("BGM评分:", viewBgm_score_f, viewBgm_score,
                            animationScore.getBgm_score());


                    break;

                case GET_CHARACTER:
                    String character_response = (String) msg.obj;
                    Gson character_gson = new Gson();
                    newCastList = character_gson.fromJson(character_response,
                            new TypeToken<List<AnimationDetailBangumiCharacter>>(){}.getType());
                    for(AnimationDetailBangumiCharacter cast : newCastList) {
                        castList.add(cast);
                    }
                    castAdapter.notifyDataSetChanged();
                    break;

                case GET_STAFF:
                    String staff_response = (String) msg.obj;
                    Gson staff_gson = new Gson();
                    newStaffList = staff_gson.fromJson(staff_response,
                            new TypeToken<List<AnimationDetailBangumiStaff>>(){}.getType());
                    for(AnimationDetailBangumiStaff staff : newStaffList) {
                        staffList.add(staff);
                    }
                    staffAdapter.notifyDataSetChanged();
                    break;

                default:
                    break;
            }


        }
    };

    private void displayScore(String str_f, TextView textView_f, TextView textView, double score) {
        if (score != 0.0) {
            textView_f.setText(str_f);
            String str = String.valueOf(score);
            textView.setText(str);
        }
        else {
            textView_f.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }

    }


}
