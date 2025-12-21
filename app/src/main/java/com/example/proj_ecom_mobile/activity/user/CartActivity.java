package com.example.proj_ecom_mobile.activity.user;

import android.content.Intent;
import android.os.Bundle;
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

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private ArrayList<CartItem> cartList;
    private TextView txtTotalPrice;
    private Button btnCheckout;
    private ImageView btnBack;
    private SQLHelper sqlHelper;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private double currentTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sqlHelper = new SQLHelper(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initView();

        cartList = new ArrayList<>();
        adapter = new CartAdapter(this, cartList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadCart();

        btnBack.setOnClickListener(v -> finish());

        btnCheckout.setOnClickListener(v -> {
            if (cartList.isEmpty()) {
                Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mAuth.getCurrentUser() == null) {
                Toast.makeText(this, "Vui lòng đăng nhập để thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            intent.putExtra("list_cart", cartList);
            intent.putExtra("total_price", currentTotal);
            startActivity(intent);
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.recycler_cart);
        txtTotalPrice = findViewById(R.id.txt_total_price);
        btnCheckout = findViewById(R.id.btn_checkout);
        btnBack = findViewById(R.id.btn_back);
    }

    public void loadCart() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            cartList.clear();
            cartList.addAll(sqlHelper.getCartItems());
            adapter.notifyDataSetChanged();
            updateTotalPrice();
        } else {
            db.collection("Cart").whereEqualTo("id_user", user.getUid())
                    .addSnapshotListener((value, error) -> {
                        if (error != null) return;
                        cartList.clear();
                        if (value != null) {
                            for (DocumentSnapshot doc : value) {
                                CartItem item = doc.toObject(CartItem.class);
                                item.setProductId(doc.getString("id_product"));
                                item.setProductName(doc.getString("name"));
                                item.setProductImage(doc.getString("image"));
                                Double price = doc.getDouble("price");
                                item.setProductPrice(price != null ? price : 0);
                                cartList.add(item);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        updateTotalPrice();
                    });
        }
    }

    public void updateTotalPrice() {
        currentTotal = 0;
        for (CartItem item : cartList) {
            currentTotal += item.getProductPrice() * item.getQuantity();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        txtTotalPrice.setText(formatter.format(currentTotal) + "đ");
    }
}