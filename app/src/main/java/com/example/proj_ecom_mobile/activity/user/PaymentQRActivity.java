package com.example.proj_ecom_mobile.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.model.Order;
import com.google.firebase.firestore.FirebaseFirestore;

public class PaymentQRActivity extends AppCompatActivity {

    private ImageView imgQR;
    private TextView tvContent;
    private Button btnConfirm;
    private FirebaseFirestore db;

    private static final String BANK_ID = "MB";
    private static final String ACCOUNT_NO = "91979876543210";
    private static final String TEMPLATE = "compact2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_qr);

        db = FirebaseFirestore.getInstance();

        imgQR = findViewById(R.id.img_qr_code);
        tvContent = findViewById(R.id.tv_transfer_content);
        btnConfirm = findViewById(R.id.btn_confirm_paid);

        Order order = (Order) getIntent().getSerializableExtra("order_data");

        if (order != null) {
            long amount = (long) order.getTotalPrice();
            String content = order.getTransferContent();

            String qrUrl = "https://img.vietqr.io/image/" + BANK_ID + "-" + ACCOUNT_NO + "-" + TEMPLATE + ".png" +
                    "?amount=" + amount +
                    "&addInfo=" + content +
                    "&accountName=HUYNH NHAT";

            Glide.with(this).load(qrUrl).into(imgQR);

            tvContent.setText("Nội dung CK: " + content + "\nSố tiền: " + String.format("%,d", amount) + "đ");

            btnConfirm.setOnClickListener(v -> {
                updateOrderStatus(order.getId());
            });
        }
    }

    private void updateOrderStatus(String orderId) {
        db.collection("orders").document(orderId)
                .update("status", "Chờ xác nhận")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Xác nhận thành công! Vui lòng chờ Shop duyệt.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PaymentQRActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi cập nhật: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}