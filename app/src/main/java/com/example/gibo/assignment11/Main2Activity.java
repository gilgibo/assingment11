package com.example.gibo.assignment11;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    TextView tv;
    ImageView img;
    EditText et;
    MyTask myTask;
    Button bt;
    int settime = 0;
    int where = 0;
    int[] imgs = {R.drawable.abocado, R.drawable.banana, R.drawable.cherry, R.drawable.cranberry, R.drawable.grape
    ,R.drawable.kiwi, R.drawable.orange, R.drawable.watermelon};
    String[] imgsname = { "아보카도", "바나나", "체리", "크란베리", "포도", "키위", "오렌지", "수박" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv = (TextView)findViewById(R.id.tv);
        img = (ImageView)findViewById(R.id.img);
        et = (EditText)findViewById(R.id.et);
        bt = (Button)findViewById(R.id.bt);
        img.setImageResource(R.drawable.start);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                where = 1;
                myTask.cancel(true);
                myTask = null;
            }
        });
    }

    public void onClick(View v){
        if(v.getId() == R.id.bt){
            if(bt.getText().equals("시작하기")){
                img.setImageResource(0);
                myTask = new MyTask();
                bt.setText("처음으로");
                myTask.execute(0);
            }else if(bt.getText().equals("처음으로")){
                if(where == 1) {
                    img.setImageResource(R.drawable.start);
                    bt.setText("시작하기");
                    tv.setText("");
                }
                else {
                    where = -1;
                    img.setImageResource(R.drawable.start);
                    myTask.cancel(true);
                    myTask = null;
                }
            }
        }
    }

    class MyTask extends AsyncTask<Integer, Integer, Integer> {

        int index = 0;
        int i = 0;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            where = 0;
            settime = Integer.parseInt(et.getText().toString());
            tv.setText("시작부터 0초");
            tv.setTextColor(Color.BLUE);
            img.setImageResource(imgs[0]);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            for(i = 1 ; ; i++) {
                if (isCancelled() == true)
                    return null;
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            index = ((values[0] - 1)/settime) % imgs.length;
            tv.setText("시작부터 " + values[0]+"초");
            img.setImageResource(imgs[index]);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(where == 1){
                tv.setText(imgsname[index]+"(이)가 "+ (i - 2) +"초에 선택 되었습니다.");
            }else if( where == -1){
                bt.setText("시작하기");
                tv.setText("");
            }
        }
    }
}
