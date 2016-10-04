package com.tfml.activity;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tfml.R;
import com.tfml.util.SetFonts;

public class ForgotPasswordActivity extends BaseActivity implements View.OnClickListener {
    private EditText txtUserId;
    private Button btnSubmit;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
    }
    public void init()
    {
        txtUserId=(EditText)findViewById(R.id.txt_user_id);
        btnSubmit=(Button)findViewById(R.id.btn_submit);
        imgBack=(ImageView)findViewById(R.id.img_forgot_pass_back);
        SetFonts.setFonts(this,imgBack,2);
        btnSubmit.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_submit:
                break;
            case R.id.img_forgot_pass_back:
                startActivity(new Intent(ForgotPasswordActivity.this,LoginActivity.class));
                break;
        }
    }
}
