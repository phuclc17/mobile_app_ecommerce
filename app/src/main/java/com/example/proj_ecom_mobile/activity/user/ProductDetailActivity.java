package com.example.proj_ecom_mobile.activity.user;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.database.SQLHelper;
import com.example.proj_ecom_mobile.model.CartItem;
import com.example.proj_ecom_mobile.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView imgDetail, btnBack;
    private TextView txtName, txtPrice, txtDesc, txtStockInfo;
    private TextView btnS, btnM, btnL, btnXL;
    private Button btnAddToCart;
    private Product product;
    private SQLHelper sqlHelper;
    private String selectedSize = null;
    private int currentStockOfSize = 0;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        sqlHelper = new SQLHelper(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initView();

        btnS.setOnClickListener(v -> selectSize("S"));
        btnM.setOnClickListener(v -> selectSize("M"));
        btnL.setOnClickListener(v -> selectSize("L"));
        btnXL.setOnClickListener(v -> selectSize("XL"));

        if (getIntent().hasExtra("product_item")) {
            product = (Product) getIntent().getSerializableExtra("product_item");
            if (product != null) {
                txtName.setText(product.getName());
                txtDesc.setText(product.getDescription());
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                txtPrice.setText(formatter.format(product.getPrice()) + "đ");
                Glide.with(this).load(product.getImageUrl()).into(imgDetail);

                // Mặc định hiển thị tổng kho
                txtStockInfo.setText("Tổng tồn kho: " + product.getTotalStock());
            }
        }

        btnBack.setOnClickListener(v -> finish());

        btnAddToCart.setOnClickListener(v -> {
            if (selectedSize == null) {
                Toast.makeText(this, "Vui lòng chọn Size!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (currentStockOfSize <= 0) {
                Toast.makeText(this, "Size này đã hết hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (product != null) {
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if (currentUser == null) {
                    CartItem item = new CartItem(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getImageUrl(),
                            1,
                            selectedSize,
                            currentStockOfSize
                    );
                    sqlHelper.addToCart(item);
                    Toast.makeText(this, "Đã thêm vào giỏ: Size " + selectedSize, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                } else {
                    addToFirestore(currentUser.getUid());
                }
            }
        });
    }

    private void addToFirestore(String userId) {
        String cartId = userId + "_" + product.getId() + "_" + selectedSize;
        Map<String, Object> cartMap = new HashMap<>();
        cartMap.put("id_user", userId);
        cartMap.put("id_product", product.getId());
        cartMap.put("name", product.getName());
        cartMap.put("price", product.getPrice());
        cartMap.put("image", product.getImageUrl());
        cartMap.put("quantity", 1);
        cartMap.put("size", selectedSize);
        cartMap.put("stock", currentStockOfSize); // Lưu stock của size để check

        db.collection("Cart").document(cartId)
                .set(cartMap)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                    startActivity(intent);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void initView() {
        imgDetail = findViewById(R.id.img_detail);
        btnBack = findViewById(R.id.btn_back);
        txtName = findViewById(R.id.txt_detail_name);
        txtPrice = findViewById(R.id.txt_detail_price);
        txtDesc = findViewById(R.id.txt_detail_desc);
        txtStockInfo = findViewById(R.id.txt_detail_stock);
        btnAddToCart = findViewById(R.id.btn_add_to_cart);

        btnS = findViewById(R.id.size_s);
        btnM = findViewById(R.id.size_m);
        btnL = findViewById(R.id.size_l);
        btnXL = findViewById(R.id.size_xl);
    }

    private void selectSize(String size) {
        selectedSize = size;
        resetSizeButton(btnS);
        resetSizeButton(btnM);
        resetSizeButton(btnL);
        resetSizeButton(btnXL);

        TextView selectedBtn = null;
        if (size.equals("S")) { selectedBtn = btnS; currentStockOfSize = product.getStockS(); }
        else if (size.equals("M")) { selectedBtn = btnM; currentStockOfSize = product.getStockM(); }
        else if (size.equals("L")) { selectedBtn = btnL; currentStockOfSize = product.getStockL(); }
        else if (size.equals("XL")) { selectedBtn = btnXL; currentStockOfSize = product.getStockXL(); }

        if (selectedBtn != null) {
            selectedBtn.setBackgroundResource(R.drawable.bg_size_selected);
            selectedBtn.setTextColor(Color.WHITE);
        }

        txtStockInfo.setText("Kho size " + size + ": " + currentStockOfSize);
    }

    private void resetSizeButton(TextView btn) {
        btn.setBackgroundResource(R.drawable.bg_size_unselected);
        btn.setTextColor(Color.BLACK);
    }
}