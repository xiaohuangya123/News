package com.xhy.reload.news;

import android.content.Intent;
import android.content.UriMatcher;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xhy.reload.news.model.Article;
import com.xhy.reload.news.utils.MyApplication;
import com.xhy.reload.news.utils.Utils;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainContentFragment extends Fragment {

    private RecyclerView articleItemRecyclerView;
    private SwipeRefreshLayout articleItemSwipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //实例化自定义fragment
        View view = inflater.inflate(R.layout.fragment_main_content,container,false);
        //文章列表recyclerView实现
        articleItemRecyclerView = view.findViewById(R.id.articleItemRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        articleItemRecyclerView.setLayoutManager(manager);
        final ArticleItemAdapter adapter = new ArticleItemAdapter(getArticleListFromDB());
        articleItemRecyclerView.setAdapter(adapter);
        //文章列表刷新功能实现
        articleItemSwipeRefreshLayout = view.findViewById(R.id.id_articleItemSwipeRefreshLayout);
        articleItemSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //模仿从网络加载数据，延迟2s执行
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setArticleList(getArticleListFromDB());
                        adapter.notifyDataSetChanged();
                        articleItemSwipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
        return view;
    }


    class ArticleItemAdapter extends RecyclerView.Adapter<ArticleItemAdapter.ViewHolder>{

        private List<Article> articleList;

        public ArticleItemAdapter(List articleList){
            this.articleList = articleList;
        }



        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.article_item_main,parent,false);
            ViewHolder vh = new ViewHolder(view);
            vh.titleArticleItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ArticleActivity.class);
                    startActivity(intent);
                }
            });
            vh.imgArticleItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),ArticleActivity.class);
                    startActivity(intent);
                }
            });
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Article article = articleList.get(position);
            Glide.with(MyApplication.getContext()).load(article.getTitleImg()).into(holder.imgArticleItem);
            holder.titleArticleItem.setText(article.getTitle());
            holder.authorArticleItem.setText(article.getAuthor());
            holder.commentNumArticleItem.setText(new Random().nextInt(1000)+"跟帖");
            holder.publishTimeArticleItem.setText(Utils.getPublishTime(new Random().nextInt(24)));
        }

        @Override
        public int getItemCount() {
            return articleList.size();
        }

        private void setArticleList(List<Article> articleList){
            this.articleList = articleList;
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            private ImageView imgArticleItem;
            private TextView titleArticleItem;
            private TextView authorArticleItem;
            private TextView commentNumArticleItem;
            private TextView publishTimeArticleItem;

            public ViewHolder(View itemView) {
                super(itemView);
                imgArticleItem = itemView.findViewById(R.id.img_article_item);
                titleArticleItem = itemView.findViewById(R.id.title_article_item);
                authorArticleItem = itemView.findViewById(R.id.author_article_item);
                commentNumArticleItem = itemView.findViewById(R.id.commentNum_article_item);
                publishTimeArticleItem = itemView.findViewById(R.id.publishTime_article_item);
            }
        }
    }

    private List<Article> getArticleListFromDB(){
        List<Article> articleList = LitePal.findAll(Article.class);
        Collections.shuffle(articleList);
        return articleList;
    }
}
