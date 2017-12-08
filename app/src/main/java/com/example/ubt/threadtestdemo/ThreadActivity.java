package com.example.ubt.threadtestdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ThreadActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "ThreadActivity";

    private Button btn_add;
    private LinearLayout ll_content;
    String[] urls = {Constant.URL_1, Constant.URL_2, Constant.URL_3, Constant.URL_4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        btn_add = findViewById(R.id.btn_add);
        ll_content = findViewById(R.id.ll_content);
        btn_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < urls.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_thread_info, null);
            ll_content.addView(view);
            doDownLoad(urls[i], view);
        }
    }


    private OnDownListener listener = new OnDownListener() {
        @Override
        public void onFailed() {
            Log.i(TAG, "onFailure");
        }

        @Override
        public void onProgress(long total, long already, final View view) {
            final String str_total = Utils.getStringFormNumber(total);
            final String str_now = Utils.getStringFormNumber(already);
//            Log.i(TAG, "total=" +str_total +"\talready=" +str_now);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView tv_total = view.findViewById(R.id.tv_total);
                    TextView tv_now = view.findViewById(R.id.tv_now);
                    tv_total.setText(str_total);
                    tv_now.setText(str_now);

                }
            });
        }

        @Override
        public void onSucc() {
            Log.i(TAG, "download");
        }
    };

    interface OnDownListener {
        void onFailed();

        void onProgress(long total, long already, View view);

        void onSucc();
    }


    private void doDownLoad(final String url, final View view) {


        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                listener.onFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                FileOutputStream fos = null;
                File file = new File(Constant.DOWNLOAD_PATH + File.separator + getFileName(url));
                long sum = 0;
                int len = 0;
                byte[] b = new byte[2048];
                Long total = response.body().contentLength();
                try {
                    /**方法一**/
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(b)) != -1) {
                        fos.write(b, 0, len);
                        sum += len;
                        listener.onProgress(total, sum, view);
                    }
                    fos.flush();
//                    /***方法二***/
//                    Okio.sink(file).write(new Buffer().readFrom(is), total);
                    listener.onSucc();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                }
            }

        });
    }

    private String getFileName(String url1) {
        return url1.substring(url1.lastIndexOf("/"));
    }
}
