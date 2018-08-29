package com.xhy.reload.news;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class CommentListReplyDialog extends Dialog implements View.OnClickListener{
    private Context myContext;

    private TextView commentListDialogisfloatReplyTv;
    private TextView commentListDialogisfloatShareTv;
    private TextView commentListDialogisfloatCopyTv;
    private TextView commentListDialogisfloatMoreTv;

    public CommentListReplyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        myContext = context;
    }

    public CommentListReplyDialog(@NonNull Context context) {
        super(context);
        myContext = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comment_list_reply_dialog);
        commentListDialogisfloatReplyTv = findViewById(R.id.id_comment_list_dialogisfloat_reply_tv);
        commentListDialogisfloatShareTv = findViewById(R.id.id_comment_list_dialogisfloat_share_tv);
        commentListDialogisfloatCopyTv = findViewById(R.id.id_comment_list_dialogisfloat_copy_tv);
        commentListDialogisfloatMoreTv = findViewById(R.id.id_comment_list_dialogisfloat_more_tv);
        commentListDialogisfloatReplyTv.setOnClickListener(this);
        commentListDialogisfloatShareTv.setOnClickListener(this);
        commentListDialogisfloatCopyTv.setOnClickListener(this);
        commentListDialogisfloatMoreTv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_comment_list_dialogisfloat_reply_tv :
                Toast.makeText(myContext, "你点击了回复", Toast.LENGTH_SHORT).show();
                this.dismiss();
                break;
            case R.id.id_comment_list_dialogisfloat_share_tv :
                Toast.makeText(myContext, "你点击了分享", Toast.LENGTH_SHORT).show();
                this.dismiss();
                break;
            case R.id.id_comment_list_dialogisfloat_copy_tv :
                Toast.makeText(myContext, "你点击了复制", Toast.LENGTH_SHORT).show();
                this.dismiss();
                break;
            case R.id.id_comment_list_dialogisfloat_more_tv :
                Toast.makeText(myContext, "你点击了更多", Toast.LENGTH_SHORT).show();
                this.dismiss();
                break;
        }

    }
}
