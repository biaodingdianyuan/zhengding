package com.example.text.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.text.R;
import com.example.text.WebViewActivity;
import com.example.text.beans.result;
import com.example.text.beans.science;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class lv_science_fragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public lv_science_fragement() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static lv_science_fragement newInstance(String param1, String param2) {
        lv_science_fragement fragment = new lv_science_fragement();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.lv_science_fragement, container, false);
        final lv_scienceAdapter adapter=new lv_scienceAdapter();
        final ListView lv= (ListView) view.findViewById(R.id.lv_science);
        RequestParams params=new RequestParams("http://www.guokr.com/apis/minisite/article.json?retrieve_type=by_channel&channel_key="+mParam2);
        x.http().get(params, new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(final String result) {
              //  Toast.makeText(x.app(),result,Toast.LENGTH_SHORT).show();

              final science bean= JSON.parseObject(result,science.class);



                adapter.setList(bean);
               lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        List<science> list=new ArrayList<science>();

                        result result1=bean.getResult().get(i);
                        Intent intent=new Intent(getActivity(),WebViewActivity.class);
                        intent.putExtra("url",result1);
                        intent.putExtra("parent","science");

                        startActivity(intent);
                    }
                });

                Toast.makeText(x.app(),bean.toString(),Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               // Toast.makeText(x.app(),"11+",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
               // Toast.makeText(x.app(),"12+",Toast.LENGTH_SHORT);
            }

            @Override
            public void onFinished() {
                Toast.makeText(x.app(),"13+",Toast.LENGTH_SHORT);
            }
        });
        WebView wv= (WebView) view.findViewById(R.id.science_wv);


        return view;
    }




class lv_scienceAdapter extends BaseAdapter{
    science list=null;
    public void setList(science list){
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.getResult().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=getActivity().getLayoutInflater().inflate(R.layout.lv_science,null);
        TextView lv_science_title= (TextView) view.findViewById(R.id.lv_science_title);
        ImageView lv_science_image= (ImageView) view.findViewById(R.id.lv_science_image);
        TextView lv_science_hot= (TextView) view.findViewById(R.id.lv_science_hot);
        lv_science_title.setText(list.getResult().get(i).getTitle_hide());
        String count=list.getResult().get(i).getAuthors().get(0).getFollowers_count();
        if(count==null){
            count="0";
        }
        lv_science_hot.setText("热度："+count);
        x.image().bind(lv_science_image,list.getResult().get(i).getSmall_image(),imageOptions);
        return view;
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
