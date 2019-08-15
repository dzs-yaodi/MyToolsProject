package com.xw.baselib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.xw.baselib.R;
import com.xw.baselib.utils.UITools;

public class CustomButton extends Button {

    /**文字颜色，大小*/
    private int textColor;
    private int textSize;
    /**按钮背景色*/
    private int backGround;
    /**圆角幅度*/
    private float radius;
    /**形状*/
    private int shape;
    /****/
    private GradientDrawable gradientDrawable = null;
    /**点击时按钮的背景 和字体颜色*/
    private int clickBg;
    private int clickColor;
    /**边框宽度和颜色*/
    private int strokeWidth;
    private int strokeColor;
    private boolean isCost  = true;

    public CustomButton(Context context) {
        this(context,null);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array =context.obtainStyledAttributes(attrs, R.styleable.CustomButton);
        if (array != null){
            textColor = array.getColor(R.styleable.CustomButton_text_color, Color.WHITE);
            setTextColor(textColor);

            backGround = array.getColor(R.styleable.CustomButton_buttom_background,Color.BLUE);
            setbgColor(backGround);
            setGravity(Gravity.CENTER);

            clickBg = array.getColor(R.styleable.CustomButton_backColorPress,Color.GRAY);
            clickColor = array.getColor(R.styleable.CustomButton_color_click,Color.BLACK);

            textSize = array.getInt(R.styleable.CustomButton_text_size, 14);
            setTextSize(textSize);

            strokeWidth = array.getInt(R.styleable.CustomButton_buttom_stroke_width,0);
            strokeColor = array.getColor(R.styleable.CustomButton_btn_stroke_color,0);
            if (strokeWidth > 0 && strokeColor != 0) {
                setBtnStroke();
            }

            int padding = array.getInt(R.styleable.CustomButton_text_padding, UITools.dp2px(context,0));
            if (padding == 0){
                int paddingLeft = array.getInt(R.styleable.CustomButton_text_padding_left,UITools.dp2px(context,8));
                int paddingRight = array.getInt(R.styleable.CustomButton_text_padding_right,UITools.dp2px(context,8));
                int paddingTop = array.getInt(R.styleable.CustomButton_text_padding_top,UITools.dp2px(context,8));
                int paddingBottom = array.getInt(R.styleable.CustomButton_text_padding_bottom,UITools.dp2px(context,8));

                setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
            }else{
                setPadding(padding,padding,padding,padding);
            }

            radius = array.getFloat(R.styleable.CustomButton_radius, 0);
            if (radius != 0) {
                setRaius();
            }else{
                float top_left_radius = array.getFloat(R.styleable.CustomButton_top_left_radius,0);
                float top_right_radius = array.getFloat(R.styleable.CustomButton_top_right_radius,0);
                float bottom_left_radius = array.getFloat(R.styleable.CustomButton_bottom_left_radius,0);
                float bottom_right_radius = array.getFloat(R.styleable.CustomButton_bottom_right_radius,0);

                if (top_left_radius != 0 || top_right_radius != 0 || bottom_left_radius != 0 || bottom_right_radius != 0){
                    setRaiusDirection(top_left_radius,top_right_radius,bottom_left_radius,bottom_right_radius);
                }
            }

            shape = array.getInt(R.styleable.CustomButton_shape, 0);
            setShape(shape);
            array.recycle();
        }

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return setTouchStyle(event.getAction());
            }
        });
    }

    private boolean setTouchStyle(int action) {
        //按下
        if (action == MotionEvent.ACTION_DOWN){
            if (clickBg != 0){
                gradientDrawable.setColor(clickBg);
                setBackground(gradientDrawable);
            }

            if (clickColor != 0){
                setTextColor(clickColor);
            }
        }else if (action == MotionEvent.ACTION_UP){
            if (backGround != 0){
                gradientDrawable.setColor(backGround);
                setBackground(gradientDrawable);
            }

            if (textColor != 0){
                setTextColor(textColor);
            }
        }
        return isCost;
    }

    /**
     * 设置画出按钮的形状
     * @param shape
     */
    private void setShape(int shape) {
        getGradientDrawable();
        gradientDrawable.setShape(shape);
        setBackground(gradientDrawable);
    }

    /**
     * 设置上下左右的幅度
     * @param top_left_radius
     * @param top_right_radius
     * @param bottom_left_radius
     * @param bottom_right_radius
     */
    private void setRaiusDirection(float top_left_radius, float top_right_radius, float bottom_left_radius, float bottom_right_radius) {
        getGradientDrawable();
        gradientDrawable.setCornerRadii(new float[]{
                top_left_radius, top_left_radius,
                top_right_radius, top_right_radius,
                bottom_right_radius, bottom_right_radius,
                bottom_left_radius, bottom_left_radius
        });
       setBackground(gradientDrawable);
    }

    /**
     * 设置按钮圆角幅度
     */
    private void setRaius() {
        getGradientDrawable();
        gradientDrawable.setCornerRadius(radius);
        setBackground(gradientDrawable);
    }

    /**
     * 设置按钮边框
     */
    private void setBtnStroke() {
        getGradientDrawable();
        gradientDrawable.setStroke(strokeWidth,strokeColor);
        setBackground(gradientDrawable);
    }

    /**
     * 设置按钮背景色
     * @param backGround
     */
    private void setbgColor(int backGround) {
        getGradientDrawable();
        gradientDrawable.setColor(backGround);
        setBackground(gradientDrawable);
    }

    private void getGradientDrawable(){
        if (gradientDrawable == null){
            gradientDrawable = new GradientDrawable();
        }
    }

    @Override
    public void setOnClickListener(View.OnClickListener l) {
        super.setOnClickListener(l);
        isCost = false;
    }
}
