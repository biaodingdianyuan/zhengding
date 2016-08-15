package com.example.text.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.text.R;
import com.example.text.beans.home;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;


public class homefragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public homefragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static homefragment newInstance(int param1) {
        homefragment fragment = new homefragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_homefrgment, container, false);

        final ListView lv= (ListView) view.findViewById(R.id.lv_home);
        final lv_homeAdapter adapter=new lv_homeAdapter();

        RequestParams params = new RequestParams("http://news-at.zhihu.com/api/4/news/latest");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess( String result) {
               // Toast.makeText(x.app(), result, Toast.LENGTH_LONG).show();
                //  System.out.print(result);
                home bean= JSON.parseObject(result,home.class);
                // bean = data.data(result);


              adapter.setlist(bean);
               lv.setAdapter(adapter);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }

        });





        return view;


    }

    class lv_homeAdapter extends BaseAdapter {
        home homeq;

        public  void  setlist(home hoo){
            this.homeq=  hoo;
        }

        @Override
        public int getCount() {
            return homeq.getStories().size();
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
             view=getActivity().getLayoutInflater().inflate(R.layout.lv_home,null);
            TextView text= (TextView) view.findViewById(R.id.textView3);
            ImageView image= (ImageView) view.findViewById(R.id.imageView);
            text.setText(homeq.getStories().get(i).getTitle());
            x.image().bind(image, homeq.getStories().get(i).getImages().get(0).toString(), imageOptions);

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
