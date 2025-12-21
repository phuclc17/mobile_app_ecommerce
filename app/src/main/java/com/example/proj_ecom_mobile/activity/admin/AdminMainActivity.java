package com.example.proj_ecom_mobile.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.activity.auth.LoginActivity;
import com.example.proj_ecom_mobile.database.SessionManager;
import com.google.firebase.auth.FirebaseAuth;

public class AdminMainActivity extends AppCompatActivity {

    private Button btnProduct, btnUser, btnOrder, btnVoucher, btnLogout;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        sessionManager = new SessionManager(this);

        btnProduct = findViewById(R.id.btn_manage_product);
        btnUser = findViewById(R.id.btn_manage_user);
        btnOrder = findViewById(R.id.btn_manage_order);
        btnVoucher = findViewById(R.id.btn_manage_voucher);
        btnLogout = findViewById(R.id.btn_admin_logout);

        btnProduct.setOnClickListener(v -> startActivity(new Intent(this, ManageProductActivity.class)));
        btnUser.setOnClickListener(v -> startActivity(new Intent(this, ManageUserActivity.class)));
        btnOrder.setOnClickListener(v -> startActivity(new Intent(this, ManageOrderActivity.class)));
        btnVoucher.setOnClickListener(v -> startActivity(new Intent(this, ManageVoucherActivity.class)));

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            sessionManager.logoutUser();
            Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}