package com.unitutoring.unitutoring.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.unitutoring.unitutoring.BackendSingleton;
import com.unitutoring.unitutoring.R;
import com.unitutoring.unitutoring.UserSingleton;
import com.unitutoring.unitutoring.events.RegisterEvent;
import com.unitutoring.unitutoring.models.Student;

public class SignupActivity extends BaseActivity {
    EditText mFirstNameEditText;
    EditText mLastNameEditText;
    EditText mEmailEditText;
    EditText mConfirmPasswordEditText;
    EditText mCreatePasswordEditText;

    LinearLayout mNextLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mFirstNameEditText = (EditText) findViewById(R.id.firstnameEditText);
        mLastNameEditText = (EditText) findViewById(R.id.lastnamdEditText);
        mEmailEditText = (EditText) findViewById(R.id.emailEditText);
        mCreatePasswordEditText = (EditText) findViewById(R.id.createPasswordEditText);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.confirmPasswordEditText);

        mNextLayout = (LinearLayout) findViewById(R.id.nextLayout);
        mNextLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    BackendSingleton.getInstance().register(
                            mEmailEditText.getText().toString(),
                            mCreatePasswordEditText.getText().toString(),
                            BackendSingleton.ACCOUNT_TYPE_STUDENT);
                } else {
                    Toast.makeText(SignupActivity.this, "Confirmation password does not match.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate() {
        return mCreatePasswordEditText.getText().toString().contentEquals(mConfirmPasswordEditText.getText().toString());
    }

    @Subscribe
    public void onRegister(RegisterEvent event) {
        if (event.isSuccessful) {
            Student student = new Student();
            student.email = mEmailEditText.getText().toString();
            UserSingleton.getInstance().setUser(student);

            Intent intent = new Intent(SignupActivity.this, MainViewActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Registration failed " + event.httpErrorCode, Toast.LENGTH_SHORT).show();
        }
    }
}
