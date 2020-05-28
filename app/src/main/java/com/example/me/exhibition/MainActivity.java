package com.example.me.exhibition;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //界面内容
    private FrameLayout BodyLayout;
    //底部按钮栏
    public LinearLayout BottomLayout;
    //底部按钮
    private View HomeBtn;
    private View FunctionBtn;
    private View MyInfoBtn;
    private TextView tv_home;
    private TextView tv_function;
    private TextView tv_myInfo;
    private ImageView iv_home;
    private ImageView iv_function;
    private ImageView iv_myInfo;
    //标题栏
    private TextView tv_main_title;
    private RelativeLayout rl_title_bar;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Home home;
    Function function;
    MyInfo myInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        init();
        initBottomBar();
        setListener();
        setInitStatus();
    }

    /**
     * 设置初始选择
     */
    private void setInitStatus() {
        clearBottomImageState();
        setSelectedStatus(0);
        createView(0);
    }

    /**
     * 为每个按钮设置监听方法
     */
    private void setListener() {
        for (int i = 0; i < BottomLayout.getChildCount(); i++) {
            BottomLayout.getChildAt(i).setOnClickListener(this);
        }
    }

    /**
     * 获取底部导航栏上的控件
     */
    private void initBottomBar() {
        BottomLayout = (LinearLayout) findViewById(R.id.main_bottom_bar);
        HomeBtn = (RelativeLayout) findViewById(R.id.bottom_bar_course_btn);
        FunctionBtn = (RelativeLayout) findViewById(R.id.bottom_bar_exercises_btn);
        MyInfoBtn = (RelativeLayout) findViewById(R.id.bottom_bar_myinfo_btn);
        tv_home = (TextView) findViewById(R.id.bottom_bar_text_course);
        tv_function = (TextView) findViewById(R.id.bottom_bar_text_exercises);
        tv_myInfo = (TextView) findViewById(R.id.bottom_bar_text_myinfo);
        iv_home = (ImageView) findViewById(R.id.bottom_bar_image_course);
        iv_function = (ImageView) findViewById(R.id.bottom_bar_image_exercises);
        iv_myInfo = (ImageView) findViewById(R.id.bottom_bar_image_myinfo);
    }

    /**
     * 获取界面上的UI控件
     */
    private void init() {

        tv_main_title = (TextView) findViewById(R.id.tv_first_title);
        tv_main_title.setText("美术展览馆");
        rl_title_bar = (RelativeLayout) findViewById(R.id.first_title_bar);
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));

        initBodyLayout();

    }

    /**
     * 界面内容
     */
    private void initBodyLayout() {
        BodyLayout = (FrameLayout) findViewById(R.id.main_body);
    }

    /**
     * 控件的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //课程的点击事件
            case R.id.bottom_bar_course_btn:
                clearBottomImageState();
                selectDisplayView(0);
                break;
            //习题的点击事件
            case R.id.bottom_bar_exercises_btn:
                clearBottomImageState();
                selectDisplayView(1);
                break;
            //我的点击事件
            case R.id.bottom_bar_myinfo_btn:
                clearBottomImageState();
                selectDisplayView(2);
                break;
            default:
                break;
        }
    }

    /**
     * 显示对应的页面
     * @param i
     */
    private void selectDisplayView(int i) {
        removeAllView();
        createView(i);
        setSelectedStatus(i);
    }

    /**
     * 设置底部按钮选中状态
     * @param i
     */
    private void setSelectedStatus(int i) {
        switch (i){
            case 0:
                HomeBtn.setSelected(true);
                iv_home.setImageResource(R.drawable.first_icon_selected);
                iv_function.setImageResource(R.drawable.second_icon2);
                iv_myInfo.setImageResource(R.drawable.third_icon);
                tv_home.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("法国卢浮宫");
                break;
            case 1:
                FunctionBtn.setSelected(true);
                iv_function.setImageResource(R.drawable.second_icon_selected);
                iv_home.setImageResource(R.drawable.first_icon3);
                iv_myInfo.setImageResource(R.drawable.third_icon);
                tv_function.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("功能");
                break;
            case 2:
                MyInfoBtn.setSelected(true);
                iv_myInfo.setImageResource(R.drawable.third_icon_selected);
                iv_home.setImageResource(R.drawable.first_icon3);
                iv_function.setImageResource(R.drawable.second_icon2);
                tv_myInfo.setTextColor(Color.parseColor("#0097F7"));
                rl_title_bar.setVisibility(View.VISIBLE);
                tv_main_title.setText("我");
                break;
        }
    }



    /**
     * 选择视图
     * @param viewIndex
     */
    private void createView(int viewIndex) {
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (viewIndex){
            case 0:
                //TODO:首页
                home = new Home();
                fragmentTransaction.replace(R.id.main_body,home);
                break;
            case 1:
                //TODO:功能界面
                function = new Function();
                fragmentTransaction.replace(R.id.main_body,function);
                break;
            case 2:
                //TODO：我的界面
                myInfo = new MyInfo();
                fragmentTransaction.replace(R.id.main_body,myInfo);
                break;
        }
        fragmentTransaction.commit();
    }

    /**
     * 移除不需要的视图
     */
    private void removeAllView() {
        for (int i = 0;i<BodyLayout.getChildCount();i++){
            BodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

    /**
     * 清除底部按钮的选中状态
     */
    private void clearBottomImageState() {
        tv_home.setTextColor(Color.parseColor("#666666"));
        tv_function.setTextColor(Color.parseColor("#666666"));
        tv_myInfo.setTextColor(Color.parseColor("#666666"));

//        iv_course.setImageResource(R.drawable.main_course_icon);
//        iv_exercises.setImageResource(R.drawable.main_exercises_icon);
//        iv_myInfo.setImageResource(R.drawable.main_my_icon);

        for (int i = 0; i < BottomLayout.getChildCount(); i++) {
            BottomLayout.getChildAt(i).setSelected(false);
        }
    }

    protected long exitTime;//记录第一次点击时的时间
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){//按返回键、按下动作
            if ((System.currentTimeMillis() - exitTime) >2000){//导入java.lang的包
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_LONG).show();
                exitTime = System.currentTimeMillis();
            }else {
                MainActivity.this.finish();
                if (readLoginStatus()){
                    //如果退出此应用时是登录状态，则需要清除登录状态，同时需清除登录时的用户名
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 清除SharedPreferences中的登录状态
     */
    private void clearLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putBoolean("isLogin", false);//清除登录状态
        editor.putString("loginUserName", "");//清除登录时的用户名
        editor.commit();//提交修改
    }

    /**
     * 获取SharedPreferences中的登录状态
     * @return
     */
    private boolean readLoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin", false);
        return isLogin;
    }
}
