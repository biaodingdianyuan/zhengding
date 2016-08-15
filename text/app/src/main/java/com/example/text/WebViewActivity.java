package com.example.text;

        import android.app.Activity;
        import android.content.ContentValues;
        import android.content.Context;
        import android.content.Intent;

        import android.content.res.Resources;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.os.Bundle;

        import android.support.v7.widget.Toolbar;
        import android.view.MenuItem;
        import android.webkit.WebView;
        import android.webkit.WebViewClient;
        import android.widget.Toast;

        import com.example.text.beans.result;
        import com.example.text.beans.science;
        import com.example.text.data.MyDBOpenHelper;

        import java.io.Serializable;
        import java.util.ArrayList;
        import java.util.List;

public class WebViewActivity extends Activity {
    private WebView wv;
    private Toolbar toolbar;
    private result result1;
    private SQLiteDatabase db;
    private StringBuilder sb;
    MyDBOpenHelper myDBOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        wv= (WebView) findViewById(R.id.science_wv);
        toolbar= (Toolbar) findViewById(R.id.Webtoolbar);
        toolbar.inflateMenu(R.menu.menu_web_view);
        Intent intent=getIntent();
        science science=new science();
        result1=new result();

        result1= (result) intent.getSerializableExtra("url");
        final String parent=intent.getStringExtra("parent");

        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl(result1.getUrl());
        //setContentView(wv);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               switch (item.getItemId()){
                   case R.id.collect_web:
                        myDBOpenHelper=new MyDBOpenHelper(WebViewActivity.this,"data",null,1);
                       db=myDBOpenHelper.getWritableDatabase();
                       sb = new StringBuilder();
                       String title=result1.getTitle_hide();
                       Cursor cursor = db.query("DATA1", null, "title=?", new String[]{title}, null, null, null);
                        if (cursor.getCount()==0) {

                          //  item.setEnabled(false);
                            ContentValues values1 = new ContentValues();
                            values1.put("title", result1.getTitle_hide());
                            values1.put("image", result1.getSmall_image());
                            values1.put("url", result1.getUrl());
                            values1.put("parent","science");
                            db.insert("DATA1", null, values1);
                            Toast.makeText(WebViewActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        }
                       else{
                            db.delete("DATA1","title=?",new String[]{title});
                            Toast.makeText(WebViewActivity.this,"已成功从收藏移除",Toast.LENGTH_SHORT).show();
                        }
                       break;
               }
                return true;
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (wv.canGoBack()) {
            wv.goBack();
        }
        super.onBackPressed();
    }


}
