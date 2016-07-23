package com.example.com.my_asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_open;
    TextView tv_tips;
    ProgressBar progr;
    AsyncTest task;
    TextView tv_progess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_tips = (TextView) findViewById(R.id.tv_tips);
        btn_open = (Button) findViewById(R.id.btn_open);
        progr = (ProgressBar) findViewById(R.id.progess);
        progr.setVisibility(View.INVISIBLE);
        tv_progess = (TextView) findViewById(R.id.tv_progess);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                task = new AsyncTest();
                task.execute();
                btn_open.setEnabled(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.cancel(true);
//        task = null;
    }

    class AsyncTest extends AsyncTask<Integer, Integer, String> {


        @Override
        protected void onPreExecute() {
            tv_tips.setText("开始执行异步加载…………");
            progr.setVisibility(View.VISIBLE);
            tv_progess.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {

            for (int i = 1; i <= 100; i ++) {
                if(isCancelled())
                {
                    return null;
                }
                try {
                    Thread.sleep(50);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //处理加载出现100%
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "加载完毕……";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv_tips.setText(s);
            tv_progess.setVisibility(View.INVISIBLE);
            progr.setVisibility(View.INVISIBLE);
            btn_open.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tv_progess.setText("加载中……"+values[0]+"%");
            progr.setProgress(values[0]);
        }

//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//            task =null;
//        }
    }
}
