package com.example.me.exhibition;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Home extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private String[] names = {"希腊和罗马艺术馆","东方艺术馆","埃及艺术馆"};
    private int[] icons = {R.drawable.xila,R.drawable.dongfang,R.drawable.aiji};
    private String[] introduces = {"简介：古希腊与古罗马艺术馆大约在1800年向公众展出，其藏品约有7000余件。",
    "东方艺术馆建于1881年，共有24个展厅，3500件展品。这些展品主要来自西亚和北非地区，包括叙利亚、黎巴嫩、巴基斯坦、伊朗等国。",
    "古埃及艺术馆建立于1826年，早于东方艺术馆，共有23个展厅，收藏珍贵文物达350件。这些文物包括古代尼罗河西岸居民使用的服饰、装饰物、玩具、乐器等，还有古埃及神庙的断墙、基门、木乃伊和公元前2600年的人头塑像等。"};

    public Home(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_first);
        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        return view;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{


        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.recycle_item_first,viewGroup,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            myViewHolder.name.setText(names[i]);
            myViewHolder.iv.setImageResource(icons[i]);
            myViewHolder.introduce.setText(introduces[i]);
        }

        @Override
        public int getItemCount() {
            return names.length;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView name;
            ImageView iv;
            TextView introduce;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.tv_name);
                iv = (ImageView) itemView.findViewById(R.id.iv_venue);
                introduce = (TextView) itemView.findViewById(R.id.tv_introduce);
            }
        }
    }



}
