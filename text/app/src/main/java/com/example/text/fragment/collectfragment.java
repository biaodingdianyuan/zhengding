package com.example.text.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.text.R;
import com.example.text.WebViewActivity;
import com.example.text.beans.collectbean;
import com.example.text.beans.result;
import com.example.text.data.MyDBOpenHelper;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


public class collectfragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SQLiteDatabase db;
    private StringBuilder sb;
    MyDBOpenHelper myDBOpenHelper;
   private collectAdapter adapter=new collectAdapter();


    public collectfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment collectfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static collectfragment newInstance(String param1, String param2) {
        collectfragment fragment = new collectfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view=inflater.inflate(R.layout.fragment_collectfragment, container, false);
        collectbean bean=new collectbean();
        ListView lv= (ListView) view.findViewById(R.id.lv_collect);
        final List<collectbean> list=new ArrayList<collectbean>();
        myDBOpenHelper=new MyDBOpenHelper(getActivity(),"data",null,1);
        db=myDBOpenHelper.getWritableDatabase();
        Cursor cursor=db.query("DATA1",null,"parent=?",new String[]{mParam1},null,null,null);

        if (cursor.moveToFirst()) {

            do {
                bean=new collectbean();
                bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                bean.setImage(cursor.getString(cursor.getColumnIndex("image")));
               bean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
               list.add(bean);

            } while (cursor.moveToNext());
        }
        cursor.close();

             adapter.setlist(list);
             lv.setAdapter(adapter);
        final collectbean finalBean = bean;
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                result result2=new result();
                result2.setTitle_hide(list.get(i).getTitle());
                result2.setUrl(list.get(i).getUrl());
                result2.setSmall_image(list.get(i).getImage());
                Intent intent1=new Intent(getActivity(),WebViewActivity.class);
                intent1.putExtra("url",result2);
                startActivity(intent1);



            }
        });
        //长按
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                View dialogview=getActivity().getLayoutInflater().inflate(R.layout.dialog_delete,null);
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        getActivity());

                builder.setView(dialogview);
                builder.create();
                final AlertDialog Dialog = builder.show();
                TextView delete= (TextView) dialogview.findViewById(R.id.dialog_delete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title=list.get(i).getTitle();
                         adapter.removelist(i);
                        adapter.notifyDataSetChanged();
                        db.delete("DATA1","title=?",new String[]{title});
                        Toast.makeText(getActivity(),"已成功从收藏移除",Toast.LENGTH_SHORT).show();
                        Dialog.dismiss();
                    }
                });
                return true;
            }
        });

        return view;
    }
    class  collectAdapter extends BaseAdapter{
    List<collectbean> list;
        public  void removelist(int position){
            list.remove(position);
        }
        public void setlist(List<collectbean> list){
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
            view =getActivity().getLayoutInflater().inflate(R.layout.lv_home,null);
            TextView text= (TextView) view.findViewById(R.id.textView3);
            ImageView image= (ImageView) view.findViewById(R.id.imageView);
            text.setText(list.get(i).getTitle());
            x.image().bind(image, list.get(i).getImage(), imageOptions);


            return view;
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




}
