package com.example.proj_ecom_mobile.activity.user;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtAddress;
    private Button btnSave, btnBack;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        edtName = findViewById(R.id.edt_profile_name);
        edtPhone = findViewById(R.id.edt_profile_phone);
        edtAddress = findViewById(R.id.edt_profile_address);
        btnSave = findViewById(R.id.btn_save_profile);
        btnBack = findViewById(R.id.btn_back_profile);

        loadUserInfo();

        btnSave.setOnClickListener(v -> saveUserInfo());
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadUserInfo() {
        if(mAuth.getCurrentUser() == null) return;
        db.collection("Users").document(mAuth.getCurrentUser().getUid())
                .get().addOnSuccessListener(doc -> {
                    User u = doc.toObject(User.class);
                    if(u != null) {
                        if(u.getName() != null) edtName.setText(u.getName());
                        if(u.getPhone() != null) edtPhone.setText(u.getPhone());
                        if(u.getAddress() != null) edtAddress.setText(u.getAddress());
                    }
                });
    }

    private void saveUserInfo() {
        if(mAuth.getCurrentUser() == null) return;

        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("phone", phone);
        updates.put("address", address);

        db.collection("Users").document(mAuth.getCurrentUser().getUid())
                .update(updates)
                .addOnSuccessListener(v -> {
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}