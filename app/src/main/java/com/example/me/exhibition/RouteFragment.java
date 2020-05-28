package com.example.me.exhibition;


import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RouteFragment extends Fragment {

    private Spinner spinner;
    private String source, destination;
    private ArrayAdapter<String> adapter;
    private CheckBox xila, dongfang, aiji, huihua,diaosu,zhenbao;
    private String routes;
    private Button button;
    private TextView textView;

    public RouteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner_position);
        xila = (CheckBox) view.findViewById(R.id.cb_xila);
        dongfang = (CheckBox) view.findViewById(R.id.cb_dongfang);
        aiji = (CheckBox) view.findViewById(R.id.cb_aiji);
        huihua = (CheckBox) view.findViewById(R.id.cb_huihua);
        diaosu = (CheckBox) view.findViewById(R.id.cb_diaosu);
        zhenbao = (CheckBox) view.findViewById(R.id.cb_zhenbao);
        button = (Button) view.findViewById(R.id.btn_getRoute);
        textView = (TextView) view.findViewById(R.id.tv_route);
        destination = new String();
        List<String> spinnerList = new ArrayList<>();
        spinnerList.add("希腊和罗马艺术馆");
        spinnerList.add("东方艺术馆");
        spinnerList.add("埃及艺术馆");
        spinnerList.add("绘画馆");
        spinnerList.add("雕塑馆");
        spinnerList.add("珍宝馆");
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinnerList);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemString = spinner.getItemAtPosition(position).toString();
                source = itemString;
                Toast.makeText(getContext(), "你选中的是" + itemString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkBoxControl();

        recommendControl();
        return view;
    }

    private void recommendControl() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                OkHttpClient client = new OkHttpClient();
//                Request request = new Request.Builder().url("http://10.0.2.2:8088/route"+"?source="+source+"&destination="+destination).build();
//
//                Call call = client.newCall(request);
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//                        Looper.prepare();
//                        Toast.makeText(getContext(),"获取规划失败",Toast.LENGTH_SHORT).show();
//                        Looper.loop();
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String res = response.body().string();
//                        Log.d("response","返回值"+res);
//                        textView.setText(res);
//                    }
//                });
            }
        });
    }

    private void checkBoxControl() {
        xila.setOnCheckedChangeListener(new OnBoxCheckListener());
        dongfang.setOnCheckedChangeListener(new OnBoxCheckListener());
        aiji.setOnCheckedChangeListener(new OnBoxCheckListener());
        huihua.setOnCheckedChangeListener(new OnBoxCheckListener());
        diaosu.setOnCheckedChangeListener(new OnBoxCheckListener());
        zhenbao.setOnCheckedChangeListener(new OnBoxCheckListener());
    }

    class OnBoxCheckListener implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            String data = buttonView.getText().toString();
            if(isChecked){
                if(!destination.contains(data) ){
                    destination = destination + data;
                }
            }else {
                if (destination.contains(data)){
                    destination = destination.replace(data,"");
                }
            }
        }
    }
}
