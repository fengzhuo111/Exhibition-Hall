package com.example.me.exhibition;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicketFragment extends Fragment {


    private int num = 0;
    private String days;
    private String time = "上午";
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private ImageButton minus, plus;
    private EditText editTextNum;
    private TextView et_date;
    private Button selectDate, query, order;


    public TicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner_time);
        minus = (ImageButton) view.findViewById(R.id.ib_minus);
        plus = (ImageButton) view.findViewById(R.id.ib_plus);
        editTextNum = (EditText) view.findViewById(R.id.et_num);
        selectDate = (Button) view.findViewById(R.id.btn_selectDate);
        et_date = (TextView) view.findViewById(R.id.et_date);
        query = (Button) view.findViewById(R.id.btn_query);
        order = (Button) view.findViewById(R.id.btn_order);

        numControl();

        dateControl();

        btnControl();
        spinner.setPrompt("请选择");
        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("上午");
        spinnerList.add("下午");
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerList);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemString = spinner.getItemAtPosition(position).toString();
                time = itemString;
                Toast.makeText(getContext(), "你选中的是" + itemString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void btnControl() {
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url("http://10.0.2.2:8088/query"+"?date="+days+"&time="+time).build();
//
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Looper.prepare();
//                        Toast.makeText(getContext(),"查询失败",Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String number = response.body().string();
//                        Looper.prepare();
//                        Toast.makeText(getContext(),"剩余"+number+"张票",Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//                });
            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url("http://10.0.2.2:8088/order"+"?date="+days+"&time="+time).build();
//
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Looper.prepare();
//                        Toast.makeText(getContext(),"订票失败",Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String res = response.body().string();
//                        Log.d("response","返回值"+res);
//                        final boolean data = JSON.parseObject(res, boolean.class);
//                        if(data == true){
//                            Looper.prepare();
//                            Toast.makeText(getContext(),"订票成功",Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                        }else {
//                            Looper.prepare();
//                            Toast.makeText(getContext(),"订票失败",Toast.LENGTH_SHORT).show();
//                            Looper.loop();
//                        }
//
//                    }
//                });
            }
        });
    }

    private void dateControl() {
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar ca = Calendar.getInstance();
                int mYear = ca.get(Calendar.YEAR);
                int mMonth = ca.get(Calendar.MONTH);
                int mDay = ca.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (monthOfYear + 1 < 10) {
                            if (dayOfMonth < 10) {
                                days = new StringBuffer().append(year).append("年").append("0").
                                        append(monthOfYear + 1).append("月").append("0").append(dayOfMonth).append("日").toString();
                            } else {
                                days = new StringBuffer().append(year).append("年").append("0").
                                        append(monthOfYear + 1).append("月").append(dayOfMonth).append("日").toString();
                            }

                        } else {
                            if (dayOfMonth < 10) {
                                days = new StringBuffer().append(year).append("年").
                                        append(monthOfYear + 1).append("月").append("0").append(dayOfMonth).append("日").toString();
                            } else {
                                days = new StringBuffer().append(year).append("年").
                                        append(monthOfYear + 1).append("月").append(dayOfMonth).append("日").toString();
                            }

                        }
                        et_date.setText(days);
                    }
                };

                //TODO 调用时间选择器
                new DatePickerDialog(getContext(), dateListener, mYear, mMonth, mDay).show();
            }
        });
    }

    private void numControl() {
        minus.setOnClickListener(new OnButtonClickListener());
        plus.setOnClickListener(new OnButtonClickListener());
        editTextNum.addTextChangedListener(new OnTextChangeListener());
        minus.setTag("+");
        plus.setTag("-");
    }

    class OnButtonClickListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            //得到输入框里的数字
            String numString = editTextNum.getText().toString();
            //进行判断为空或是没文字设置为0
            if (numString == null || numString.equals(""))
            {
                num = 0;
                editTextNum.setText("0");
            } else{
                //当点击-的时候一次递减（num--）
                if (v.getTag().equals("-"))
                {
                    //判断（大于0的才能往下减）
                    if (++num < 0)  //先加，再判断
                    {
                        num--;
                        Toast.makeText(getContext(), "请输入一个大于0的数字",
                                Toast.LENGTH_SHORT).show();
                    } else
                    {
                        editTextNum.setText(String.valueOf(num));
                    }
                } else if (v.getTag().equals("+"))
                {
                    //判断（自减限制数不小于0）
                    if (--num < 0)  //先减，再判断
                    {
                        num++;
                        Toast.makeText(getContext(), "不能小于0",Toast.LENGTH_SHORT).show();
                    } else
                    {
                        editTextNum.setText(String.valueOf(num));
                    }
                }
            }
        }
    }

    class OnTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String numString = s.toString();
            if (numString == null || numString.equals("")) {
                num = 0;
            } else {
                int numInt = Integer.parseInt(numString);
                if (numInt < 0) {
                    Toast.makeText(getContext(), "请输入一个大于0的数字",
                            Toast.LENGTH_SHORT).show();
                } else {
                    //设置EditText光标位置 为文本末端
                    editTextNum.setSelection(editTextNum.getText().toString().length());
                    num = numInt;

                }
            }
        }
    }
}
