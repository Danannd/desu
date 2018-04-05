package cn.edu.jlu.animation.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.List;

import cn.edu.jlu.animation.R;
import cn.edu.jlu.animation.data.AnimationDetailBangumiCharacter;

/**
 * Created by paworks on 18-3-17.
 */

public class AnimationDetailBangumiCharacterAdapter extends
        RecyclerView.Adapter<AnimationDetailBangumiCharacterAdapter.ViewHolder> {

    private List<AnimationDetailBangumiCharacter> mAnimationDetailBangumiCharacter;
    private Context mContex;
    private Activity mActivity;
    private LayoutInflater inflater;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView characterImage;
        TextView characterName;
        ImageView personImage;
        TextView personName;


        public ViewHolder(View view) {
            super(view);
            characterImage = (ImageView) view.findViewById(R.id.detail_cast_recyclerview_jpg);
            characterName = (TextView) view.findViewById(R.id.detail_cast_recyclerview_character_name);
            personImage = (ImageView) view.findViewById(R.id.detail_cast_recyclerview_personjpg);
            personName = (TextView) view.findViewById(R.id.detail_cast_recyclerview_personname);
        }
    }

    public AnimationDetailBangumiCharacterAdapter(Activity mActivity, Context mContext,
                                                  List<AnimationDetailBangumiCharacter> animationDetailBangumiCharacter) {
        this.mAnimationDetailBangumiCharacter = animationDetailBangumiCharacter;
        this.mContex = mContext;
        this.mActivity = mActivity;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_adapter_detail_bangumi_character,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnimationDetailBangumiCharacter animationDetailBangumiCharacter =
                mAnimationDetailBangumiCharacter.get(position);
        String url = "http://116.196.116.15:8000/media/picture_character/" +
                Integer.toString(animationDetailBangumiCharacter.getCharacter_id()) + ".jpg";
        Glide.with(mContex).load(url).asBitmap().into(holder.characterImage);
        String url2 = "http://116.196.116.15:8000/media/picture_person/" +
                Integer.toString(animationDetailBangumiCharacter.getPerson_id()) + ".jpg";
        Glide.with(mContex).load(url2).asBitmap().into(holder.personImage);
        holder.characterName.setText(animationDetailBangumiCharacter.getCharacter_name_jp());
        holder.personName.setText(animationDetailBangumiCharacter.getPerson_name_jp());
    }

    @Override
    public int getItemCount() {
        return mAnimationDetailBangumiCharacter.size();
    }

}
