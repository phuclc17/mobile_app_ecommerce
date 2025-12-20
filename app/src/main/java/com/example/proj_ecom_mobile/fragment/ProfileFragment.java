package com.example.proj_ecom_mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.activity.auth.LoginActivity;
import com.example.proj_ecom_mobile.activity.user.OrderHistoryActivity;
import com.example.proj_ecom_mobile.adapter.ProductAdapter;
import com.example.proj_ecom_mobile.database.SessionManager;
import com.example.proj_ecom_mobile.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;

public class ProfileFragment extends Fragment {

    private TextView tvUsername, tvEmail;
    private ImageView imgAvatar;
    private LinearLayout btnOrderHistory;
    private Button btnLogout;

    private RecyclerView rcvSuggest;
    private ArrayList<Product> mListProduct;
    private ProductAdapter productAdapter;
    private FirebaseFirestore db;

    private FirebaseAuth mAuth;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initData();
        initView(view);
        displayUserInfo();
        loadRandomProducts();

        return view;
    }

    private void initData() {
        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(getContext());
        db = FirebaseFirestore.getInstance();

        mListProduct = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), mListProduct);
    }

    private void initView(View view) {
        tvUsername = view.findViewById(R.id.tv_username);
        tvEmail = view.findViewById(R.id.tv_email);
        imgAvatar = view.findViewById(R.id.img_avatar_profile);
        btnOrderHistory = view.findViewById(R.id.btn_order_history);
        btnLogout = view.findViewById(R.id.btn_logout);

        rcvSuggest = view.findViewById(R.id.rcv_suggest_products);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rcvSuggest.setLayoutManager(gridLayoutManager);
        rcvSuggest.setAdapter(productAdapter);

        btnOrderHistory.setOnClickListener(v -> {
            if (mAuth.getCurrentUser() != null) {
                Intent intent = new Intent(getContext(), OrderHistoryActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Vui lòng đăng nhập để xem lịch sử", Toast.LENGTH_SHORT).show();
            }
        });

        btnLogout.setOnClickListener(v -> {
            if (mAuth.getCurrentUser() != null) {
                mAuth.signOut();
                sessionManager.logoutUser();
                Toast.makeText(getContext(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(getContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            if (getActivity() != null) getActivity().finish();
        });
    }

    private void displayUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            tvEmail.setText(user.getEmail());
            String name = sessionManager.getKeyEmail();
            tvUsername.setText(name != null ? name : "Thành viên Minimalish");
            btnLogout.setText("Đăng xuất");
        } else {
            tvUsername.setText("Khách");
            tvEmail.setText("Chưa đăng nhập");
            btnLogout.setText("Đăng nhập");
        }
    }

    private void loadRandomProducts() {
        // --- ĐÃ SỬA: Chữ "products" viết thường cho khớp với ảnh Firebase bạn gửi ---
        String collectionName = "products";

        db.collection(collectionName)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        // Nếu vẫn không thấy thì chắc chắn do Rules chưa mở
                        Toast.makeText(getContext(), "Lỗi: Vẫn không thấy bảng '" + collectionName + "'. Kiểm tra lại Rules!", Toast.LENGTH_LONG).show();
                        return;
                    }

                    mListProduct.clear();
                    ArrayList<Product> tempList = new ArrayList<>();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        try {
                            Product product = doc.toObject(Product.class);
                            if (product != null) {
                                product.setId(doc.getId());
                                tempList.add(product);
                            }
                        } catch (Exception e) {
                            Log.e("LoadProduct", "Lỗi convert: " + e.getMessage());
                        }
                    }

                    if (tempList.isEmpty()) {
                        // Toast.makeText(getContext(), "Đã tìm thấy bảng nhưng lỗi tên cột dữ liệu", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Collections.shuffle(tempList);

                    int displayCount = Math.min(tempList.size(), 6);
                    for (int i = 0; i < displayCount; i++) {
                        mListProduct.add(tempList.get(i));
                    }

                    productAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi tải: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}