package com.lizhaoxuan.embedview;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseViewActivity implements View.OnClickListener {

    private Button btn1, btn2, btn3, btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        setTitle("嵌入式ViewDemo");
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn1:
                if (embedView.isTopWidgetShowing()) {
                    embedView.hideTopWidget();
                } else {
                    embedView.showTopWidget();
                }
                break;
            case R.id.btn2:
                if (embedView.isBottomWidgetShowing()) {
                    embedView.hideBottomWidget();
                } else {
                    embedView.showBottomWidget();
                }
                break;
            case R.id.btn3:
                if (embedView.isCenterTipViewShowing()) {
                    embedView.hideCenterTipView();
                } else {
                    embedView.showCenterTipView();
                }
                break;
            case R.id.btn4:
                if (embedView.isLoadViewShowing()) {
                    embedView.hideLoadView();
                } else {
                    embedView.showLoadView();
                }
                break;

        }
    }
}
