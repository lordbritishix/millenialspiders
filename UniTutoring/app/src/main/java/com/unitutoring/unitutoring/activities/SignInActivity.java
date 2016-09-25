package com.unitutoring.unitutoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.unitutoring.unitutoring.BackendSingleton;
import com.unitutoring.unitutoring.R;
import com.unitutoring.unitutoring.UserSingleton;
import com.unitutoring.unitutoring.events.LoginEvent;
import com.unitutoring.unitutoring.models.Student;

public class SignInActivity extends BaseActivity {
    EditText mEmailEditText;
    EditText mPasswordEditText;
    Button mLoginButton;
    Button mSignupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        BackendSingleton.init(getApplicationContext());

        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

        mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BackendSingleton.getInstance().login(mEmailEditText.getText().toString(),
                        mPasswordEditText.getText().toString());
            }
        });

        mSignupButton = (Button) findViewById(R.id.signup_Button);
        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        if (event.isSuccessful) {
            Student student = new Student();
            student.email = mEmailEditText.getText().toString();
            UserSingleton.getInstance().setUser(student);

            Intent intent = new Intent(SignInActivity.this, MainViewActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(SignInActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();

            mEmailEditText.setText("");
            mPasswordEditText.setText("");
        }
    }
}
