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
import cn.edu.jlu.animation.data.AnimationDetailBangumiStaff;

/**
 * Created by paworks on 18-3-18.
 */

public class AnimationDetailBnagumiStaffAdapter extends
        RecyclerView.Adapter<AnimationDetailBnagumiStaffAdapter.ViewHolder>{

    private List<AnimationDetailBangumiStaff> mAnimationDetailBangumiStaff;
    private Context mContext;
    private Activity mActivity;
    private LayoutInflater inflater;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView staffImage;
        TextView staffName;

        public ViewHolder(View view) {
            super(view);
            staffImage = (ImageView) view.findViewById(R.id.detail_staff_recyclerview_jpg);
            staffName = (TextView) view.findViewById(R.id.detail_staff_recyclerview_person_name);
        }
    }

    public AnimationDetailBnagumiStaffAdapter(Activity mActivity, Context mContext,
                                              List<AnimationDetailBangumiStaff> animationDetailBangumiStaff) {
        this.mAnimationDetailBangumiStaff = animationDetailBangumiStaff;
        this.mContext = mContext;
        this.mActivity = mActivity;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_adapter_detail_bangumi_staff,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AnimationDetailBangumiStaff animationDetailBangumiStaff =
                mAnimationDetailBangumiStaff.get(position);
        String url = "http:116.196.116.15:8000/media/picture_person/" +
                Integer.toString(animationDetailBangumiStaff.getPerson_id()) + ".jpg";
        Glide.with(mContext).load(url).asBitmap().into(holder.staffImage);
        holder.staffName.setText(animationDetailBangumiStaff.getPerson_name_jp());
    }

    @Override
    public int getItemCount() {
        return mAnimationDetailBangumiStaff.size();
    }
}
