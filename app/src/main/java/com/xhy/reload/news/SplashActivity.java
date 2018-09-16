package com.xhy.reload.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xhy.reload.news.model.Article;
import com.xhy.reload.news.model.Column;
import com.xhy.reload.news.utils.HttpUtil;
import com.xhy.reload.news.utils.JsonParseUtil;
import com.xhy.reload.news.utils.Utils;

import org.litepal.LitePal;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {
    private ImageView launcherImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        launcherImg = findViewById(R.id.id_launcher_img);
        Glide.with(this).load(R.drawable.v).into(launcherImg);
        if(HttpUtil.isNetworkAvailable()){
            //先请求导航栏数据是否有变化，有则请求导航栏信息并存入数据库
            //如果没有变化且数据库中已经缓存则不发送请求后续直接从数据库中读取
            getColumnsFromServerAndSaveDbByRxjava2();

            // TODO: 2018/7/30 请求新闻客户端第一个栏目的文章列表
            getArticleListFromServerAndSaveToDb(1);

        }


        // TODO: 2018/7/30 请求服务器，是否播放广告，播放则下载广告图片并缓存 ，否则进入主页面

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        timer.schedule(timerTask,2000);
    }

    private void getColumnsFromServerAndSaveDbByRxjava2(){
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String columnStateJson = HttpUtil.sendOkhttpRequest("http://101.251.230.234:8080/news/state.json");
                String columnState = JsonParseUtil.parseColumnStateJson(columnStateJson);
                emitter.onNext(columnState);
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io())
          .flatMap(new Function<String, ObservableSource<String>>() {
              @Override
              public ObservableSource<String> apply(String s) throws Exception {
                  String colsJson = "";
                  if(s.equals("true") || LitePal.findAll(Column.class).size()<=0){
                      colsJson = HttpUtil.sendOkhttpRequest("http://101.251.230.234:8080/news/columns.json");
                      return Observable.just(colsJson);
                  }
              return Observable.just("columnNotChange");
           }
        }).observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(String colsJson) {
                if(!"columnNotChange".equals(colsJson)){
                    JsonParseUtil.parseColumnJsonWithGSON(colsJson);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"获取导航栏信息失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onComplete() { }
        });
    }

    private void getArticleListFromServerAndSaveToDb(int columnId){
        LitePal.deleteAll(Article.class);
        for (int i=0;i<30;i++){
            Article article = new Article();
            article.setTitle(Utils.getComContent()+Utils.getComContent());
            article.setAuthor(Utils.getAuthor());
            article.setTitleImg(Utils.getImgSrc());
            article.save();
        }
    }
}
