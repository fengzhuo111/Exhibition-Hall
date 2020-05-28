package com.example.me.exhibition;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.me.exhibition.utils.MD5Tool;
import com.example.me.exhibition.utils.NetStateUtil;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity {

//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //标题
    private TextView tv_main_title;
    //返回按钮
    private TextView tv_back;
    //注册按钮
    private Button btn_register;
    //账号、密码、再次输入的密码的控件
    private EditText et_user_name,et_psw,et_psw_again;
    //账号、密码、再次输入的密码的控件的获取值
    private String userName,passWord,pswAgain;
    //标题布局
    private RelativeLayout rl_title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        initPage();
    }

    private void initPage() {
        //从main_title_bar.xml页面布局中获取对应的UI控件
        tv_main_title = (TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        tv_back = (TextView) findViewById(R.id.tv_back);
        rl_title_bar = (RelativeLayout) findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //从activity_register.xml页面布局中获得对应的UI控件
        btn_register=(Button) findViewById(R.id.btn_register);
        et_user_name=(EditText) findViewById(R.id.et_username);
        et_psw=(EditText) findViewById(R.id.et_pwd);
        et_psw_again=(EditText) findViewById(R.id.et_pwd_again);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register.this.finish();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(Register.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(passWord)){
                    Toast.makeText(Register.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(Register.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else if(!passWord.equals(pswAgain)){
                    Toast.makeText(Register.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
                    return;
                } else if(!NetStateUtil.checkNetworkState(Register.this)){
                    Toast.makeText(Register.this,"网络连接错误", Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    //把账号、密码和账号保存
                    try {
                        saveUserInfo(userName, passWord);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        passWord=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();
    }

    /**
     *保存账户密码，判断账户是否存在逻辑放在服务器端
     */
    private void saveUserInfo(final String userName, String passWord) throws NoSuchAlgorithmException {
        String md5Psw= MD5Tool.getInstance().getMd5(passWord);//把密码用MD5加密
//        发起http请求
        OkHttpClient client = new OkHttpClient();
//        创建表单对象，用于传输数据对象
//        RequestBody formBody = new FormBody.Builder().add("userName",userName).add("userPassword", md5Psw).build();
//        +"?userName="+ userName + "&userPassword=" + md5Psw
//        post方式发起请求
//        直接
        Request request = new Request.Builder().url("http://10.0.2.2:8080/register"+"?userName="+ userName + "&userPassword=" + passWord).build();
//        创建Call接口
        Call call = client.newCall(request);

//        异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                Log.d("call","请求失败"+e.toString());
                Looper.prepare();
                Toast.makeText(Register.this,"fail", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String responseData = response.body().string();
                Log.d("response","返回值"+responseData);
                final boolean data = JSON.parseObject(responseData, boolean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data == true){
                            Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("userName", userName);
                            setResult(RESULT_OK, intent);
                            //RESULT_OK为Activity系统常量，状态码为-1，表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                            Register.this.finish();
                        }else{
                            Toast.makeText(Register.this, "账户已被注册", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


}
