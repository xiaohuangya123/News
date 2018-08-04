package com.xhy.reload.news.utils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xhy.reload.news.model.Column;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.List;

public class JsonParseUtil {

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
