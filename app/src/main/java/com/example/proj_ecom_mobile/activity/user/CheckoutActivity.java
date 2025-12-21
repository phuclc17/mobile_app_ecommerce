package com.example.proj_ecom_mobile.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.database.SQLHelper;
import com.example.proj_ecom_mobile.model.CartItem;
import com.example.proj_ecom_mobile.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CheckoutActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtAddress;
    private TextView txtTotal;
    private Button btnConfirm;
    private ImageView btnBack;

    private ArrayList<CartItem> cartList;
    private double totalPrice = 0;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private SQLHelper sqlHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        sqlHelper = new SQLHelper(this);
        cartList = new ArrayList<>();

        initView();
        loadCartData();

        btnBack.setOnClickListener(v -> finish());
        btnConfirm.setOnClickListener(v -> handlePlaceOrder());
    }

    private void initView() {
        edtName = findViewById(R.id.edt_checkout_name);
        edtPhone = findViewById(R.id.edt_checkout_phone);
        edtAddress = findViewById(R.id.edt_checkout_address);
        txtTotal = findViewById(R.id.txt_checkout_total);
        btnConfirm = findViewById(R.id.btn_confirm_order);
        btnBack = findViewById(R.id.btn_back);
    }

    private void loadCartData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Vui lòng đăng nhập để thanh toán!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Lấy giỏ hàng từ Firebase về để tính tiền
        db.collection("Cart")
                .whereEqualTo("id_user", user.getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    cartList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        String id = doc.getString("id_product");
                        String name = doc.getString("name");
                        Double price = doc.getDouble("price");
                        String image = doc.getString("image");
                        Long quantity = doc.getLong("quantity");
                        String size = doc.getString("size");

                        if (id != null) {
                            // Số 0 ở cuối là stock, ta chỉ cần truyền tạm để tạo object
                            cartList.add(new CartItem(id, name, price, image, quantity.intValue(), size, 0));
                        }
                    }
                    calculateTotal();
                });
    }

    private void calculateTotal() {
        totalPrice = 0;
        for (CartItem item : cartList) {
            totalPrice += item.getProductPrice() * item.getQuantity();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        txtTotal.setText(formatter.format(totalPrice) + "đ");
    }

    private void handlePlaceOrder() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin giao hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        if (cartList.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        // --- TẠO ĐƠN HÀNG ---
        String orderId = UUID.randomUUID().toString();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());

        Order order = new Order(
                orderId,
                user.getUid(),
                user.getEmail(),
                currentDate,
                totalPrice,
                "Chờ xác nhận", // Trạng thái mặc định
                cartList,
                name,      // Tên người nhận
                phone,     // SĐT người nhận
                address    // Địa chỉ người nhận
        );

        // Lưu đơn hàng lên Firebase
        db.collection("Orders").document(orderId)
                .set(order)
                .addOnSuccessListener(aVoid -> {
                    // Đặt thành công -> Trừ kho và Xóa giỏ
                    processAfterOrder(user.getUid());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Lỗi đặt hàng: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void processAfterOrder(String userId) {
        // 1. TRỪ TỒN KHO (STOCK)
        for (CartItem item : cartList) {
            DocumentReference productRef = db.collection("products").document(item.getProductId());

            // Dùng Transaction để đảm bảo an toàn dữ liệu khi trừ
            db.runTransaction(transaction -> {
                DocumentSnapshot snapshot = transaction.get(productRef);
                Long currentStock = snapshot.getLong("stock");
                if (currentStock == null) currentStock = 0L;

                long newStock = currentStock - item.getQuantity();
                if (newStock < 0) newStock = 0; // Không để âm

                transaction.update(productRef, "stock", newStock);
                return null;
            });
        }

        // 2. XÓA GIỎ HÀNG TRÊN FIREBASE
        db.collection("Cart")
                .whereEqualTo("id_user", userId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    WriteBatch batch = db.batch();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        batch.delete(doc.getReference());
                    }
                    batch.commit().addOnSuccessListener(aVoid -> {
                        // Xóa xong hết mới thông báo
                        Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công!", Toast.LENGTH_LONG).show();

                        // Về trang chủ
                        Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    });
                });
    }
}