package com.xw.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import com.xw.baselib.R;
import com.xw.baselib.utils.UITools;

public class CustomTextView extends TextView {

    private int textColor;
    private int textSize;
    private int textbackGround;
    //边框
    private int strokeWidth;
    private int strokeColor;
    //圆角半径
    private float radius;
    //形状
    private int shape;

    private GradientDrawable gradientDrawable;

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        getGradientDrawable();
        if (array != null) {
            textColor = array.getColor(R.styleable.CustomTextView_txt_color, Color.WHITE);
            textbackGround = array.getColor(R.styleable.CustomTextView_txt_background, Color.RED);
            textSize = array.getInt(R.styleable.CustomTextView_txt_size, 14);

            //设置背景颜色，字体颜色，大小 和 居中
            setTextColor(textColor);
            setTextSize(textSize);
            setGravity(Gravity.CENTER);
            gradientDrawable.setColor(textbackGround);

            strokeWidth = array.getInt(R.styleable.CustomTextView_txt_stroke_width, 0);
            strokeColor = array.getColor(R.styleable.CustomTextView_txt_stroke_color, 0);
            if (strokeWidth > 0 && strokeColor != 0) {
                gradientDrawable.setStroke(strokeWidth, strokeColor);
            }

            shape = array.getInt(R.styleable.CustomTextView_txt_shape, 0);
            gradientDrawable.setShape(shape);

            int padding = array.getInt(R.styleable.CustomTextView_txt_padding, UITools.dp2px(context, 0));
            if (padding == 0) {
                int paddingLeft = array.getInt(R.styleable.CustomTextView_txt_padding_left, UITools.dp2px(context, 8));
                int paddingRight = array.getInt(R.styleable.CustomTextView_txt_padding_right, UITools.dp2px(context, 8));
                int paddingTop = array.getInt(R.styleable.CustomTextView_txt_padding_top, UITools.dp2px(context, 8));
                int paddingBottom = array.getInt(R.styleable.CustomTextView_txt_padding_bottom, UITools.dp2px(context, 8));

                setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
            } else {
                setPadding(padding, padding, padding, padding);
            }

            radius = array.getFloat(R.styleable.CustomTextView_txt_radius, 0);
            if (radius != 0) {
                gradientDrawable.setCornerRadius(radius);
            } else {
                float top_left_radius = array.getFloat(R.styleable.CustomButton_top_left_radius, 0);
                float top_right_radius = array.getFloat(R.styleable.CustomButton_top_right_radius, 0);
                float bottom_left_radius = array.getFloat(R.styleable.CustomButton_bottom_left_radius, 0);
                float bottom_right_radius = array.getFloat(R.styleable.CustomButton_bottom_right_radius, 0);

                if (top_left_radius != 0 || top_right_radius != 0 || bottom_left_radius != 0 || bottom_right_radius != 0) {
                    gradientDrawable.setCornerRadii(new float[]{
                            top_left_radius, top_left_radius,
                            top_right_radius, top_right_radius,
                            bottom_right_radius, bottom_right_radius,
                            bottom_left_radius, bottom_left_radius
                    });
                }
            }
            setBackground(gradientDrawable);
            array.recycle();
        }
    }

    private void getGradientDrawable() {
        if (gradientDrawable == null)
            gradientDrawable = new GradientDrawable();
    }
}
