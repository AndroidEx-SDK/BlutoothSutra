package com.androidex.bluetooth.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.androidex.bluetooth.R;

public abstract class BaseActivity extends FragmentActivity implements
        View.OnClickListener {

    public Resources mResources;
    public Context mContext;
    private boolean mIsNeedBaseAnim = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mContext = this;
        mResources = mContext.getResources();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (mIsNeedBaseAnim) {
            overridePendingTransition(R.anim.push_in_left, R.anim.push_out_left);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (mIsNeedBaseAnim) {
            overridePendingTransition(R.anim.push_in_left, R.anim.push_out_left);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_in_left, R.anim.push_out_right);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setIsNeedBaseAnim(boolean isNeedBaseAnim) {
        mIsNeedBaseAnim = isNeedBaseAnim;
    }
}

