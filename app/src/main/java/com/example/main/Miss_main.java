package com.example.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Miss_main extends AppCompatActivity {
    int rand, cnt, max_cnt;
    //    진행도 : miss_cnt
    TextView miss_cnt;
    //    문제 : miss_question
    AutoCompleteTextView miss_question;
    //    정답 : miss_answer1~4
    Button miss_answer[];
    //    다음문제 버튼 : miss_next
    Button miss_next;
    Dialog dialogx, dialogo, dialogcheck,final_miss;
    LinkedList list;
    Context con;
    TextView total_checkview;
    Button total_checkview_back;
    String nums[] = {"①", "②", "③", "④"};

    Button btn_go_main;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.miss_main);

        dialogx = new Dialog(Miss_main.this);
        dialogx.setContentView(R.layout.popupxview);
        dialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo = new Dialog(Miss_main.this);
        dialogo.setContentView(R.layout.popupoview);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogcheck = new Dialog(Miss_main.this);
        dialogcheck.setContentView(R.layout.checkview);
        dialogcheck.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        total_checkview = dialogcheck.findViewById(R.id.checkview_text);
        total_checkview_back = dialogcheck.findViewById(R.id.checkview_back);

        final_miss = new Dialog(Miss_main.this);
        final_miss.setContentView(R.layout.xmain_view);
        final_miss.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_go_main = final_miss.findViewById(R.id.btn_mian_back);

        miss_cnt = findViewById(R.id.miss_cnt);
        miss_question = findViewById(R.id.miss_question);

        miss_answer = new Button[4];
        miss_answer[0] = findViewById(R.id.miss_answer1);
        miss_answer[1] = findViewById(R.id.miss_answer2);
        miss_answer[2] = findViewById(R.id.miss_answer3);
        miss_answer[3] = findViewById(R.id.miss_answer4);

        miss_next = findViewById(R.id.miss_next);
        GetProblem pro = new GetProblem();
        con = getApplicationContext();
        pro.insertMissProblem(con);
        list = pro.getRandomList();


        cnt = 1;
        max_cnt = list.getNodeCount();
        miss_cnt.setText(cnt + "/" + max_cnt);

        rand = (int) (Math.random() * 4);
        miss_question.setText(list.front.problem);
        miss_answer[(0 + rand) % 4].setText(list.front.answer0);
        miss_answer[(1 + rand) % 4].setText(list.front.answer1);
        miss_answer[(2 + rand) % 4].setText(list.front.answer2);
        miss_answer[(3 + rand) % 4].setText(list.front.answer3);

        miss_answer[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        miss_answer[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        miss_answer[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        miss_answer[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        miss_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(list, con);
            }
        });
    }

    public void go(LinkedList list, Context con) {
        if (list.nextNotNull()) {
            list.goNext();
            cnt++;
            rand = (int) (Math.random() * 4);
            miss_cnt.setText(cnt + "/" + max_cnt);
            miss_question.setText(list.front.problem);
            miss_answer[(0 + rand) % 4].setText(list.front.answer0);
            miss_answer[(1 + rand) % 4].setText(list.front.answer1);
            miss_answer[(2 + rand) % 4].setText(list.front.answer2);
            miss_answer[(3 + rand) % 4].setText(list.front.answer3);
        }else{
            gomain();
        }
    }

    public void goaAndBack(View view, LinkedList list) {
        Boolean isCorrect = ((Button) view).getText().toString().equals(list.front.correct);
        if (isCorrect) {
            showpopupo();
        } else {
            showpopupx();
        }
    }

    public void showpopupx() {
        dialogx.show();

        Button popupcheck = dialogx.findViewById(R.id.popcheck);
        popupcheck.setOnClickListener(new View.OnClickListener() {
            //정답 확인
            @Override
            public void onClick(View v) {
                int num=0;
                if(miss_answer[0].getText().toString().equals(list.front.correct)){
                    num=0;
                }
                if(miss_answer[1].getText().toString().equals(list.front.correct)){
                    num=1;
                }
                if(miss_answer[2].getText().toString().equals(list.front.correct)){
                    num=2;
                }
                if(miss_answer[3].getText().toString().equals(list.front.correct)){
                    num=3;
                }
                total_checkview.setText(nums[num]);
                dialogcheck.show();
                total_checkview_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogcheck.dismiss();
                    }
                });
                dialogx.dismiss();
            }
        });
        Button popuprestart = dialogx.findViewById(R.id.poprestart);

        popuprestart.setOnClickListener(new View.OnClickListener() {
            //다시 풀기
            @Override
            public void onClick(View v) {
                dialogx.dismiss();
            }
        });
    }

    class BackgroundThread extends Thread {
        public void run() {
            try {
                Thread.sleep(500);
                dialogo.dismiss();
            } catch (Exception e) {
            }
        }
    }

    //poppo 시작메소드 showpopupo();
    public void showpopupo() {
        dialogo.show();
        BackgroundThread thread = new BackgroundThread();
        thread.start();
        if (list.nextNotNull()) {
            list.goNext();
            rand = (int) (Math.random() * 4);
            cnt++;
            miss_cnt.setText(cnt + "/" + max_cnt);
            miss_question.setText(list.front.problem);
            miss_answer[(0 + rand) % 4].setText(list.front.answer0);
            miss_answer[(1 + rand) % 4].setText(list.front.answer1);
            miss_answer[(2 + rand) % 4].setText(list.front.answer2);
            miss_answer[(3 + rand) % 4].setText(list.front.answer3);
        }else {
            gomain();
        }
    }
    public void gomain(){
        final_miss.show();
        btn_go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(home);
                finish();
                final_miss.dismiss();
            }
        });
    }
}