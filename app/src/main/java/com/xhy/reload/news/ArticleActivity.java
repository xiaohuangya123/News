package com.xhy.reload.news;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xhy.reload.news.model.Article;
import com.xhy.reload.news.model.Comment;
import com.xhy.reload.news.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ArticleActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ArticleActivity";
    private WebView articleContentWebView;
    private RelativeLayout articleDetailLikeNumRL;
    private TextView articleDetailLikeNumTv;
    private ImageView articleDetailLikeNumImg;
    private RecyclerView articleHotCommentRv;
    private Toolbar articleDetailToolbar;
    private NestedScrollView articleDetailNestedScrollView;
    private TextView articleDetailCommentNumTv;
    private ConstraintLayout articleDetailHeadConstraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        //文章详情页Toolbar
        articleDetailToolbar = findViewById(R.id.id_article_detail_toolbar);
        setSupportActionBar(articleDetailToolbar);

        //文章详情页右上角评论数量textview实现
        articleDetailCommentNumTv = findViewById(R.id.id_article_detail_comment_num_tv);
        articleDetailCommentNumTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, CommentListActivity.class);
                startActivity(intent);
            }
        });

        //文章详情页文章具体内容webview实现
        articleContentWebView = findViewById(R.id.article_content);
        articleContentWebView.setWebViewClient(new WebViewClient());
        articleContentWebView.loadDataWithBaseURL(null, Utils.getArticleHtml(), "text/html", "UTF-8", null);

        //文章详情页喜欢人数按钮
        articleDetailLikeNumRL = findViewById(R.id.id_article_detail_likenum_RL);
        articleDetailLikeNumTv = findViewById(R.id.id_article_detail_likenum_tv);
        articleDetailLikeNumImg = findViewById(R.id.id_article_detail_likenum_img);
        articleDetailLikeNumTv.setOnClickListener(this);

        //文章热门跟帖评论列表
        articleHotCommentRv = findViewById(R.id.id_article_detail_hotcomment_rv);
        //解决NestedScrollView嵌套RecyclerView时，RecyclerView滑动卡顿问题
        articleHotCommentRv.setNestedScrollingEnabled(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        articleHotCommentRv.setLayoutManager(manager);
        List<Comment> hotCommentList = getHotCommentList(1);
        ArticleHotCommentAdapter adapter = new ArticleHotCommentAdapter(hotCommentList);
        articleHotCommentRv.setAdapter(adapter);

        //文章详情页NestedScrollView滚动事件监听
        articleDetailHeadConstraintLayout = findViewById(R.id.id_artilce_detail_head_constraintlayout);
        articleDetailNestedScrollView = findViewById(R.id.id_article_detail_nestedscrollview);
        articleDetailNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int articleDetailHeadHeight = articleDetailHeadConstraintLayout.getMeasuredHeight();
                //向上滚动
                if(scrollY > oldScrollY && scrollY > articleDetailHeadHeight){
                    articleDetailCommentNumTv.setText("已有8988人跟帖");
                    articleDetailCommentNumTv.setSelected(true);
                }
                //向下滚动
                if(scrollY < oldScrollY && scrollY < articleDetailHeadHeight){
                    articleDetailCommentNumTv.setText("8988跟帖");
                    articleDetailCommentNumTv.setSelected(false);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_article_detail_likenum_tv:
                articleDetailLikeNumTv.setSelected(true);
                articleDetailLikeNumTv.setText(new Random().nextInt(10000) + "人喜欢");
                articleDetailLikeNumRL.setSelected(true);
                articleDetailLikeNumImg.setImageResource(R.drawable.heartr_red);
                return;
            default:
                return;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(android.R.id.home == item.getItemId()){
            onBackPressed();
        }
        return true;
    }

    //获取热门跟帖内容
    private List<Comment> getHotCommentList(int articleId){
        List<Comment> commentList = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            Comment comment = new Comment();
            comment.setComAuthorImg(Utils.getImgSrc());
            comment.setComAuthor(Utils.getAuthor());
            comment.setComAaddress(Utils.getAddr());
            comment.setComPhone(Utils.getPhoneType());
            comment.setComPublishTimeStr(Utils.getPublishTime(i));
            comment.setComContent(Utils.getComContent());
            comment.setComZanNum(new Random().nextInt(1000));
            commentList.add(comment);
        }
        return  commentList;
    }

    //设置webview各种设置 此方法暂时没有被调用
    private void setWebviewSettings(WebView webView){
        WebSettings webSettings = webView.getSettings();
        //JS处理
        webSettings.setJavaScriptEnabled(true);//支持JS
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        //缩放处理
        webSettings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true);//缩放到屏幕大小
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。 这个取决于setSupportZoom(), 若setSupportZoom(false)，则该WebView不可缩放，这个不管设置什么都不能缩放。
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        //内容布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows(); //多窗口
        //文件缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        //其他设置
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings. setDefaultTextEncodingName("utf-8"); //设置编码格式
        webSettings.setPluginState(WebSettings.PluginState.OFF); //设置是否支持flash插件
        webSettings.setDefaultFontSize(20); //设置默认字体大小

    }


}
