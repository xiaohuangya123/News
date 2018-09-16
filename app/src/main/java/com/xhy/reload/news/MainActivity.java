package com.xhy.reload.news;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.xhy.reload.news.model.Column;
import com.xhy.reload.news.utils.HttpUtil;
import com.xhy.reload.news.utils.JsonParseUtil;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TabLayout headNavTablayout;
    //新闻客户端导航栏所对应的主题内容
    private ViewPager mainArticleListViewpager;

    private List<Column> columnList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取导航栏
        columnList = getColumnsFromDb();

        mainArticleListViewpager = findViewById(R.id.id_mainArticleList);
        headNavTablayout = findViewById(R.id.headNav);
        MainContentAdapter adapter = new MainContentAdapter(columnList,getSupportFragmentManager());
        mainArticleListViewpager.setAdapter(adapter);
        headNavTablayout.setupWithViewPager(mainArticleListViewpager);

        headNavTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(MainActivity.this,
                        headNavTablayout.getSelectedTabPosition()+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private List<Column> getColumnsFromDb(){
        List<Column> cols = LitePal.findAll(Column.class);
        return cols;
    }

    //实现两次点击back键退出应用程序
    Long firstTime = 0L;
    @Override
    public void onBackPressed() {
        Long currentTime = System.currentTimeMillis();
        if(currentTime - firstTime < 2000){
            finish();
        }else {
            firstTime = currentTime;
        }
    }
}
