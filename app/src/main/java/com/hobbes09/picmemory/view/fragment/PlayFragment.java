package com.hobbes09.picmemory.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hobbes09.picmemory.R;
import com.hobbes09.picmemory.model.pojos.PicFeed;
import com.hobbes09.picmemory.presenter.FetchPicsPresenter;
import com.hobbes09.picmemory.view.IPlayView;
import com.hobbes09.picmemory.view.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/** 
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends BaseFragment implements IPlayView {

    private OnFragmentInteractionListener mListener;

    private FetchPicsPresenter mFetchPicsPresenter;

    @BindView(R.id.tvHeader) TextView tvHeader;
    @BindView(R.id.rvGrid) RecyclerView rvGrid;
    @BindView(R.id.ivQuestion) ImageView ivQuestion;
    @BindView(R.id.btnNew) Button btnNew;

    Context mContext;
    int currentPage = 1;
    List<String> currentUrls;
    String currentCard;

    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlayFragment.
     */
    public static PlayFragment newInstance() {
        PlayFragment fragment = new PlayFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mFetchPicsPresenter = new FetchPicsPresenter(this);
        refreshPlay();
    }

    // Called to make network call to get fresh image set
    @Override
    @OnClick(R.id.btnNew)
    public void refreshPlay(){
        Toast.makeText(mContext, "Refreshing ..", Toast.LENGTH_SHORT).show();
        mFetchPicsPresenter.fetchPics(currentPage++);
    }

    // Called to change recycler view content
    @Override
    public void refeshAdapter(List<String> urls) {
        Toast.makeText(mContext, "Fetched : " + urls.size(), Toast.LENGTH_SHORT).show();
        currentUrls = urls;
    }

    @Override
    public void onFetchFailed() {
        Toast.makeText(mContext, "Unable to fetch", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onPlayFragmentInteraction();
    }
}
