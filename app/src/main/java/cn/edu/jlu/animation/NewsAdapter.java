package cn.edu.jlu.animation;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by paworks on 18-3-15.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> mNewsList;
    private Context mContext;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView newsName;
        ImageView newsImage;

        public ViewHolder(View view) {
            super(view);
            newsName = (TextView) view.findViewById(R.id.newslist_title);
            newsImage = (ImageView) view.findViewById(R.id.newslist_jpg);
        }
    }

    public NewsAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.mNewsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_listview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = mNewsList.get(position);
        String url = "http://116.196.116.15:8000/media/picture_news/"+
                Integer.toString(news.getId())+".jpg";
        Glide.with(mContext).load(url).asBitmap().into(holder.newsImage);
        holder.newsName.setText(news.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

}
