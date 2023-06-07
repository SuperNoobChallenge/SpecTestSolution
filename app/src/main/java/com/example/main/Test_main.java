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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Test_main extends AppCompatActivity {
    int rand,cnt,max_cnt,fail_count=0;
    //    진행도 : test_cnt
    TextView test_cnt;
    //    문제 : test_question
    AutoCompleteTextView test_question;
    //    정답 : test_answer1~4
    Button test_answer[];
    //    다음문제 버튼 : test_next
    Button test_next;

    Dialog dialogx, dialogo, dialogcheck, scorePass, scoreFail;
    LinkedList list;
    Context con;
    Boolean stop_loss=true;
    TextView total_checkview;
    Button total_checkview_back;
    String nums[] = {"①", "②", "③", "④"};

    Button scorePassBtn,scoreFailBtn;
    TextView scorePassText,scoreFailText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        scorePass = new Dialog(Test_main.this);
        scorePass.setContentView(R.layout.scoreo_view);
        scorePass.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        scoreFail = new Dialog(Test_main.this);
        scoreFail.setContentView(R.layout.scorex_view);
        scoreFail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogx = new Dialog(Test_main.this);
        dialogx.setContentView(R.layout.popupxview);
        dialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogo = new Dialog(Test_main.this);
        dialogo.setContentView(R.layout.popupoview);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogcheck = new Dialog(Test_main.this);
        dialogcheck.setContentView(R.layout.checkview);
        dialogcheck.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        total_checkview = dialogcheck.findViewById(R.id.checkview_text);
        total_checkview_back = dialogcheck.findViewById(R.id.checkview_back);

        scorePassBtn= scorePass.findViewById(R.id.checkview_pass_back);
        scorePassText =scorePass.findViewById(R.id.text_pass_score);
        scoreFailBtn= scoreFail.findViewById(R.id.checkview_fail_back);
        scoreFailText =scoreFail.findViewById(R.id.text_fail_score);

        test_cnt = findViewById(R.id.test_cnt);
        test_question = findViewById(R.id.test_question);

        test_answer = new Button[4];
        test_answer[0] = findViewById(R.id.test_answer1);
        test_answer[1] = findViewById(R.id.test_answer2);
        test_answer[2] = findViewById(R.id.test_answer3);
        test_answer[3] = findViewById(R.id.test_answer4);

        test_next = findViewById(R.id.test_next);

        GetProblem pro = new GetProblem();
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
        list.delCount(50);

        con = getApplicationContext();
        WriteTextFile("Chxx<<11",con);

        cnt = 1;
        max_cnt = list.getNodeCount();
        test_cnt.setText(cnt+"/"+max_cnt);

        rand=(int)(Math.random()*4);
        test_question.setText(list.front.problem);
        test_answer[(0+rand)%4].setText(list.front.answer0);
        test_answer[(1+rand)%4].setText(list.front.answer1);
        test_answer[(2+rand)%4].setText(list.front.answer2);
        test_answer[(3+rand)%4].setText(list.front.answer3);

        test_answer[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        test_answer[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        test_answer[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        test_answer[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goaAndBack(view,list,con);
            }
        });
        test_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go(list,con);
            }
        });
    }

    public void go(LinkedList list, Context con){
        String str = cnt+"<<"+list.front.problem+"<<"+list.front.answer0+"<<"+list.front.answer1+"<<"+list.front.answer2+"<<"+list.front.answer3+"<<"+list.front.correct+"<<\n";
        WriteAppendTextFile(str,con);
        fail_count++;
        if(list.nextNotNull()){
            list.goNext();
            stop_loss=true;
            cnt++;
            rand=(int)(Math.random()*4);
            test_cnt.setText(cnt+"/"+max_cnt);
            test_question.setText(list.front.problem);
            test_answer[(0+rand)%4].setText(list.front.answer0);
            test_answer[(1+rand)%4].setText(list.front.answer1);
            test_answer[(2+rand)%4].setText(list.front.answer2);
            test_answer[(3+rand)%4].setText(list.front.answer3);
        }else {
            getScore();
        }
    }
    public void goaAndBack(View view, LinkedList list,Context con){
        Boolean isCorrect = ((Button)view).getText().toString().equals(list.front.correct);
        if(isCorrect){
            showpopupo();
        }else{
            if(stop_loss){
                stop_loss=false;
                fail_count++;
                String str = cnt+"<<"+list.front.problem+"<<"+list.front.answer0+"<<"+list.front.answer1+"<<"+list.front.answer2+"<<"+list.front.answer3+"<<"+list.front.correct+"<<\n";
                WriteAppendTextFile(str,con);
            }
            showpopupx();
        }
    }
    public void WriteTextFile(String data, Context context){
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("ErrProblem.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public void WriteAppendTextFile(String data, Context context){
        String str =  readFromFile(context);
        str = str + data;
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("ErrProblem.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(str);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("ErrProblem.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString).append("\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("Exception", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Exception", "Can not read file: " + e.toString());
        }
        return ret;
    }
    public void showpopupx(){
        dialogx.show();

        Button popupcheck = dialogx.findViewById(R.id.popcheck);
        popupcheck.setOnClickListener(new View.OnClickListener() {
            //정답 확인
            @Override
            public void onClick(View v) {
                int num=0;
                if(test_answer[0].getText().toString().equals(list.front.correct)){
                    num=0;
                }
                if(test_answer[1].getText().toString().equals(list.front.correct)){
                    num=1;
                }
                if(test_answer[2].getText().toString().equals(list.front.correct)){
                    num=2;
                }
                if(test_answer[3].getText().toString().equals(list.front.correct)){
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
            stop_loss=true;
            rand = (int) (Math.random() * 4);
            cnt++;
            test_cnt.setText(cnt + "/" + max_cnt);
            test_question.setText(list.front.problem);
            test_answer[(0 + rand) % 4].setText(list.front.answer0);
            test_answer[(1 + rand) % 4].setText(list.front.answer1);
            test_answer[(2 + rand) % 4].setText(list.front.answer2);
            test_answer[(3 + rand) % 4].setText(list.front.answer3);
        }else {
            getScore();
        }
    }

//    Button scorePassBtn,scoreFailBtn;
//    TextView scorePassText,scoreFailText;
    public void getScore(){
        int score = (max_cnt-fail_count)*2;
        if(score>= 60){
            scorePassText.setText(score+" 점");
            scorePass.show();
            scorePassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    home.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(home);
                    finish();
                    scoreFail.dismiss();
                }
            });
        }else{
            scoreFailText.setText(score+" 점");
            scoreFail.show();
            scoreFailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent home = new Intent(getApplicationContext(), MainActivity.class);
                    home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    home.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(home);
                    finish();
                    scoreFail.dismiss();
                }
            });
        }
    }
}