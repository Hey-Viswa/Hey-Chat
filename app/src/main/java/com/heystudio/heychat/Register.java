package com.heystudio.heychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.heystudio.heychat.databinding.ActivityRegisterBinding;

public class Register extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    private boolean passwordShowing = false;
    private boolean conPasswordShowing = false;

    final EditText email = findViewById(R.id.EmailEdt);
    final EditText mobile = findViewById(R.id.MobileEdt);
    final EditText password = findViewById(R.id.passwordEdt);
    final EditText conPassword = findViewById(R.id.conpasswordEdt);
    final AppCompatButton  signUpBtn = findViewById(R.id.signUpBtn);
    final ImageView conPasswordIcon = findViewById(R.id.conpasswordIcon);
    final ImageView passwordIcon = findViewById(R.id.passwordIcon);
    final TextView signInBtn = findViewById(R.id.signInBtn);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        firebaseAuth = FirebaseAuth.getInstance();


        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if password is showing or not
                if (passwordShowing) {
                    passwordShowing = false;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                } else {
                    passwordShowing = true;
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_hide);
                }
                // move the cursor at last of the text
                password.setSelection(password.length());
            }
        });

        conPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking if password is showing or not
                if (conPasswordShowing) {
                    conPasswordShowing = false;
                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.password_show);
                } else {
                    conPasswordShowing = true;
                    conPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    conPasswordIcon.setImageResource(R.drawable.password_hide);
                }
                // move the cursor at last of the text
                password.setSelection(password.length());
            }
        });
        binding.signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String getMobileTxt = mobile.getText().toString();
                final String getEmailTxt = email.getText().toString();
                // Opening OTP Verification Activity along with mobile and email
                Intent intent = new Intent(Register.this,OTPVerification.class);
                intent.putExtra("mobile",getMobileTxt);
                intent.putExtra("email",getEmailTxt);

                startActivity(intent);
            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.EmailEdt.getText().toString();
                String password = binding.passwordEdt.getText().toString();
                String ConfirmPassword = binding.conpasswordEdt.getText().toString();

                if (!email.isEmpty() && !password.isEmpty() && !ConfirmPassword.isEmpty()){
                    if (password.equals(ConfirmPassword)){
                        firebaseAuth.createUserWithEmailAndPassword(email,password)
                                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            Intent intent = new Intent(Register.this,Login.class);
                                            startActivity(intent);
                                        }else {
                                            Toast.makeText(Register.this, task.getException().toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        Toast.makeText(Register.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Empty Fields are not Allowed !!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}