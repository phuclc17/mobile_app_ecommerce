package com.example.proj_ecom_mobile.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.model.CartItem;
import com.example.proj_ecom_mobile.model.Order;
import com.example.proj_ecom_mobile.model.User;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class CheckoutActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtAddress, edtVoucher;
    private TextView txtSubTotal, txtShippingFee, txtDiscount, txtTotal;
    private Button btnConfirm, btnApplyVoucher;
    private ImageView btnBack;
    private RadioGroup rgShipping, rgPayment;
    private RadioButton rbStandard, rbExpress, rbCod, rbQr;
    private LinearLayout layoutItems;

    private ArrayList<CartItem> cartList;
    private double subTotal = 0;
    private double shippingFee = 30000;
    private double discountAmount = 0;
    private double finalTotal = 0;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initView();

        // Nhận dữ liệu từ Giỏ hàng
        cartList = (ArrayList<CartItem>) getIntent().getSerializableExtra("list_cart");
        subTotal = getIntent().getDoubleExtra("total_price", 0);

        if (cartList == null || cartList.isEmpty()) {
            loadCartData();
        } else {
            updateTotalUI();
            displayOrderItems();
        }

        rgShipping.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_standard) {
                shippingFee = 30000;
            } else if (checkedId == R.id.rb_express) {
                shippingFee = 60000;
            }
            updateTotalUI();
        });

        btnApplyVoucher.setOnClickListener(v -> handleApplyVoucher());
        prefillUserData();

        btnBack.setOnClickListener(v -> finish());
        btnConfirm.setOnClickListener(v -> handlePlaceOrder());
    }

    private void initView() {
        edtName = findViewById(R.id.edt_checkout_name);
        edtPhone = findViewById(R.id.edt_checkout_phone);
        edtAddress = findViewById(R.id.edt_checkout_address);
        edtVoucher = findViewById(R.id.edt_voucher);

        txtSubTotal = findViewById(R.id.txt_subtotal);
        txtShippingFee = findViewById(R.id.txt_shipping_fee);
        txtDiscount = findViewById(R.id.txt_discount);
        txtTotal = findViewById(R.id.txt_checkout_total);

        btnConfirm = findViewById(R.id.btn_confirm_order);
        btnApplyVoucher = findViewById(R.id.btn_apply_voucher);
        btnBack = findViewById(R.id.btn_back);

        rgShipping = findViewById(R.id.rg_shipping);
        rgPayment = findViewById(R.id.rg_payment);
        rbStandard = findViewById(R.id.rb_standard);
        rbExpress = findViewById(R.id.rb_express);
        rbCod = findViewById(R.id.rb_cod);
        rbQr = findViewById(R.id.rb_qr);
        layoutItems = findViewById(R.id.layout_checkout_items);
    }

    private void displayOrderItems() {
        if (layoutItems == null) return;
        layoutItems.removeAllViews();
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        if (cartList != null) {
            for (CartItem item : cartList) {
                TextView tv = new TextView(this);
                String content = "- " + item.getProductName() + " (Size: " + item.getSize() + ") x"
                        + item.getQuantity() + " : " + formatter.format(item.getProductPrice() * item.getQuantity()) + "đ";
                tv.setText(content);
                tv.setPadding(0, 10, 0, 10);
                tv.setTextColor(getResources().getColor(android.R.color.black));
                layoutItems.addView(tv);
            }
        }
    }

    private void prefillUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            db.collection("Users").document(user.getUid()).get()
                    .addOnSuccessListener(doc -> {
                        User u = doc.toObject(User.class);
                        if (u != null) {
                            if (u.getName() != null) edtName.setText(u.getName());
                            if (u.getPhone() != null) edtPhone.setText(u.getPhone());
                            if (u.getAddress() != null) edtAddress.setText(u.getAddress());
                        }
                    });
        }
    }

    private void loadCartData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        db.collection("Cart")
                .whereEqualTo("id_user", user.getUid())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    cartList = new ArrayList<>();
                    subTotal = 0;
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        CartItem item = doc.toObject(CartItem.class);
                        item.setProductId(doc.getString("id_product"));
                        item.setProductName(doc.getString("name"));
                        item.setProductImage(doc.getString("image"));
                        Double price = doc.getDouble("price");
                        item.setProductPrice(price != null ? price : 0);
                        cartList.add(item);
                        subTotal += item.getProductPrice() * item.getQuantity();
                    }
                    displayOrderItems();
                    updateTotalUI();
                });
    }

    // --- ĐOẠN CODE ĐÃ SỬA LOGIC VOUCHER ---
    private void handleApplyVoucher() {
        String code = edtVoucher.getText().toString().trim().toUpperCase();
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "Vui lòng nhập mã giảm giá", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("Vouchers").whereEqualTo("code", code).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);

                        // 1. Lấy đúng tên trường "value" mà bên Admin đã lưu
                        Double value = doc.getDouble("value");
                        String type = doc.getString("type");
                        Long quantity = doc.getLong("quantity");

                        if (quantity != null && quantity > 0) {
                            if (value != null) {
                                // 2. Kiểm tra loại voucher để tính toán
                                if ("PERCENT".equals(type)) {
                                    // Nếu là phần trăm: Giảm = Tổng tiền hàng * (value / 100)
                                    discountAmount = (subTotal * value) / 100;
                                } else {
                                    // Nếu là số tiền cố định (FIXED)
                                    discountAmount = value;
                                }

                                Toast.makeText(this, "Áp dụng thành công!", Toast.LENGTH_SHORT).show();
                                updateTotalUI(); // Cập nhật lại giao diện ngay
                            }
                        } else {
                            Toast.makeText(this, "Mã này đã hết lượt sử dụng", Toast.LENGTH_SHORT).show();
                            discountAmount = 0;
                            updateTotalUI();
                        }
                    } else {
                        discountAmount = 0;
                        Toast.makeText(this, "Mã không tồn tại", Toast.LENGTH_SHORT).show();
                        updateTotalUI();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateTotalUI() {
        finalTotal = subTotal + shippingFee - discountAmount;
        if (finalTotal < 0) finalTotal = 0;

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        txtSubTotal.setText(formatter.format(subTotal) + "đ");
        txtShippingFee.setText(formatter.format(shippingFee) + "đ");
        txtDiscount.setText("-" + formatter.format(discountAmount) + "đ");
        txtTotal.setText(formatter.format(finalTotal) + "đ");
    }

    private void handlePlaceOrder() {
        String name = edtName.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cartList == null || cartList.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) return;

        String orderId = UUID.randomUUID().toString();
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        String payMethod = rbQr.isChecked() ? "QR" : "COD";
        String transferContent = payMethod.equals("QR") ? "TT " + orderId.substring(0, 6).toUpperCase() : "";

        Order order = new Order(
                orderId, user.getUid(), user.getEmail(), currentDate, finalTotal, "Chờ xác nhận",
                new ArrayList<>(cartList), name, phone, address,
                rbStandard.isChecked() ? "Tiêu chuẩn" : "Hỏa tốc",
                shippingFee, discountAmount, payMethod, transferContent
        );

        db.collection("orders").document(orderId).set(order)
                .addOnSuccessListener(aVoid -> {
                    checkAndUpdateUserProfile(user.getUid(), name, phone, address);
                    processAfterOrder(user.getUid(), order, payMethod);
                });
    }

    private void checkAndUpdateUserProfile(String uid, String name, String phone, String address) {
        DocumentReference userRef = db.collection("Users").document(uid);
        userRef.get().addOnSuccessListener(doc -> {
            User u = doc.toObject(User.class);
            if (u != null) {
                Map<String, Object> updates = new HashMap<>();
                if (TextUtils.isEmpty(u.getName())) updates.put("name", name);
                if (TextUtils.isEmpty(u.getPhone())) updates.put("phone", phone);
                if (TextUtils.isEmpty(u.getAddress())) updates.put("address", address);
                if (!updates.isEmpty()) userRef.update(updates);
            }
        });
    }

    private void processAfterOrder(String userId, Order order, String payMethod) {
        // Trừ lượt dùng voucher (Nếu có dùng mã)
        String code = edtVoucher.getText().toString().trim().toUpperCase();
        if (!TextUtils.isEmpty(code) && discountAmount > 0) {
            db.collection("Vouchers").whereEqualTo("code", code).get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            DocumentSnapshot doc = queryDocumentSnapshots.getDocuments().get(0);
                            Long currentQty = doc.getLong("quantity");
                            if (currentQty != null && currentQty > 0) {
                                doc.getReference().update("quantity", currentQty - 1);
                            }
                        }
                    });
        }

        // Trừ kho và xóa giỏ hàng
        db.collection("Cart").whereEqualTo("id_user", userId).get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    WriteBatch batch = db.batch();
                    for (CartItem item : cartList) {
                        DocumentReference productRef = db.collection("products").document(item.getProductId());
                        String sizeField = "stock" + item.getSize();
                        batch.update(productRef, sizeField, com.google.firebase.firestore.FieldValue.increment(-item.getQuantity()));
                    }
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        batch.delete(doc.getReference());
                    }
                    batch.commit().addOnSuccessListener(aVoid -> {
                        if (payMethod.equals("QR")) {
                            Intent intent = new Intent(this, com.example.proj_ecom_mobile.activity.user.PaymentQRActivity.class);
                            intent.putExtra("order_data", order);
                            startActivity(intent);
                        } else {
                            Toast.makeText(this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(this, com.example.proj_ecom_mobile.activity.user.MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        finish();
                    });
                });
    }
}