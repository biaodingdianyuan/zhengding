package com.example.text.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.text.beans.ReadingBean;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;


public class lv_read_fragement extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;



    public lv_read_fragement() {
        // Required empty public constructor
    }

    public static lv_read_fragement newInstance(String param1) {
        lv_read_fragement fragment = new lv_read_fragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

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
        x.Ext.init(getActivity().getApplication());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.lv_read_fragement, container, false);
        final ListView lv= (ListView) view.findViewById(R.id.lv_read);
        RequestParams params=new RequestParams("https://api.douban.com/v2/book/search?q="+mParam1);
        x.http().get(params,new Callback.CommonCallback<String>(){
            @Override
            public void onSuccess(String result) {
                //Toast.makeText(x.app(),result,Toast.LENGTH_SHORT).show();

              ReadingBean bean=  JSON.parseObject(result,ReadingBean.class);
                lv_read_adapter adapter=new lv_read_adapter();
                adapter.setList(bean);
                lv.setAdapter(adapter);


               //Toast.makeText(x.app(),bean.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(),"111",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


        return view;

    }

class lv_read_adapter extends BaseAdapter{
    ReadingBean read;
    public void setList(ReadingBean read){
        this.read=read;

    }
    @Override
    public int getCount() {
        return read.getBooks().length;
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
        view=getActivity().getLayoutInflater().inflate(R.layout.lv_read,null);
        TextView name,author,date,number,price;
        name= (TextView) view.findViewById(R.id.lv_read_name);
        author= (TextView) view.findViewById(R.id.lv_read_author);
        date= (TextView) view.findViewById(R.id.lv_read_date);
        number= (TextView) view.findViewById(R.id.lv_read_number);
        price= (TextView) view.findViewById(R.id.lv_read_price);
        ImageView image= (ImageView) view.findViewById(R.id.lv_read_image);
        ImageView read_collect= (ImageView) view.findViewById(R.id.read_collect);
            read_collect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });
        name.setText(read.getBooks()[i].getTitle());
      int w=read.getBooks()[i].getAuthor().length;
        String authors = "";
      for (int e=0;e<w;e++){
          authors=authors+read.getBooks()[i].getAuthor()[e].toString()+"  ";
      }
       author.setText(authors);
        date.setText(read.getBooks()[i].getPubdate());
        number.setText(read.getBooks()[i].getPages());
        price.setText(read.getBooks()[i].getPrice());

        x.image().bind(image,read.getBooks()[i].getImage(),imageOptions);
        return view;
    }
}
    ImageOptions imageOptions = new ImageOptions.Builder()
            .setSize(DensityUtil.dip2px(240), DensityUtil.dip2px(240))
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
