package cn.hp.hp.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.hp.hp.R;

import java.util.List;


/**
 * Created by A17028 on 2019/2/24.
 */

public class DynamicTitleBar extends LinearLayout {
    Context context;
    public DynamicTitleBar(Context context) {
        this(context,null);
    }

    OnClickTitleBarListener onClickTitleBarListener;

    public DynamicTitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DynamicTitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    public void setOnClickTitleBarListener(OnClickTitleBarListener onClickTitleBarListener) {
        this.onClickTitleBarListener = onClickTitleBarListener;
    }

    public void setTitleBars(List<String> titles){
        removeAllViews();
        int width = (int) context.getResources().getDimension(R.dimen.nav_tab_btn_width);
        int height = (int) context.getResources().getDimension(R.dimen.nav_tab_btn_height);
        LayoutParams layoutLp = new LayoutParams(width, height);

        for(int i=0;i<titles.size();i++){
            CustomizeTextView textView = new CustomizeTextView(context);
            textView.setText(titles.get(i));
            if(i == 0){
                textView.setBackgroundResource(R.drawable.left_nav_selector);
            }else if(i== titles.size()-1){
                textView.setBackgroundResource(R.drawable.right_nav_selector);
            }else {
                textView.setBackgroundResource(R.drawable.mid_nav_selector);
            }
            final int pos = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickTitleBarListener!=null){
                        setSelectItem(pos);
                        onClickTitleBarListener.onClick(pos);
                    }
                }
            });
            textView.setTextSize(17);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            textView .setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            textView.setLayoutParams(layoutLp);
            addView(textView);
        }
    }

    public interface OnClickTitleBarListener{
        void onClick(int pos);
    }


    public void setSelectItem(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view != null) {
                if (i == position) {
                    view.setSelected(true);
                } else {
                    view.setSelected(false);
                }
            }
        }
    }

    public void setItemEnable(int pos,boolean enable){
        for (int i = 0; i < getChildCount(); i++) {
            TextView view = (TextView) getChildAt(i);
            if (view != null) {
                if (i == pos) {
                    view.setEnabled(enable);
                    view.setTextColor(Color.GRAY);
                }
            }
        }
    }
}
