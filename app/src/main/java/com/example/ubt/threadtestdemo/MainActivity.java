package com.example.ubt.threadtestdemo;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String[] activity_title = {"Thread", "Runnable", "AsyncTask", "ThreadPool"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView() {
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new SimpleItemDecoration(20));
        recyclerView.setAdapter(new SimpleAdapter());
    }


    class SimpleItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SimpleItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;
            if (parent.getChildPosition(view) == 0) {
                outRect.top = space;

            }
        }
    }

    class SimpleAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new SimpleHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SimpleHolder simpleHolder = (SimpleHolder) holder;
            simpleHolder.textView.setText(activity_title[position]);
            simpleHolder.textView.setTag(position);

        }

        @Override
        public int getItemCount() {
            return activity_title.length;
        }


        class SimpleHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public SimpleHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.title);
                textView.setOnClickListener(new SimpleOnClickListener());
            }

        }


        class SimpleOnClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Intent intent = new Intent();
                switch (activity_title[position]) {
                    case "Thread":
                        intent.setClass(MainActivity.this, ThreadActivity.class);
                        break;
                    case "Runnable":
                        intent.setClass(MainActivity.this, RunnableActivity.class);
                        break;
                    case "AsyncTask":
                        intent.setClass(MainActivity.this, AsyncTaskActivity.class);
                        break;
                    case "ThreadPool":
                        intent.setClass(MainActivity.this, ThreadPoolActivity.class);
                        break;
                }
                intent.putExtra(BaseActivity.TITLE_NAME, activity_title[position]);
                MainActivity.this.startActivity(intent);
                Toast.makeText(MainActivity.this, activity_title[position], Toast.LENGTH_SHORT).show();
            }
        }

    }
}
