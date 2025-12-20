package com.example.proj_ecom_mobile.activity.auth;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proj_ecom_mobile.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText edtEmail;
    private Button btnReset;
    private TextView tvBack;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        edtEmail = findViewById(R.id.edt_reset_email);
        btnReset = findViewById(R.id.btn_reset_password);
        tvBack = findViewById(R.id.tv_back_login);

        // Sự kiện nút Gửi
        btnReset.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Vui lòng nhập Email!", Toast.LENGTH_SHORT).show();
                return;
            }

            resetPassword(email);
        });

        // Sự kiện nút Quay lại
        tvBack.setOnClickListener(v -> finish());
    }

    private void resetPassword(String email) {
        // Hàm thần thánh của Firebase giúp gửi mail reset pass
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Đã gửi email! Vui lòng kiểm tra hộp thư (cả mục Spam).",
                                Toast.LENGTH_LONG).show();
                        finish(); // Gửi xong thì đóng màn hình này lại
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this,
                                "Lỗi: " + task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}