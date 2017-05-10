package com.hobbes09.picmemory.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.hobbes09.picmemory.utils.NotifyCountDownTimer;
import com.hobbes09.picmemory.view.IPlayView;
import com.hobbes09.picmemory.view.adapter.MyRecyclerViewAdapter;
import com.hobbes09.picmemory.view.base.BaseFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
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
public class PlayFragment extends BaseFragment implements IPlayView, MyRecyclerViewAdapter.TileItemClickListener, NotifyCountDownTimer.CountDownTimerInterface {

    private OnFragmentInteractionListener mListener;

    private FetchPicsPresenter mFetchPicsPresenter;

    private static final int NUM_COLS = 3;

    TextView tvHeader;
    RecyclerView rvGrid;
    ImageView ivQuestion;
    Button btnNew;
    TextView tvCountDown;
    MyRecyclerViewAdapter mMyRecyclerViewAdapter;

    public PicMemoryApplication mApplication;
    Context mContext;
    int currentPage = 1;
    int indexQuestionImage = 0;
    int countCorrect = 0;
    boolean isPlaying = false;
    List<String> currentUrls;
    Boolean[] matrixDisplayedFlags = new Boolean[NUM_COLS * NUM_COLS];
    int numColumns;

    NotifyCountDownTimer mNotifyCountDownTimer;

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

    // Method executed only when mContext != null
    public void initializeUi(View view){
        // TODO : To be replaced with butterknife injection.
        // Not working for some unknown annotation conflict with dagger 2

        Arrays.fill(matrixDisplayedFlags, true);

        mNotifyCountDownTimer = new NotifyCountDownTimer(5 * 1000, 1000, this);

        btnNew = (Button) view.findViewById(R.id.btnNew);
        tvHeader = (TextView) view.findViewById(R.id.tvHeader);
        ivQuestion = (ImageView) view.findViewById(R.id.ivQuestion);
        tvCountDown = (TextView) view.findViewById(R.id.tvCountDown);

        rvGrid = (RecyclerView) view.findViewById(R.id.rvGrid);
        rvGrid.setLayoutManager(new GridLayoutManager(mContext, NUM_COLS));
        mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(mContext, this, new ArrayList<String>(), matrixDisplayedFlags);
        rvGrid.setAdapter(mMyRecyclerViewAdapter);

        ivQuestion.setVisibility(View.INVISIBLE);

        btnNew.setOnClickListener(view1 -> refreshPlay());
    }

    // Called to make network call to get fresh image set
    @Override
    public void refreshPlay(){
        if (tvHeader != null){
            tvHeader.setText("Round " + currentPage);
        }
        mFetchPicsPresenter.fetchPics(currentPage++);
        isPlaying = false;
        countCorrect = 0;
    }

    // Called to change recycler view content
    @Override
    public void refeshAdapter(List<String> urls) {
        currentUrls = urls;
        Arrays.fill(matrixDisplayedFlags, true);
        indexQuestionImage = 0;
        updateAdapterState(currentUrls, matrixDisplayedFlags);

        if (tvCountDown != null && mNotifyCountDownTimer != null){
            tvCountDown.setText("Starting in 15 sec ...");
            ivQuestion.setVisibility(View.INVISIBLE);
            mNotifyCountDownTimer.start();
        }
    }

    @Override
    public void onFetchFailed() {
        Toast.makeText(mContext, "Unable to fetch", Toast.LENGTH_SHORT).show();
    }

    // TODO : Ideally use notifyDataSetChanged method of adapter
    // TODO : Also can use notifyItemChanged for correct answers
    @Override
    public void updateAdapterState(List<String> data, Boolean[] matrixDisplayedFlags) {
        if (rvGrid != null && mMyRecyclerViewAdapter != null){
            mMyRecyclerViewAdapter = new MyRecyclerViewAdapter(mContext, this, currentUrls, matrixDisplayedFlags);
            rvGrid.setAdapter(mMyRecyclerViewAdapter);
//            mMyRecyclerViewAdapter.setData(currentUrls);
//            mMyRecyclerViewAdapter.setMatrixDisplayedFlags(matrixDisplayedFlags);
//            mMyRecyclerViewAdapter.notifyDataSetChanged();    // causes unknown bug. Leaving it for time restriction.
        }
    }

    @Override
    public void setQuestionImage(int index) {
        if (ivQuestion != null && mContext != null){
            ivQuestion.setVisibility(View.VISIBLE);
            ivQuestion.setTag(index);

            if (index < currentUrls.size())
                Picasso.with(mContext)
                        .load(currentUrls.get(index))
                        .fit()
                        .centerInside()
                        .placeholder(R.drawable.picasso_placeholder)
                        .error(R.drawable.picasso_placeholder)
                        .into(ivQuestion);
        }
    }

    @Override
    public int getAndUpdateQuestionIndex() {
        return indexQuestionImage++;
    }

    @Override
    public boolean isSubmissionCorrect(int index) {
        if (ivQuestion != null){
            if ((int)ivQuestion.getTag() == index)
                return true;
        }
        return false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTileItemClick(View view, int position) {
        // TODO : Check opaque logic here
        if (isPlaying){
            if (isSubmissionCorrect(position)){
                matrixDisplayedFlags[position] = true;
                countCorrect++;
                updateAdapterState(currentUrls, matrixDisplayedFlags);

                // Set new image unless game is complete
                if (isPlayOver()){
                    isPlaying = false;
                    Toast.makeText(mContext, "Great job !!", Toast.LENGTH_SHORT).show();
                    refreshPlay();
                }else {
                    setQuestionImage(getAndUpdateQuestionIndex());
                }

            }else {
                Toast.makeText(mContext, "Incorrect" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isPlayOver() {
        return countCorrect == NUM_COLS*NUM_COLS;
    }

    @Override
    public void onCountDownTick(long secUntilFinished) {
        tvCountDown.setText("Starting in " + secUntilFinished + " sec ...");
    }

    @Override
    public void onCountDownFinished() {
        tvCountDown.setText("");
        Arrays.fill(matrixDisplayedFlags, false);
        updateAdapterState(currentUrls, matrixDisplayedFlags);
        setQuestionImage(getAndUpdateQuestionIndex());
        isPlaying = true;
    }

    public interface OnFragmentInteractionListener {
        void onPlayFragmentInteraction();
    }
}
