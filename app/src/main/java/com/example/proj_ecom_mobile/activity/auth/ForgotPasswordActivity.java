package com.example.proj_ecom_mobile.activity.auth;

import android.os.Bundle;
import android.text.TextUtils;
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
    private TextView tvBack; // Sửa thành TextView cho khớp với XML của bạn
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        initView();

        // Xử lý sự kiện nút Gửi
        btnReset.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Vui lòng nhập Email!", Toast.LENGTH_SHORT).show();
                return;
            }

            resetPassword(email);
        });

        // Xử lý sự kiện nút Quay lại (Dòng chữ "Quay lại Đăng nhập")
        tvBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        // Ánh xạ đúng ID trong file XML bạn gửi
        edtEmail = findViewById(R.id.edt_reset_email);
        btnReset = findViewById(R.id.btn_reset_password);
        tvBack = findViewById(R.id.tv_back_login);
    }

    private void resetPassword(String email) {
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