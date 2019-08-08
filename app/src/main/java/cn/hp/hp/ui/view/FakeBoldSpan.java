package cn.hp.hp.ui.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.style.CharacterStyle;

/**
 * Created by A17028 on 2019/4/4.
 */
public class FakeBoldSpan extends CharacterStyle {
    float strokeWidth = 0.3f;
    int color = Color.BLACK;
    @Override
    public void updateDrawState(TextPaint tp) {
//        tp.setFakeBoldText(true);      //一种伪粗体效果，比原字体加粗的效果弱一点
        tp.setAntiAlias(true);
        tp.setStyle(Paint.Style.FILL_AND_STROKE);
//        tp.setColor(color);      //字体颜色
        tp.setStrokeWidth(strokeWidth);        //控制字体加粗的程度
    }

    public FakeBoldSpan(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public FakeBoldSpan() {
    }

    public FakeBoldSpan(float strokeWidth, int color) {
        this.strokeWidth = strokeWidth;
        this.color = color;
    }
}
