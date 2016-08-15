package com.example.text.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.text.R;
import com.example.text.WebViewActivity;
import com.example.text.beans.home;
import com.example.text.beans.stories;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘海风 on 2016/8/2.
 */

public class swipe extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private home home1;
    private home  home2;
    private  home home3;
    private  home home8;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecycleAdapter recycleAdapter;
    private List<stories> stories ;
    private  SwipeRefreshLayout swipe;
    public swipe() {
        // Required empty public constructor
    }
    public static swipe newInstance(int param1) {
        swipe fragment = new swipe();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     final   View view=inflater.inflate(R.layout.swiperefresh,container,false);
        swipe = (SwipeRefreshLayout) view.findViewById(R.id.swipe);

        stories = new ArrayList<>();
        recycleAdapter=new RecycleAdapter(getActivity(), stories);
        RecyclerView recycler= (RecyclerView) view.findViewById(R.id.recycle);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,true));
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(recycleAdapter);

        downLoadData();


        //滑动的监听器
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                stories.clear();
                downLoadData();
            }
        });



        return view;
    }

    private void downLoadData(){
        RequestParams params = new RequestParams("http://news-at.zhihu.com/api/4/news/latest");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
              //  Toast.makeText(x.app(),result,Toast.LENGTH_SHORT).show();


                home8 = JSON.parseObject(result,home.class);

                stories.addAll(home8.getStories());
                recycleAdapter.notifyDataSetChanged();
//                recycleAdapter.notifyItemRangeChanged(0,stories.size());
                swipe.setRefreshing(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    class RecycleAdapter extends RecyclerView.Adapter{

        private Context context1;
        private List<stories> home4;
        private LayoutInflater layoutInflater;
        public RecycleAdapter(Context context,List<stories> home5){
            this.context1=context;
            this.home4=home5;
            this.layoutInflater=LayoutInflater.from(context1);
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           View view=layoutInflater.inflate(R.layout.lv_home,parent,false);
            MyViewHolder holder=new MyViewHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            ((MyViewHolder)holder).tv.setText(home4.get(position).getTitle());
           x.image().bind(((MyViewHolder)holder).image,home4.get(position).getImages().get(0).toString(),imageOptions);
//            ((MyViewHolder)holder).tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                  //  home2.getStories().remove(position);
//                    RecycleAdapter.this.notifyItemRemoved(position);
//                    RecycleAdapter.this.notifyItemRangeChanged(position,home4.size());
//                }
//            });
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent(getActivity(), WebViewActivity.class);
//                    String url=home4.get(position)
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return home4.size();
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            tv=(TextView) view.findViewById(R.id.textView3);
            image= (ImageView) view.findViewById(R.id.imageView);

        }

    }
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
            .setRadius(DensityUtil.dip2px(5))
            // 如果ImageView的大小不是定义为wrap_content, 不要crop.
            .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
            // 加载中或错误图片的ScaleType
            //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.ic_launcher)
            .setFailureDrawableId(R.mipmap.ic_launcher)
            .build();


}
