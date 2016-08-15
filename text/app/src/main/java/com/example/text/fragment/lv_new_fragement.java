package com.example.text.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaControllerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.text.R;
import com.example.text.WebViewActivity;
import com.example.text.beans.news_bean;
import com.example.text.beans.result;
import com.example.text.data.data_news1;

import org.xmlpull.v1.XmlPullParserException;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static android.R.attr.data;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link lv_new_fragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lv_new_fragement extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public lv_new_fragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment lv_new_fragement.
     */
    // TODO: Rename and change types and number of parameters
    public static lv_new_fragement newInstance(String param1,String param2) {
        lv_new_fragement fragment = new lv_new_fragement();
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
        View view=inflater.inflate(R.layout.fragment_lv_new_fragement, container, false);
        RequestParams params=new RequestParams(mParam1);
        final data_news1 data=new data_news1();
        final ListView lv= (ListView) view.findViewById(R.id.lv_new_fragement);
        final lv_news_fragementAdapter adapter=new lv_news_fragementAdapter();
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
               // Toast.makeText(x.app(),result.toString(),Toast.LENGTH_SHORT).show();
                try {
                 final   List<news_bean> list=data.getlist(result);
                       adapter.setList(list);
                        lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String url=list.get(i).getLink();
                            Intent intent=new Intent(getActivity(), WebViewActivity.class);
                            intent.putExtra("url",url);
                            startActivity(intent);
                        }
                    });
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               // Toast.makeText(x.app(),"645+",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        }) ;

        return view;
    }
class lv_news_fragementAdapter extends BaseAdapter{
    List<news_bean> list;
    public void setList(List<news_bean> list){
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
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
     view=getActivity().getLayoutInflater().inflate(R.layout.lv_news,null);
        TextView lv_news_title,lv_news_description,lv_news_date;
        lv_news_title= (TextView) view.findViewById(R.id.lv_news_title);
        lv_news_description= (TextView) view.findViewById(R.id.lv_news_description);
        lv_news_date= (TextView) view.findViewById(R.id.lv_news_date);
        lv_news_title.setText(list.get(i).getTitle());
        lv_news_description.setText(list.get(i).getDescription());
     //   lv_news_date.setText(list.get(i).ge);
        return view;
    }
}



}
