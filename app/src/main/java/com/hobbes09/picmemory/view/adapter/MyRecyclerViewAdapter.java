package com.hobbes09.picmemory.view.adapter;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.hobbes09.picmemory.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hobbes09 on 08/05/17.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;
    private TileItemClickListener mClickListener;
    Boolean[] matrixDisplayedFlags;

    ObjectAnimator flipAnimation;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, Fragment fragment, List<String> data, Boolean[] matrixDisplayedFlags) {
        this.mInflater = LayoutInflater.from(context);
        this.mClickListener = (TileItemClickListener)fragment;
        this.mContext = context;
        this.mData = data;
        this.matrixDisplayedFlags = matrixDisplayedFlags;

        flipAnimation = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.animator.flipping);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    public void setData(List<String> data) {
        mData = data;
    }

    public void setMatrixDisplayedFlags(Boolean[] matrixDisplayedFlags) {
        this.matrixDisplayedFlags = matrixDisplayedFlags;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mContext != null && matrixDisplayedFlags[position]){
            Picasso.with(mContext)
                    .load(mData.get(position))
                    .fit()
                    .centerInside()
                    .placeholder(R.drawable.picasso_placeholder)
                    .error(R.drawable.picasso_placeholder)
                    .into(holder.mImageView);
        }
        flipAnimation.setTarget(holder.mImageView);
        flipAnimation.setDuration(3000);
        flipAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        flipAnimation.start();

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public String getItem(int index) {
        return mData.get(index);
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.ivPic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onTileItemClick(view, getAdapterPosition());
        }
    }

    public interface TileItemClickListener {
        void onTileItemClick(View view, int position);
    }
}
