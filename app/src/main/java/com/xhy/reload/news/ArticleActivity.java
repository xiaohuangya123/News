package com.xhy.reload.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xhy.reload.news.model.Comment;
import com.xhy.reload.news.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import okhttp3.internal.Util;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ArticleActivity";
    private WebView articleContentWebView;
    private Button articleDetailBackBtn;
    private RelativeLayout articleDetailLikeNumRL;
    private TextView articleDetailLikeNumTv;
    private ImageView articleDetailLikeNumImg;
    private RecyclerView articleHotCommentRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        //文章详情页左上角返回键
        articleDetailBackBtn = findViewById(R.id.articleDetailBack);
        articleDetailBackBtn.setOnClickListener(this);

        //文件详情页文章具体内容webview实现
        articleContentWebView = findViewById(R.id.article_content);
        articleContentWebView.setWebViewClient(new WebViewClient());
        String origianlArticleContent = getArticleContent();
        String cssLayout = "<style>*{padding: 0;margin: 0}#webview_content_wrapper{margin: 10px 15px 0 15px;} p{color: #333333;line-height: 2em;font-size:17px;opacity: 1;} img{margin-top: 13px;margin-bottom: 15px;width: 100%;}</style>";
        String htmlModify = origianlArticleContent.replaceAll("<br/>", "");
        String articleDetailHtml = cssLayout + "<body><div id='webview_content_wrapper'>" + htmlModify + "</div></body>";
        articleContentWebView.loadDataWithBaseURL(null, articleDetailHtml, "text/html", "UTF-8", null);

        //文章详情页喜欢人数按钮
        articleDetailLikeNumRL = findViewById(R.id.id_article_detail_likenum_RL);
        articleDetailLikeNumTv = findViewById(R.id.id_article_detail_likenum_tv);
        articleDetailLikeNumImg = findViewById(R.id.id_article_detail_likenum_img);
        articleDetailLikeNumTv.setOnClickListener(this);

        //文章热门跟帖
        articleHotCommentRv = findViewById(R.id.id_article_detail_hotcomment_rv);
        // TODO: 2018/7/28 用不用设置manager水平，垂直?
        LinearLayoutManager manager = new LinearLayoutManager(this);
        articleHotCommentRv.setLayoutManager(manager);
        List<Comment> hotCommentList = getHotCommentList(1);
        ArticleHotCommentAdapter adapter = new ArticleHotCommentAdapter(hotCommentList);
        articleHotCommentRv.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.articleDetailBack:
                super.onBackPressed();
                return;
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

    //得到文章具体内容的html
    private String getArticleContent(){
        String articleContent= "<p><span style=\"\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;华春莹在当天举行的外交部例行记者会上对《环球时报》记者表示，中方一直在关注日本福岛核泄漏极其后续影响。\n" +
                "“我注意到在福岛核事故发生六周年之际，日本国内媒体也在大量报道评论，总体认为日本政府在污染水和土壤及放射性废弃物处理方面缺乏有效手段，\n" +
                "向海洋排放核污水给周边海洋环境和民众健康带来隐患，有关对策滞后且信息公开不透明，食品安全等相关数据缺乏足够说服力。</span></p>\n" +
                "<div class=\"image-package\"><img src=\"http://duty.oss-cn-shenzhen.aliyuncs.com/8380e6fa-db21-4b7c-83e1-34bff9ba89a0tyggh2.png\" data-by-webuploader=\"true\"/>\n" +
                "</div><p>表示，中方一直在关注日本福岛核泄漏极其后续影响。“我注意到在福岛核事故发生六周年之际，日本国内媒体也在大量报道评论，\n" +
                "总体认为日本政府在污染水和土壤及放射性废弃物处理方面缺乏有效手段，向海洋排放核污水给周边海洋环境和民众健康带来隐患，\n" +
                "有关对策滞后且信息公开不透明，食品安全等相关数据缺乏足够说服力。</p><div class=\"image-package\">\n" +
                "<img src=\"http://duty.oss-cn-shenzhen.aliyuncs.com/b25eaee8-f133-4618-9411-94685f4ae2detyggh4.png\" data-by-webuploader=\"true\"/></div>\n" +
                "<div class=\"image-package\"><img src=\"http://duty.oss-cn-shenzhen.aliyuncs.com/726d283c-8c8d-4e34-916b-52a78bca3ba0tyggh1.png\" data-by-webuploader=\"true\"/>\n" +
                "</div><p><span style=\"\"></span><br/></p>";
        return articleContent;
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