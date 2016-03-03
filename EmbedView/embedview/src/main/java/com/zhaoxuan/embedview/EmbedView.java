package com.zhaoxuan.embedview;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by lizhaoxuan on 16/2/6.
 */
public class EmbedView extends FrameLayout {


    /*toolbar*/
    protected Toolbar mToolbar;

    /*content LinearLayout*/
    protected LinearLayout mContentLayout;

    /*嵌入式 顶部控件*/
    protected View mTopWidget;

    /*用户定义的view*/
    protected View mUserView;

    /*嵌入式 底部控件*/
    protected View mBottomWidget;

    /**
     * 居中全覆盖控件
     * 0:Loading
     * 1:CenterTipView
     * 2:customView
     * ...
     */
    protected SparseArray<View> mCoverWidgets;

    /**
     * 动画时长设置
     */
    protected int mAnimationTime;

    private boolean isTopWidgetShowing;

    protected EmbedView(Context context) {
        super(context);
        this.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /*--------- getter ----------*/

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public View getTopWidget() {
        return mTopWidget;
    }

    public View getUserView() {
        return mUserView;
    }

    public View getBottomWidget() {
        return mBottomWidget;
    }

    public View getLoadView() {
        if (mCoverWidgets == null) {
            return null;
        }
        return mCoverWidgets.get(0);
    }

    public View getTipView() {
        if (mCoverWidgets == null) {
            return null;
        }
        return mCoverWidgets.get(1);
    }

    public View getViewForCoverWidgets(int position) {
        if (mCoverWidgets == null) {
            return null;
        }
        return mCoverWidgets.get(position);
    }

    /*--------- show and hide ----------*/

    public void showTopWidget() {
        if (mTopWidget != null && mTopWidget.getVisibility() != VISIBLE){
            mTopWidget.setVisibility(VISIBLE);
        }
//        if (mTopWidget != null && !isTopWidgetShowing) {
//            isTopWidgetShowing = true;
//            int height = mTopWidget.getHeight();
//            ObjectAnimator anim1 = ObjectAnimator.ofFloat(mTopWidget,
//                    "y", -height, 0f);
//            ObjectAnimator anim2 = ObjectAnimator.ofFloat(mUserView,
//                    "y", 0f, height);
//            AnimatorSet animSet = new AnimatorSet();
//            animSet.play(anim1).with(anim2);
//            animSet.setDuration(mAnimationTime);
//            animSet.start();
//        }
    }

    public void hideTopWidget() {
        if (mTopWidget != null && mTopWidget.getVisibility() != GONE) {
            mTopWidget.setVisibility(GONE);
        }
//        if (mTopWidget != null && isTopWidgetShowing) {
//            isTopWidgetShowing = false;
//            int height = mTopWidget.getHeight();
//            ObjectAnimator anim1 = ObjectAnimator.ofFloat(mTopWidget,
//                    "y", 0f, -height);
//            ObjectAnimator anim2 = ObjectAnimator.ofFloat(mUserView,
//                    "y", height, 0f);
//            AnimatorSet animSet = new AnimatorSet();
//            animSet.play(anim1).with(anim2);
//            animSet.setDuration(mAnimationTime);
//            animSet.start();
//        }
    }

    public boolean isTopWidgetShowing() {
        return mTopWidget != null && mTopWidget.getVisibility() == VISIBLE;
    }

    public void showBottomWidget() {
        if (mBottomWidget != null && mBottomWidget.getVisibility() != VISIBLE) {
            mBottomWidget.setVisibility(VISIBLE);
        }
    }

    public void hideBottomWidget() {
        if (mBottomWidget != null && mBottomWidget.getVisibility() != GONE) {
            mBottomWidget.setVisibility(GONE);
        }
    }

    public boolean isBottomWidgetShowing() {
        return mBottomWidget != null && mBottomWidget.getVisibility() == VISIBLE;
    }

    public void showLoadView() {
        if (getLoadView() != null && getLoadView().getVisibility() != VISIBLE) {
            getLoadView().setVisibility(VISIBLE);
        }
    }

    public void hideLoadView() {
        if (getLoadView() != null && getLoadView().getVisibility() != GONE) {
            getLoadView().setVisibility(GONE);
        }
    }

    public boolean isLoadViewShowing() {
        return getLoadView() != null && getLoadView().getVisibility() == VISIBLE;
    }

    public void showCenterTipView() {
        if (getTipView() != null && getTipView().getVisibility() != VISIBLE) {
            getTipView().setVisibility(VISIBLE);
        }
    }

    public void hideCenterTipView() {
        if (getTipView() != null && getTipView().getVisibility() != GONE) {
            getTipView().setVisibility(GONE);
        }
    }

    public boolean isCenterTipViewShowing() {
        return getTipView() != null && getTipView().getVisibility() == VISIBLE;
    }


}
