package com.example.proj_ecom_mobile.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.adapter.CartAdapter;
import com.example.proj_ecom_mobile.database.SQLHelper;
import com.example.proj_ecom_mobile.model.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerCart;
    private TextView txtTotalPrice;
    private Button btnCheckout;
    private ImageView btnBack;
    private SQLHelper sqlHelper;
    private ArrayList<CartItem> cartList;
    private CartAdapter adapter;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sqlHelper = new SQLHelper(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        cartList = new ArrayList<>();

        initView();

        // Load dữ liệu
        loadCartData();

        btnBack.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(v -> {
            if (cartList.size() > 0) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Giỏ hàng đang trống!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        recyclerCart = findViewById(R.id.recycler_cart);
        txtTotalPrice = findViewById(R.id.txt_total_price);
        btnCheckout = findViewById(R.id.btn_checkout);
        btnBack = findViewById(R.id.btn_back);
    }

    private void loadCartData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            // --- KHÁCH (Chưa đăng nhập) -> Lấy SQL ---
            cartList = sqlHelper.getCartItems();
            setupRecyclerView();
        } else {
            // --- THÀNH VIÊN (Đã đăng nhập) -> Lấy Firebase ---
            db.collection("Cart")
                    .whereEqualTo("id_user", currentUser.getUid())
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        cartList.clear(); // Xóa sạch list tạm
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            String id = doc.getString("id_product");
                            String name = doc.getString("name");
                            Double price = doc.getDouble("price");
                            String image = doc.getString("image");
                            Long quantity = doc.getLong("quantity");
                            String size = doc.getString("size");

                            if (id != null && price != null && quantity != null) {
                                cartList.add(new CartItem(id, name, price, image, quantity.intValue(), size));
                            }
                        }
                        setupRecyclerView();
                    })
                    .addOnFailureListener(e -> Toast.makeText(CartActivity.this, "Lỗi tải giỏ hàng", Toast.LENGTH_SHORT).show());
        }
    }

    private void setupRecyclerView() {
        adapter = new CartAdapter(this, cartList);
        recyclerCart.setLayoutManager(new LinearLayoutManager(this));
        recyclerCart.setAdapter(adapter);
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        double total = 0;
        for (CartItem item : cartList) {
            total += item.getProductPrice() * item.getQuantity();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        txtTotalPrice.setText(formatter.format(total) + "đ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartData();
    }
}