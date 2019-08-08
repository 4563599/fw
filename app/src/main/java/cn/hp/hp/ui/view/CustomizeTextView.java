package cn.hp.hp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hp.hp.R;

/**
 * Created by lyy on 2019/6/26.
 */

public class CustomizeTextView extends TextView {
    float strokeWidth;

    public CustomizeTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public CustomizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomizeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.strokeWidth = 0.0F;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomizeTextView, defStyleAttr, 0);
        this.strokeWidth = a.getFloat(R.styleable.CustomizeTextView_tvstrokeWidth, 0.8F);
        if (!TextUtils.isEmpty(this.getText())) {
            this.setText(this.getText());
        }

        a.recycle();
    }

    public void setText(CharSequence text, BufferType type) {
        if (TextUtils.isEmpty(text)) {
            super.setText(text, type);
        } else {
            super.setText((new Spanny()).append(text, new FakeBoldSpan(this.strokeWidth)), type);
        }
    }
}
