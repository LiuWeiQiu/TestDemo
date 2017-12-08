package com.example.ubt.threadtestdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ubt on 2017/12/7 0007.
 */

public class BaseActivity extends AppCompatActivity {
    public static final String TITLE_NAME = "title_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getIntent().getStringExtra(TITLE_NAME));
    }
}
