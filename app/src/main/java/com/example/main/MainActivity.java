package com.example.main;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4;
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    TextView text,title;
    //popupx 시작메소드 showpopupx(); < 실행문구

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);

        //span
        Spannable span = (Spannable) title.getText();
//        span.setSpan(new ForegroundColorSpan(Color.MAGENTA),0,1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        span.setSpan(new ForegroundColorSpan(Color.MAGENTA),5,6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        span.setSpan(new ForegroundColorSpan(Color.MAGENTA),10,11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(1.5f),0,1,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(1.5f),5,6,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(1.5f),10,11,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //select_bar
        textInputLayout = findViewById(R.id.select_box);
        autoCompleteTextView = findViewById(R.id.text_item);

        text = findViewById(R.id.main_text);
        String[] select = {"정보처리기사","빅데이터분석기사","정보보안기사","정보통신기사","무선설비기사"};
        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(MainActivity.this,R.layout.select_bar, select);
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("정보처리기사")) {
                    text.setText("\n정보처리 기사 1회 : 2023.02.13 ~ 2023.03.15\n정보처리 기사 2회 : 2023.05.13 ~ 2023.06.04\n정보처리 기사 3회 : 2023.07.08 ~ 2023.07.23");
                }
                if(s.toString().equals("빅데이터분석기사")){
                    text.setText("\n빅데이터분석기사 1회 필기 : 2023.04.21\n빅데이터분석기사 1회 실기 : 2023.06.24\n빅데이터분석기사 2회 필기 : 2023.09.23\n빅데이터분석기사 2회 실기 : 2023.12.02");
                }
                if(s.toString().equals("정보보안기사")){
                    text.setText("\n정보보안기사 2회 필기 : 2023.06.19 ~ 2023.06.27\n정보보안기사 2회 실기 : 2023.07.15 ~ 2023.08.13\n정보보안기사 3회 필기 : 2023.09.14 ~ 2023.10.18\n정보보안기사 3회 실기 : 2023.11.04 ~ 2023.12.10");
                }
                if(s.toString().equals("정보통신기사")){
                    text.setText("\n정보통신기사 1회 : 2023.03.11 ~ 2023.03.21\n정보통신기사 2회 : 2023.06.17 ~ 2023.06.27\n정보통신기사 3회 : 2023.09.14 ~ 2023.10.18");
                }
                if(s.toString().equals("무선설비기사")){
                    text.setText("\n무선설비기사 1회 : 2023.04.10 ~ 2023.05.17\n무선설비기사 2회 : 2023.07.15 ~ 2023.08.13\n무선설비기사 3회 : 2023.06.12 ~ 2023.06.21\n무선설비기사 4회 : 2023.11.04 ~ 2023.12.10");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        autoCompleteTextView.setAdapter(itemAdapter);

        //버튼
        btn1 = findViewById(R.id.btn);
        btn2 = findViewById(R.id.btn1);
        btn3 = findViewById(R.id.btn2);
        btn4 = findViewById(R.id.btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Total_main.class);
                startActivity(intent);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Choice_sub.class);
                startActivity(intent);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Test_main.class);
                startActivity(intent);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetProblem pro = new GetProblem();
                Context con = getApplicationContext();
                if(!pro.insertMissProblem(con)){
                    Toast.makeText(getApplicationContext(),"시험 보기 항목을 먼저 수행해 주세요",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(getApplicationContext(),Miss_main.class);
                    startActivity(intent);
                }
            }
        });
    }
}
