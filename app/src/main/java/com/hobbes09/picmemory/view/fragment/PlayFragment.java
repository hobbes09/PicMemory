package com.hobbes09.picmemory.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hobbes09.picmemory.PicMemoryApplication;
import com.hobbes09.picmemory.R;
import com.hobbes09.picmemory.presenter.FetchPicsPresenter;
import com.hobbes09.picmemory.view.IPlayView;
import com.hobbes09.picmemory.view.base.BaseFragment;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;

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

    TextView tvHeader;
    RecyclerView rvGrid;
    ImageView ivQuestion;
    Button btnNew;

    public PicMemoryApplication mApplication;
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
    public static PlayFragment newInstance(PicMemoryApplication mApplication) {
        PlayFragment fragment = new PlayFragment();
        fragment.mApplication = mApplication;
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
        //unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mContext != null){

            initializeUi(view);

            mFetchPicsPresenter = new FetchPicsPresenter(mApplication, this);
            refreshPlay();
        }
    }

    public void initializeUi(View view){
        // TODO : To be replaced with butterknife injection.
        // Not working for some unknown annotation conflict.

        btnNew = (Button) view.findViewById(R.id.btnNew);
        tvHeader = (TextView) view.findViewById(R.id.tvHeader);
        rvGrid = (RecyclerView) view.findViewById(R.id.rvGrid);
        ivQuestion = (ImageView) view.findViewById(R.id.ivQuestion);

        btnNew.setOnClickListener(view1 -> refreshPlay());
    }

    // Called to make network call to get fresh image set
    @Override
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
