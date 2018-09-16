package com.xhy.reload.news;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xhy.reload.news.model.Comment;
import com.xhy.reload.news.utils.MyApplication;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentListExpandableListViewAdapter extends BaseExpandableListAdapter{

    public String[] groupStrings = {"西游记", "水浒传", "三国演义", "红楼梦"};
    public String[][] childStrings = { {"唐三藏", "孙悟空", "猪八戒", "沙和尚"},
            {"宋江", "林冲", "李逵", "鲁智深"}, {"曹操", "刘备", "孙权", "诸葛亮", "周瑜"},
            {"贾宝玉", "林黛玉", "薛宝钗", "王熙凤"} };

    private List<Comment> groupCommentList;
    private List<List<Comment>> childCommentList;
    private Activity myContext;
    //获取点击坐标
    private float touchX;
    private float touchY;

    public CommentListExpandableListViewAdapter(List<Comment> groupCommentList
            , List<List<Comment>> childCommentList){
        this.groupCommentList = groupCommentList;
        this.childCommentList = childCommentList;
    }

    public CommentListExpandableListViewAdapter(List<Comment> groupCommentList
            , List<List<Comment>> childCommentList
            , Activity myContext) {
        this.groupCommentList = groupCommentList;
        this.childCommentList = childCommentList;
        this.myContext = myContext;
    }

    @Override
    public int getGroupCount() {
        return groupCommentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        //用于解决有些评论没有子评论也就是回复（children层评论）时，展开group层评论报错问题
        if(groupPosition + 1 > childCommentList.size()){
            return 0;
        }
        return childCommentList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupCommentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childCommentList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_list_group, parent, false);
            groupHolder = new GroupHolder();
            groupHolder.groupTouXiang = convertView.findViewById(R.id.id_comment_list_group_touxiang);
            groupHolder.groupAnthor = convertView.findViewById(R.id.id_comment_list_group_author);
            groupHolder.groupContent = convertView.findViewById(R.id.id_comment_list_group_content);
            groupHolder.groupPhone = convertView.findViewById(R.id.id_comment_list_group_phone);
            groupHolder.groupPublshTime = convertView.findViewById(R.id.id_comment_list_group_publishtime);
            groupHolder.groupZanNum = convertView.findViewById(R.id.id_comment_list_group_zan_num);
            groupHolder.groupAddr = convertView.findViewById(R.id.id_comment_list_group_addr);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (GroupHolder)convertView.getTag();
        }
        Comment groupComment = groupCommentList.get(groupPosition);
        groupHolder.groupTouXiang.setImageResource(groupComment.getComAuthorImg());
        groupHolder.groupAnthor.setText(groupComment.getComAuthor());
        groupHolder.groupAddr.setText(groupComment.getComAaddress());
        groupHolder.groupPhone.setText(groupComment.getComPhone());
        groupHolder.groupPublshTime.setText(groupComment.getComPublishTimeStr());
        groupHolder.groupZanNum.setText(groupComment.getComZanNum() + "");
        groupHolder.groupContent.setText(groupComment.getComContent());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.comment_list_children, parent, false);
            childHolder = new ChildHolder();
            childHolder.childrenFloorNum = convertView.findViewById(R.id.id_comment_list_children_floor_num);
            childHolder.childrenAnthor = convertView.findViewById(R.id.id_comment_list_children_author);
            childHolder.childrenAddr = convertView.findViewById(R.id.id_comment_list_children_addr);
            childHolder.childrenPhone = convertView.findViewById(R.id.id_comment_list_children_phone);
            childHolder.childrenPublshTime = convertView.findViewById(R.id.id_comment_list_children_publishtime);
            childHolder.childrenZanNum = convertView.findViewById(R.id.id_comment_list_children_zannum);
            childHolder.childrenContent = convertView.findViewById(R.id.id_comment_list_children_content);
            convertView.setTag(childHolder);
        }else {
            childHolder = (ChildHolder) convertView.getTag();
        }
        Comment childrenCommemt = childCommentList.get(groupPosition).get(childPosition);
        childHolder.childrenFloorNum.setText(childPosition + 1 + "");
        childHolder.childrenAnthor.setText(childrenCommemt.getComAuthor());
        childHolder.childrenAddr.setText(childrenCommemt.getComAaddress());
        childHolder.childrenPhone.setText(childrenCommemt.getComPhone());
        childHolder.childrenPublshTime.setText(childrenCommemt.getComPublishTimeStr());
        childHolder.childrenZanNum.setText(childrenCommemt.getComZanNum() + "");
        childHolder.childrenContent.setText(childrenCommemt.getComContent());

        childHolder.childrenContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                touchX = event.getRawX();
                touchY = event.getRawY();
                return false;
            }
        });

        //给子评论增加点击事件
        childHolder.childrenContent.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                CommentListReplyDialog replyDialog = new CommentListReplyDialog(myContext);
                //去掉dialog蒙层
                replyDialog.getWindow().setDimAmount(0f);

                Window window = replyDialog.getWindow();
                WindowManager.LayoutParams params = window.getAttributes();
                params.y = (int)touchY - 350;
                window.setAttributes(params);
                window.setGravity(Gravity.TOP);
                replyDialog.show();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupHolder{
        CircleImageView groupTouXiang;
        TextView groupAnthor;
        TextView groupZanNum;
        TextView groupPhone;
        TextView groupPublshTime;
        TextView groupContent;
        TextView groupAddr;

    }

    class ChildHolder{
        TextView childrenFloorNum;
        TextView childrenAnthor;
        TextView childrenZanNum;
        TextView childrenPhone;
        TextView childrenPublshTime;
        TextView childrenContent;
        TextView childrenAddr;
    }

    public void addGroupCommentAndShow(Comment comment){
        if(comment != null){
            groupCommentList.add(comment);
        }
        notifyDataSetChanged();
    }
}
