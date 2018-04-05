package cn.edu.jlu.animation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by paworks on 18-1-18.
 */

public class AnimationAdapter1 extends RecyclerView.Adapter<AnimationAdapter1.ViewHolder> {

    private List<AnimationReceive> mAnimationReceivesList;
    private Context mContex;
    private Activity mActivity;
    private LayoutInflater inflater;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View animationView;
        ImageView animationImage;
        AutoMarqueeTextview animationName_cn;
        AutoMarqueeTextview animationName_jp;
        TextView animationTv;
        TextView animationState;

        public ViewHolder(View view) {
            super(view);
            animationView = view;
            animationImage = (ImageView) view.findViewById(R.id.animation_jpg);
            animationName_cn = (AutoMarqueeTextview) view.findViewById(R.id.animation_name_cn);
            animationName_jp = (AutoMarqueeTextview) view.findViewById(R.id.animation_name_jp);
            animationTv = (TextView) view.findViewById(R.id.animation_tv);
            animationState = (TextView) view.findViewById(R.id.animation_state);
        }
    }

    public AnimationAdapter1(Activity mActivity,Context mContex, List<AnimationReceive> animationReceivesList) {
        this.mAnimationReceivesList = animationReceivesList;
        this.mContex = mContex;
        this.mActivity = mActivity;
        inflater = LayoutInflater.from(mContex);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.animation_jpg, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationReceive animationReceive = mAnimationReceivesList.get(holder.getAdapterPosition());

                Intent intent = new Intent(mContex, DetailFragmentActivity.class);
                intent.putExtra("name_cn", animationReceive.getAni_id()+"");
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity).toBundle();
                mContex.startActivity(intent,bundle);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnimationReceive animationReceive = mAnimationReceivesList.get(position);
        String url = "http://116.196.116.15:8000/media/picture/" +
                animationReceive.getAni_id() + ".jpg";
        Glide.with(mContex).load(url).asBitmap().into(holder.animationImage);
        holder.animationName_jp.setText(animationReceive.getName_jp());
        holder.animationName_cn.setText(animationReceive.getName_cn());
        holder.animationTv.setText(animationReceive.getAni_type());
        if(animationReceive.getAni_type().equals("剧场版")) {
            holder.animationState.setText(animationReceive.getReleast_time());
        }
        else {
            holder.animationState.setText(animationReceive.getBroadcast_time());
        }


    }

    @Override
    public int getItemCount() {
        return mAnimationReceivesList.size();
    }

    private Bitmap setimage(ImageView view1) {
        Bitmap image = ((BitmapDrawable) view1.getDrawable()).getBitmap();
        Bitmap bitmap1 = Bitmap.createBitmap(image);
        return bitmap1;
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
