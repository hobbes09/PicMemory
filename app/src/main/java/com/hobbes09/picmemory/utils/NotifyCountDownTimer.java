package com.hobbes09.picmemory.utils;

import android.os.CountDownTimer;
import android.support.v4.app.Fragment;

/**
 * Created by hobbes09 on 09/05/17.
 */

public class NotifyCountDownTimer extends CountDownTimer {

    CountDownTimerInterface mCountDownTimerInterface;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and
     *                          {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public NotifyCountDownTimer(long millisInFuture, long countDownInterval, Fragment fragment) {
        super(millisInFuture, countDownInterval);
        this.mCountDownTimerInterface = (CountDownTimerInterface) fragment;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.mCountDownTimerInterface.onCountDownTick(millisUntilFinished / 1000);
    }

    @Override
    public void onFinish() {
        this.mCountDownTimerInterface.onCountDownFinished();
    }

    public interface CountDownTimerInterface {
        public void onCountDownTick(long secUntilFinished);

        public void onCountDownFinished();
    }
}
