package com.heystudio.heychat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class OTPVerification extends AppCompatActivity {

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() > 0){
                if (selectedEDITPosition == 0 ){
                    selectedEDITPosition = 1;
                    showKeyboard(otpEdit2);

                } else if (selectedEDITPosition == 1) {
                    selectedEDITPosition = 2;
                    showKeyboard(otpEdit3);

                } else if (selectedEDITPosition == 2) {
                    selectedEDITPosition = 3;
                    showKeyboard(otpEdit4);
                }
            }
        }
    };



    private EditText otpEdit1,otpEdit2,otpEdit3,otpEdit4;
    private TextView resendBtn;
    //true after every 60 s
    private boolean resendEnabled =  false;

    private int resendtime = 60;

    private int selectedEDITPosition = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        otpEdit1 = findViewById(R.id.otpEdit1);
        otpEdit2 = findViewById(R.id.otpEdit2);
        otpEdit3 = findViewById(R.id.otpEdit3);
        otpEdit4 = findViewById(R.id.otpEdit4);

        resendBtn = findViewById(R.id.resendBtn);
        final Button verifyBtn = findViewById(R.id.verifyBtn);

        final TextView otpMobile = findViewById(R.id.otpMobileno);

        //getting mobile no form register activity through intent
        final String getMobile = getIntent().getStringExtra("mobile");

        //setting mobile to TextView
        otpMobile.setText(getMobile);

        otpEdit1.addTextChangedListener(textWatcher);
        otpEdit2.addTextChangedListener(textWatcher);
        otpEdit3.addTextChangedListener(textWatcher);
        otpEdit4.addTextChangedListener(textWatcher);

        // by defautl open key board at otpedit1
        showKeyboard(otpEdit1);

        // start resend count down timer
        startCountDownTimer();

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resendEnabled){
                    // handeling resend code

                    //
                    startCountDownTimer();
                }
            }
        });
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String generateotp = otpEdit1.getText().toString()+otpEdit2.getText().toString()+otpEdit3.getText().toString()+otpEdit4.getText().toString();
                if(generateotp.length() == 4){
                    //handle your otp verification here
                }
            }
        });

    }
    private void showKeyboard(EditText otpedit){
        otpedit.requestFocus();

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(otpedit, InputMethodManager.SHOW_IMPLICIT);
    }
    private void startCountDownTimer(){
        resendEnabled = false;
        resendBtn.setTextColor(Color.parseColor("#99000000"));

        new CountDownTimer(resendtime * 1000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                resendBtn.setText("Resend Code("+(millisUntilFinished/1000)+")");
            }

            @Override
            public void onFinish() {
                resendEnabled = true;
                resendBtn.setText("Resend Code");
                resendBtn.setTextColor(getResources().getColor(R.color.primary));
            }
        }.start();

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_DEL){
            if(selectedEDITPosition == 3){
                selectedEDITPosition = 2;
                showKeyboard(otpEdit3);
            } else if (selectedEDITPosition == 2) {
                selectedEDITPosition = 1;
                showKeyboard(otpEdit2);
            } else if (selectedEDITPosition == 1) {
                selectedEDITPosition = 1;
                showKeyboard(otpEdit1);
            }

            return  true;
        }else {
            return super.onKeyUp(keyCode, event);
        }
    }
}