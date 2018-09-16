package com.xhy.reload.news;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xhy.reload.news.model.Comment;
import com.xhy.reload.news.utils.MyApplication;
import com.xhy.reload.news.utils.Utils;

import java.util.List;

public class ArticleHotCommentAdapter extends RecyclerView.Adapter<ArticleHotCommentAdapter.ViewHolder>{
    private List<Comment> hotCommentList;


    public ArticleHotCommentAdapter(List<Comment> hotCommentList) {
        this.hotCommentList = hotCommentList;
    }

    //可以用于后续加载更多评论
    public void setHotCommentList(List<Comment> hotCommentList) {
        this.hotCommentList = hotCommentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_detail_hotcomment_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return hotCommentList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = hotCommentList.get(position);
        holder.hotCommentTouXiangTv.setImageResource(comment.getComAuthorImg());
        Glide.with(MyApplication.getContext()).load(comment.getComAuthorImg()).into(holder.hotCommentTouXiangTv);
        holder.hotCommnetAuthorTv.setText(comment.getComAuthor());
        holder.hotCommentAddrTv.setText(comment.getComAaddress());
        holder.hotCommentPhoneTv.setText(comment.getComPhone());
        holder.hotCommentPublishTimeTv.setText(comment.getComPublishTimeStr());
        holder.hotCommentZanNumTv.setText(comment.getComZanNum() + "项");
        holder.hotCommentContent.setText(comment.getComContent());
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        // TODO: 2018/8/8 这里错误 应该是imageview 或circleimageview
        ImageView hotCommentTouXiangTv;
        TextView hotCommnetAuthorTv;
        TextView hotCommentAddrTv;
        TextView hotCommentPhoneTv;
        TextView hotCommentPublishTimeTv;
        TextView hotCommentZanNumTv;
        TextView hotCommentContent;

        public ViewHolder(View itemView) {
            super(itemView);
            hotCommentTouXiangTv = itemView.findViewById(R.id.id_hotComment_touxiang);
            hotCommnetAuthorTv = itemView.findViewById(R.id.id_hotComment_author);
            hotCommentAddrTv = itemView.findViewById(R.id.id_hotComment_addr);
            hotCommentPhoneTv = itemView.findViewById(R.id.id_hotComment_phone);
            hotCommentPublishTimeTv = itemView.findViewById(R.id.id_hotComment_publishtime);
            hotCommentZanNumTv = itemView.findViewById(R.id.id_hotComment_zannum);
            hotCommentContent = itemView.findViewById(R.id.id_hotComment_content);
        }
    }
}
