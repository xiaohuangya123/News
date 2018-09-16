package com.xhy.reload.news.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xhy.reload.news.model.Column;
import com.xhy.reload.news.model.Comment;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.List;

public class JsonParseUtil {
    //解析从服务器获取的栏目数据（JSON格式）并存入本地数据库
    public static boolean parseColumnJsonWithGSON(String columnJson){
        LitePal.deleteAll(Column.class);
        Gson gson = new Gson();
        List<Column> columnList = gson.fromJson(columnJson
                , new TypeToken<List<Column>>(){}.getType());
        for (Column col:columnList) {
            col.save();
        }
        return true;
    }
    //解析从服务器获取的文章评论数据（JSON格式）并封装成List<Comment >
    public static List<Comment> parseCommentJsonWithGSON(String CommentJson){
        LitePal.deleteAll(Comment.class);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        List<Comment> commentList = gson.fromJson(CommentJson
        , new TypeToken<List<Comment>>(){}.getType());
        return commentList;
    }

    public static String parseColumnStateJson(String columnStateJson){
        String state = "false";
        try {
            JSONObject object = new JSONObject(columnStateJson);
            state = object.getString("isColumnChanged");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return state;
    }
}
