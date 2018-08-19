package com.xhy.reload.news;

import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.xhy.reload.news.model.Comment;
import com.xhy.reload.news.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CommentListActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar commentListToolbar;
    private ExpandableListView commentListExpandableListView;
    private TextView commentListWriteGentieTv;
    private BottomSheetDialog commentDialog;
    private EditText commentListInputEt;
    private TextView commentListSubmitTv;

    private CommentListExpandableListViewAdapter adapter;

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
        //准备评论数据group层，children层
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
        adapter = new CommentListExpandableListViewAdapter(groupList,
                childrenList,CommentListActivity.this);
        commentListExpandableListView.setAdapter(adapter);
        //展开所有group层评论，显示下面的children层评论
        for (int i = 0; i <groupList.size() ; i++) {
            commentListExpandableListView.expandGroup(i);
        }

        //写跟帖
        commentListWriteGentieTv = findViewById(R.id.id_comment_list_write_gentie_tv);
        commentListWriteGentieTv.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_comment_list_write_gentie_tv:
                showCommentDialog();
                break;
            case R.id.id_comment_list_submit_gentie_tv:
                String inputComment = commentListInputEt.getText().toString().trim();
                Comment comment = new Comment();
                comment.setComAuthor(Utils.getAuthor());
                comment.setComAuthorImg(Utils.getImgSrc());
                comment.setComAaddress(Utils.getAddr());
                comment.setComPublishTimeStr(Utils.getPublishTime(4));
                comment.setComZanNum(5);
                comment.setComContent(inputComment);
                comment.setComPhone(Utils.getPhoneType());
                adapter.addGroupCommentAndShow(comment);
                commentDialog.dismiss();
                break;
        }
    }

    private void showCommentDialog(){
        if(null == commentDialog){
            View view =  LayoutInflater.from(CommentListActivity.this)
                    .inflate(R.layout.comment_list_dialog, null);
            commentListInputEt = view.findViewById(R.id.id_comment_list_input_edittext);
            commentListSubmitTv = view.findViewById(R.id.id_comment_list_submit_gentie_tv);
            commentListSubmitTv.setOnClickListener(this);
            commentDialog = new BottomSheetDialog(CommentListActivity.this);
            commentDialog.setContentView(view);
        }
        commentListInputEt.getText().clear();

        //Edittext获取焦点自动弹出键盘
//        commentListInputEt.setFocusable(true);
//        commentListInputEt.setFocusableInTouchMode(true);
//        commentListInputEt.requestFocus();

        commentDialog.show();
// TODO: 2018/8/18  弹出键盘遮挡dialog问题还没有解决 
//        commentDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        commentDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
