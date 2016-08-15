package com.example.text;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.text.fragment.Collect_fragment;
import com.example.text.fragment.collectfragment;
import com.example.text.fragment.homefragment;
import com.example.text.fragment.newfragment;
import com.example.text.fragment.readfragment;
import com.example.text.fragment.sciencefragment;
import com.example.text.fragment.swipe;

import org.xutils.x;

import java.util.List;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends FragmentActivity {
    private NavigationView navigation1;
    private Toolbar toolbar;
    private DrawerLayout  brawer1;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        toolbar();
        drawer();


    }
    /*
    * tollbar的布局及事件
    * */
    public void toolbar(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("日报");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setBackgroundColor(Color.BLUE);
        toolbar.inflateMenu(R.menu.menuread);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, swipe.newInstance(0)).commit();
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.science:
                        toolbar.getMenu().clear();
                        toolbar.setTitle("科学");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, sciencefragment.newInstance(0)).commit();
                        toolbar.inflateMenu(R.menu.menuhome);
                        break;
                    case R.id.home:
                        toolbar.getMenu().clear();
                        toolbar.setTitle("日报");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, swipe.newInstance(0)).commit();
                        toolbar.inflateMenu(R.menu.menuread);
                        break;
                    case R.id.news:
                        toolbar.getMenu().clear();
                        toolbar.setTitle("新闻");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, newfragment.newInstance(0)).commit();
                        toolbar.inflateMenu(R.menu.menuscience);
                        break;
                    case R.id.read:
                        toolbar.getMenu().clear();
                        toolbar.setTitle("阅读");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, readfragment.newInstance(null,null)).commit();
                        toolbar.inflateMenu(R.menu.menunews);
                        break;
                }
                return true;
            }
        });
    }
    /*
    * 侧滑菜单事件
    * */
    public  void drawer(){
        brawer1= (DrawerLayout) findViewById(R.id.drawer);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                brawer1.openDrawer(GravityCompat.START);
            }
        });

        navigation1= (NavigationView) findViewById(R.id.navigation);
        navigation1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        toolbar.setTitle("日报");
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.menunews);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame,swipe.newInstance(0)).commit();

                        break;
                    case R.id.science:
                        toolbar.setTitle("科学");
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.menuscience);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, sciencefragment.newInstance(0)).commit();

                        break;
                    case R.id.read:
                        toolbar.getMenu().clear();
                        toolbar.setTitle("阅读");
                        toolbar.inflateMenu(R.menu.menuread);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, readfragment.newInstance(null,null)).commit();                        break;


                    case R.id.news:
                        toolbar.setTitle("新闻");
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.menunews);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, newfragment.newInstance(0)).commit();                        break;

                    case R.id.collect:
                        toolbar.setTitle("收藏");
                        toolbar.getMenu().clear();
                        toolbar.inflateMenu(R.menu.menuread);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame, Collect_fragment.newInstance(null,null)).commit();                        break;

                }
                brawer1.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

}
