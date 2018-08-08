package com.xhy.reload.news;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * ExpandableListView 在嵌套在 NestedScrollView中是显示不正常，所以自定义
 * ExpandableListView并从新计算其高度，可以解决嵌套不正常的问题
 */
public class MyNestedExpandableListView extends ExpandableListView {


    public MyNestedExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        //将重新计算的高度传递回去
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
