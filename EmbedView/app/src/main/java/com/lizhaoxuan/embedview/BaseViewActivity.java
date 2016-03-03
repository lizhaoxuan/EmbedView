package com.lizhaoxuan.embedview;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;

import com.zhaoxuan.embedview.EmbedManager;
import com.zhaoxuan.embedview.EmbedView;

/**
 * Created by lizhaoxuan on 16/3/1.
 */
public class BaseViewActivity extends AppCompatActivity {

    protected EmbedManager embedManager;
    protected EmbedView embedView;
    private Toolbar toolbar;

    @Override
    public void setContentView(int layoutResID) {

        initEmbedView(layoutResID);

        toolbar = embedView.getToolbar();
        //将包装好的View赋值给ContentView
        setContentView(embedView);
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    private void initEmbedView(int layoutResID) {
        embedManager = new EmbedManager.Builder(this, layoutResID)
                .addToolbar(R.layout.widget_toolbar, R.id.id_tool_bar)
                .addTopWidget(LayoutInflater.from(this).inflate(R.layout.widget_top_view, null))
                .addBottomWidget(LayoutInflater.from(this).inflate(R.layout.widget_bottom_view, null))
                .addCenterTipView(new NoDataTips(this))
                .addLoadView(LayoutInflater.from(this).inflate(R.layout.widget_loading_view, null))
                .build();
        embedView = embedManager.getEmbedView();
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
    }

    public void setTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }
}
