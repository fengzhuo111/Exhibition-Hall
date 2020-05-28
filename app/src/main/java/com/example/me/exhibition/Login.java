package com.example.me.exhibition;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.example.me.exhibition.utils.MD5Tool;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    //标题
    private TextView tv_main_title;
    //返回按钮, 立即注册， 找回密码
    private TextView tv_back,tv_register,tv_find_psw;
    // 登录按钮
    private Button btn_login;
    //账号、密码的控件的获取值
    private String userName,userPassword,readPsw;
    //账号、密码的控件
    private EditText et_user_name,et_psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    /**
     * 获取界面控件
     */
    private void init(){
        tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back=(TextView) findViewById(R.id.tv_back);
        tv_register=(TextView) findViewById(R.id.tv_register);
        tv_find_psw= (TextView) findViewById(R.id.tv_find_psw);
        btn_login=(Button) findViewById(R.id.btn_login);
        et_user_name=(EditText) findViewById(R.id.et_user_name);
        et_psw=(EditText) findViewById(R.id.et_psw);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login.this.finish();
            }
        });

        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivityForResult(intent, 1);
            }
        });

        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面（此页面暂未创建）
            }
        });

        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=et_user_name.getText().toString().trim();
                userPassword=et_psw.getText().toString().trim();
//                String md5Psw= null;//对当前用户输入的密码进行MD5加密再进行比对判断
//                try {
//                    md5Psw = MD5Tool.getInstance().getMd5(password);
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                }

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(Login.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }else if(TextUtils.isEmpty(userPassword)){
                    Toast.makeText(Login.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    login(userName, userPassword);
                }
            }
        });
    }

    /**
     *从SharedPreferences中根据用户名读取密码
     */
    private void login(String userName, String userPassword){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://10.0.2.2:8080/login"+ "?userName="+userName).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("连接失败","message"+e.toString());
                Looper.prepare();
                Toast.makeText(Login.this,"fail",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("连接成功","message");
                String readPsw = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(Login.this,"从数据库获取密码成功",Toast.LENGTH_SHORT).show();
//                    }
//                });

                if(readPsw.equals("UserNotFound")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }else if(!userPassword.equals(readPsw)){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //保存登录状态
                    saveLoginStatus(true, userName);
                    //登录成功后关闭此页面进入主页
                    Intent data=new Intent();
                    data.putExtra("isLogin",true);
                    setResult(RESULT_OK,data);
                    Login.this.finish();
                    startActivity(new Intent(Login.this, MainActivity.class));
                    return;
                }

            }
        });
    }

    /**
     *保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status,String userName){
        //loginInfo表示文件名
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        editor.putBoolean("isLogin", status);//存入boolean类型的登录状态
        editor.putString("loginUserName", userName);//存入登录状态时的用户名
        editor.commit();//提交修改
    }

    /**
     * 注册成功的数据返回至此
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            //从注册界面传递过来的用户名
            String userName =data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName);
                //设置光标的位置
                et_user_name.setSelection(userName.length());
            }
        }
    }
}
