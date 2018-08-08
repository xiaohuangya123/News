package com.xhy.reload.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.xhy.reload.news.model.Comment;
import com.xhy.reload.news.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommentListActivity extends AppCompatActivity {

    private Toolbar commentListToolbar;
    private ExpandableListView commentListExpandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        //评论列表页面的toolbar实现
        commentListToolbar = findViewById(R.id.id_comment_list_toolbar);
        setSupportActionBar(commentListToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //评论列表页面的评论列表实现
        commentListExpandableListView = findViewById(R.id.id_comment_list_ExpandableListView);
        //去掉group层评论的三角标
        commentListExpandableListView.setGroupIndicator(null);

        List<Comment> groupList = getHotCommentList(1,8);
        List<Comment> list1 = getHotCommentList(1,Utils.getRandromNum(5));
        List<Comment> list2 = getHotCommentList(1,Utils.getRandromNum(4));
        List<Comment> list3 = getHotCommentList(1,Utils.getRandromNum(3));
        List<Comment> list4 = getHotCommentList(1,Utils.getRandromNum(2));
        List<Comment> list5 = getHotCommentList(1,Utils.getRandromNum(5));
        List<List<Comment>> childrenList = new ArrayList<List<Comment>>();
        childrenList.add(list1);
        childrenList.add(list2);
        childrenList.add(list3);
        childrenList.add(list4);
        childrenList.add(list5);
        commentListExpandableListView.setAdapter(
                new CommentListExpandableListViewAdapter(groupList,childrenList));

        for (int i = 0; i <groupList.size() ; i++) {
            commentListExpandableListView.expandGroup(i);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }

    //获取热门跟帖内容
    private List<Comment> getHotCommentList(int articleId, int commentNum){
        List<Comment> commentList = new ArrayList<>();
        for (int i = 0; i <commentNum ; i++) {
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
}
