package com.androidex.bluetooth.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.androidex.basebluetooth.utils.BluetoothUtil;
import com.androidex.bluetooth.adapter.GroupFriendAdapter;
import com.androidex.bluetooth.common.AppConstant;
import com.androidex.bluetooth.manager.AppManager;
import com.androidex.bluetooth.mode.FriendInfo;
import com.androidex.bluetooth.mode.GroupInfo;
import com.androidex.bluetooth.utils.ToastUtil;
import com.androidex.utils.character.DateTime;
import com.androidex.utils.view.ActivityUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * ***********************************************
 * **                  _oo0oo_                  **
 * **                 o8888888o                 **
 * **                 88" . "88                 **
 * **                 (| -_- |)                 **
 * **                 0\  =  /0                 **
 * **               ___/'---'\___               **
 * **            .' \\\|     |// '.             **
 * **           / \\\|||  :  |||// \\           **
 * **          / _ ||||| -:- |||||- \\          **
 * **          | |  \\\\  -  /// |   |          **
 * **          | \_|  ''\---/''  |_/ |          **
 * **          \  .-\__  '-'  __/-.  /          **
 * **        ___'. .'  /--.--\  '. .'___        **
 * **     ."" '<  '.___\_<|>_/___.' >'  "".     **
 * **    | | : '-  \'.;'\ _ /';.'/ - ' : | |    **
 * **    \  \ '_.   \_ __\ /__ _/   .-' /  /    **
 * **====='-.____'.___ \_____/___.-'____.-'=====**
 * **                  '=---='                  **
 * ***********************************************
 * **              佛祖保佑  镇类之宝              **
 * ***********************************************
 *
 * @author 李永平[QQ:2428775145]
 */
public class MainActivity extends BaseChatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ExpandableListView mGroupFriendLv;
    private GroupFriendAdapter mGroupFriendAdapter;
    private List<GroupInfo> mGroupFriendListData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.androidex.bluetooth.R.layout.activity_main);
    }

    @Override
    protected void initWidget() {
        Toolbar toolbar = (Toolbar) findViewById(com.androidex.bluetooth.R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(com.androidex.bluetooth.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.startForwardActivity(MainActivity.this, AddFriendActivity.class);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(com.androidex.bluetooth.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.androidex.bluetooth.R.string.navigation_drawer_open, com.androidex.bluetooth.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(com.androidex.bluetooth.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mGroupFriendLv = (ExpandableListView) findViewById(com.androidex.bluetooth.R.id.friend_group_list);
    }

    @Override
    protected void initData() {
        mGroupFriendAdapter = new GroupFriendAdapter(mContext, mGroupFriendListData);
        mGroupFriendLv.setAdapter(mGroupFriendAdapter);
        mGroupFriendLv.expandGroup(0);

        if(BluetoothUtil.isSupportBle(mContext)){
            BluetoothUtil.enableBluetooth((Activity) mContext, 1);
        } else{
            ToastUtil.showLong(mContext, getString(com.androidex.bluetooth.R.string.phone_not_support_bluetooth));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppManager.getAppManager().appExit(mContext);
                }
            }, 3000);
        }
    }

    @Override
    protected void initEvent() {
        mGroupFriendLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                FriendInfo friendInfo = mGroupFriendListData.get(groupPosition).getFriendList().get(childPosition);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppConstant.FRIEND_INFO, friendInfo);
                ActivityUtil.startForwardActivity(MainActivity.this, ChatActivity.class, bundle, false);
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                findDevice();
            } else{
                AppManager.getAppManager().appExit(mContext);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onRestart() {
        if(BluetoothUtil.isSupportBle(mContext)){
            BluetoothUtil.enableBluetooth((Activity) mContext, 1);
        } else{
            ToastUtil.showLong(mContext, getString(com.androidex.bluetooth.R.string.phone_not_support_bluetooth));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppManager.getAppManager().appExit(mContext);
                }
            }, 3000);
        }
        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(com.androidex.bluetooth.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.androidex.bluetooth.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.androidex.bluetooth.R.id.menu_about) {
            displayAboutDialog();
            return true;
        } else if(id == com.androidex.bluetooth.R.id.menu_share){
            ToastUtil.showLong(mContext, getString(com.androidex.bluetooth.R.string.menu_share));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == com.androidex.bluetooth.R.id.nav_camera) {
            // Handle the camera action
        } else if (id == com.androidex.bluetooth.R.id.nav_gallery) {

        } else if (id == com.androidex.bluetooth.R.id.nav_slideshow) {

        } else if (id == com.androidex.bluetooth.R.id.nav_manage) {

        } else if (id == com.androidex.bluetooth.R.id.nav_share) {

        } else if (id == com.androidex.bluetooth.R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(com.androidex.bluetooth.R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayAboutDialog() {
        final int paddingSizeDp = 5;
        final float scale = getResources().getDisplayMetrics().density;
        final int dpAsPixels = (int) (paddingSizeDp * scale + 0.5f);

        final TextView textView = new TextView(this);
        final SpannableString text = new SpannableString(getString(com.androidex.bluetooth.R.string.about_dialog_text));

        textView.setText(text);
        textView.setAutoLinkMask(RESULT_OK);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);

        Linkify.addLinks(text, Linkify.ALL);
        new AlertDialog.Builder(this)
                .setTitle(com.androidex.bluetooth.R.string.menu_about)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, null)
                .setView(textView)
                .show();
    }

    private void findDevice(){
        // 获得已经保存的配对设备
        Set<BluetoothDevice> pairedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
        if (pairedDevices.size() > 0) {
            mGroupFriendListData.clear();
            GroupInfo groupInfo = new GroupInfo();
            groupInfo.setGroupName(BluetoothAdapter.getDefaultAdapter().getName());
            List<FriendInfo> friendInfoList = new ArrayList<>();
            for (BluetoothDevice device : pairedDevices) {
                FriendInfo friendInfo = new FriendInfo();
                friendInfo.setIdentificationName(device.getName());
                friendInfo.setDeviceAddress(device.getAddress());
                friendInfo.setFriendNickName(device.getName());
                friendInfo.setOnline(false);
                friendInfo.setJoinTime(DateTime.getStringByFormat(new Date(), DateTime.DEFYMDHMS));
                friendInfo.setBluetoothDevice(device);
                friendInfoList.add(friendInfo);
            }
            groupInfo.setFriendList(friendInfoList);
            groupInfo.setOnlineNumber(0);
            mGroupFriendListData.add(groupInfo);
            mGroupFriendAdapter.setGroupInfoList(mGroupFriendListData);
        }
    }

}
