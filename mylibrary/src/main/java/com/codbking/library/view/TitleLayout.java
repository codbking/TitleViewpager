package com.codbking.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.codbking.library.R;


/**
 * Created by codbking on 2016/12/13.
 */

public class TitleLayout extends LinearLayout implements ViewPager.OnPageChangeListener {

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private Paint mPaint;
    //移动位置
    private int movePostion = 0;
    //变化率
    private float positionOffset;
    //指示器颜色
    private int indicatorColor=-1;
    //指示器高度
    private int indicatorHeight=-1;

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        init();
    }

    public void setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
        init();
    }

    private void  init(){

        mPaint = new Paint();
        if(indicatorColor==-1){
            mPaint.setColor(getContext().getResources().getColor(R.color.main_color));
        }else{
            mPaint.setColor(indicatorColor);
        }
        if(indicatorHeight==-1){
            indicatorHeight=Utils.px(2);
        }
    }

    public void onPageScrolled(int position, float positionOffset
            , int positionOffsetPixels) {

        this.positionOffset = positionOffset;
        this.movePostion = position;

        if(getChildCount()>0){
            invalidate();
        }
    }

    public void onPageSelected(int position) {
        if(getChildCount()>0){
            invalidate();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas.save();
        View moveView = getChildAt(movePostion);

        //指示器宽度
        int indicatorWidth = 0;
        //指示器高度 default 1dp
        int indicatorHeight=this.indicatorHeight;

        //目标位置
        int postion = movePostion + 1;

        if (postion > getChildCount() - 1) {
            postion = getChildCount() - 1;
        }

        //指示器随着目标位置view的宽度变化而变化
        indicatorWidth = (int) (moveView.getWidth() + (getChildAt(postion).getWidth() - moveView.getWidth()) * positionOffset);

        int left = (int) (moveView.getLeft() + moveView.getWidth() * positionOffset);
        canvas.translate(left, moveView.getBottom() - indicatorHeight);
        canvas.drawRect(new Rect(0, 0, indicatorWidth, indicatorHeight), mPaint);
        canvas.restore();
    }


}
