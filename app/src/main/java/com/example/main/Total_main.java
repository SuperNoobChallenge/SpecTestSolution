package com.example.main;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Total_main extends AppCompatActivity {
    int rand;
    //    문제 : total_question
    AutoCompleteTextView total_question;
    //    정답 : total_answer1~4
    Button total_answer[];
    //    다음문제 버튼 : total_next
    Button total_next;
    Dialog dialogx, dialogo, dialogcheck;
    LinkedList list;
    GetProblem pro;
    TextView total_checkview;
    Button total_checkview_back;
    String nums[] = {"①", "②", "③", "④"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.total_main);
        //dialog
        dialogx = new Dialog(Total_main.this);
        dialogx.setContentView(R.layout.popupxview);
        dialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogo = new Dialog(Total_main.this);
        dialogo.setContentView(R.layout.popupoview);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogcheck = new Dialog(Total_main.this);
        dialogcheck.setContentView(R.layout.checkview);
        dialogcheck.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        total_checkview = dialogcheck.findViewById(R.id.checkview_text);
        total_checkview_back = dialogcheck.findViewById(R.id.checkview_back);

        total_question = findViewById(R.id.total_question);

        total_answer = new Button[4];
        total_answer[0] = findViewById(R.id.total_answer1);
        total_answer[1] = findViewById(R.id.total_answer2);
        total_answer[2] = findViewById(R.id.total_answer3);
        total_answer[3] = findViewById(R.id.total_answer4);

        total_next = findViewById(R.id.total_next);

        pro = new GetProblem();
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_1));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_2));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_3));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_4));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.ch1_data_5));

        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.sq1));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.sq2));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.sm1));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.sm2));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.db1));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.db2));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.pro1));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.pro2));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.info1));
        pro.insertProblem(getApplicationContext().getResources().openRawResource(R.raw.info1));
        list = pro.getRandomList();

        rand = (int) (Math.random() * 4);
        total_question.setText(list.front.problem);
        total_answer[(0 + rand) % 4].setText(list.front.answer0);
        total_answer[(1 + rand) % 4].setText(list.front.answer1);
        total_answer[(2 + rand) % 4].setText(list.front.answer2);
        total_answer[(3 + rand) % 4].setText(list.front.answer3);

//        showpopupo();
//        showpopupx();
        total_answer[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        total_answer[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        total_answer[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        total_answer[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view, list);
            }
        });
        total_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(list);
            }
        });
    }

    public void go(LinkedList list) {
        if (list.nextNotNull()) {
            list.goNext();
            rand = (int) (Math.random() * 4);
            total_question.setText(list.front.problem);
            total_answer[(0 + rand) % 4].setText(list.front.answer0);
            total_answer[(1 + rand) % 4].setText(list.front.answer1);
            total_answer[(2 + rand) % 4].setText(list.front.answer2);
            total_answer[(3 + rand) % 4].setText(list.front.answer3);
        } else {
            list = pro.getRandomList();
            rand = (int) (Math.random() * 4);
            total_question.setText(list.front.problem);
            total_answer[(0 + rand) % 4].setText(list.front.answer0);
            total_answer[(1 + rand) % 4].setText(list.front.answer1);
            total_answer[(2 + rand) % 4].setText(list.front.answer2);
            total_answer[(3 + rand) % 4].setText(list.front.answer3);
        }
    }

    public void goaAndBack(View view, LinkedList list) {
        Boolean isCorrect = ((Button) view).getText().toString().equals(list.front.correct);
        if (isCorrect) {
            showpopupo();
        } else {
            showpopupx(view);
        }
    }

    public void showpopupx(View view) {
        dialogx.show();

        Button popupcheck = dialogx.findViewById(R.id.popcheck);
        popupcheck.setOnClickListener(new View.OnClickListener() {
            //정답 확인
            @Override
            public void onClick(View v) {

                int num = 0;
                if (total_answer[0].getText().toString().equals(list.front.correct)) {
                    num = 0;
                }
                if (total_answer[1].getText().toString().equals(list.front.correct)) {
                    num = 1;
                }
                if (total_answer[2].getText().toString().equals(list.front.correct)) {
                    num = 2;
                }
                if (total_answer[3].getText().toString().equals(list.front.correct)) {
                    num = 3;
                }
                total_checkview.setText(nums[num]);
                total_checkview_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogcheck.dismiss();
                    }
                });
                dialogcheck.show();
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
            if (list.nextNotNull()) {
                list.goNext();
            } else {
                list = pro.getRandomList();
            }
        }
        rand = (int) (Math.random() * 4);
        total_question.setText(list.front.problem);
        total_answer[(0 + rand) % 4].setText(list.front.answer0);
        total_answer[(1 + rand) % 4].setText(list.front.answer1);
        total_answer[(2 + rand) % 4].setText(list.front.answer2);
        total_answer[(3 + rand) % 4].setText(list.front.answer3);
    }
}