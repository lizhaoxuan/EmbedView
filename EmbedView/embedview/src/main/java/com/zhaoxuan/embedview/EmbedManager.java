package com.zhaoxuan.embedview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by lizhaoxuan on 16/2/18.
 */
public class EmbedManager {

    private static final int ANIMATION_TIME = 700;

    /*上下文，创建view的时候需要用到*/
    private Context mContext;

    private EmbedView mEmbedView;

    private FrameLayout.LayoutParams mFrameParams;

    /**
     * 两个属性
     * 1、toolbar是否悬浮在窗口之上
     * 2、toolbar的高度获取
     */
    private static int[] ATTRS = {
            R.attr.windowActionBarOverlay,
            R.attr.actionBarSize
    };

    public EmbedView getEmbedView() {
        return mEmbedView;
    }

    private EmbedManager(Context context, int toolbarLayoutId, int toolbarViewId, View topWidget, View userView,
                         View bottomWidget, SparseArray<View> coverWidgets, int animationTime, int offset) {
        mContext = context;
        mEmbedView = new EmbedView(context);
        mEmbedView.mAnimationTime = animationTime;

        initContentLayout(addToolbar(toolbarLayoutId, toolbarViewId),
                topWidget, userView, bottomWidget, coverWidgets, offset);

    }

    private boolean addToolbar(int layoutId, int viewId) {
        if (layoutId != -1 && viewId != -1) {
            View view = LayoutInflater.from(mContext).inflate(layoutId, mEmbedView);
            mEmbedView.mToolbar = (Toolbar) view.findViewById(viewId);
            return true;
        }
        return false;
    }

    private void initContentLayout(boolean hasToolbar, View topWidget, View userView,
                                   View bottomWidget, SparseArray<View> coverWidgets, int offset) {
        mFrameParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //如果这三个控件都不存在，则不需要LinearLayout,减少一层布局
        if (!hasToolbar && bottomWidget == null && topWidget == null) {
            addUserView(mEmbedView, mFrameParams, userView);
            addCoverWidgets(coverWidgets);
        } else {
            if (hasToolbar) {
                TypedArray typedArray = mContext.getTheme().obtainStyledAttributes(ATTRS);
                //获取主题中定义的悬浮标志
                boolean overly = typedArray.getBoolean(0, false);
                //获取主题中定义的toolbar的高度
                int toolBarSize = (int) typedArray.getDimension(1, (int) mContext.getResources().getDimension(R.dimen.abc_action_bar_default_height_material));
                typedArray.recycle();
                //如果是悬浮状态，则不需要设置间距
                mFrameParams.topMargin = overly ? 0 : toolBarSize;
            }

            mEmbedView.mContentLayout = new LinearLayout(mContext);
            mEmbedView.mContentLayout.setLayoutParams(mFrameParams);
            mEmbedView.mContentLayout.setOrientation(LinearLayout.VERTICAL);
            mEmbedView.addView(mEmbedView.mContentLayout);
            addTopWidget(topWidget,offset);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;
            addUserView(mEmbedView.mContentLayout, layoutParams, userView);

            addBottomWidget(bottomWidget);
            addCoverWidgets(coverWidgets);
        }

    }

    private void addTopWidget(View topWidget ,int offset) {
        if (topWidget != null) {
            mEmbedView.mTopWidget = topWidget;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mEmbedView.mTopWidget.setVisibility(View.GONE);
            mEmbedView.mContentLayout.addView(mEmbedView.mTopWidget, params);
        }
    }

    private void addUserView(ViewGroup viewGroup, ViewGroup.LayoutParams params, View userView) {
        mEmbedView.mUserView = userView;
        viewGroup.addView(mEmbedView.mUserView, params);
    }

    private void addBottomWidget(View bottomWidget) {
        if (bottomWidget != null) {
            mEmbedView.mBottomWidget = bottomWidget;
            mEmbedView.mBottomWidget.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            mEmbedView.mContentLayout.addView(mEmbedView.mBottomWidget, params);
        }
    }

    private void addCoverWidgets(SparseArray<View> coverWidgets) {
        if (coverWidgets != null && coverWidgets.size() != 0) {
            mEmbedView.mCoverWidgets = coverWidgets;
            for (int i = 0; i < coverWidgets.size(); i++) {
                View v = coverWidgets.get(i);
                if (v != null) {
                    v.setVisibility(View.GONE);
                    mEmbedView.addView(v, mFrameParams);
                }
            }
        }
    }

    public static class Builder {

        private Context mContext;

        /*toolbar*/
        protected int mToolbarLayoutId;
        protected int mToolbarViewId;

        /*嵌入式 顶部控件*/
        protected View mTopWidget;

        /*用户定义的view*/
        protected View mUserView;

        /*嵌入式 底部控件*/
        protected View mBottomWidget;

        /**
         * 居中全覆盖控件
         * 0:Loading
         * 1:TipView
         * 2:customView
         * ...
         */
        protected SparseArray<View> mCoverWidgets;

        /*控件show and hide 动画时间*/
        protected int mAnimationTime = 700;
        /*默认向上或向下偏移隐藏距离*/
        protected int mOffset;

        private int coverIndex = 2;

        public Builder(Context context, int layoutId) {
            mContext = context;
            mUserView = LayoutInflater.from(context).inflate(layoutId, null);
            mOffset = (int) context.getResources().getDimension(R.dimen.offset);
        }

        public Builder addToolbar(int layoutId, int viewId) {
            mToolbarLayoutId = layoutId;
            mToolbarViewId = viewId;
            return this;
        }

        public Builder addTopWidget(View topWidget) {
            mTopWidget = topWidget;
            return this;
        }

        public Builder addBottomWidget(View bottomWidget) {
            mBottomWidget = bottomWidget;
            return this;
        }

        public Builder addLoadView(View loadView) {
            getCoverWidgets().put(0, loadView);
            return this;
        }

        public Builder addCenterTipView(View tipView) {
            getCoverWidgets().put(1, tipView);
            return this;
        }

        public Builder addCoverWidget(View view) {
            getCoverWidgets().put(coverIndex++, view);
            return this;
        }

        public Builder setAnimationTime(int time) {
            mAnimationTime = time;
            return this;
        }

        public Builder setOffset(int offset) {
            mOffset = offset;
            return this;
        }

        private SparseArray<View> getCoverWidgets() {
            return mCoverWidgets == null ? mCoverWidgets = new SparseArray<>() : mCoverWidgets;
        }

        public EmbedManager build() {
            return new EmbedManager(mContext, mToolbarLayoutId, mToolbarViewId, mTopWidget, mUserView, mBottomWidget, mCoverWidgets, mAnimationTime, mOffset);
        }
    }

}
