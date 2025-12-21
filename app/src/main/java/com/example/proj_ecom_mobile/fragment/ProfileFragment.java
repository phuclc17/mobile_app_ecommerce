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
import com.example.proj_ecom_mobile.activity.user.UserInfoActivity;
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
    private LinearLayout layoutUserView, layoutGuestView;
    private LinearLayout btnOrderHistory, btnPersonalInfo;
    private Button btnLogout, btnLoginRequire;
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayUserInfo();
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
        layoutUserView = view.findViewById(R.id.layout_user_view);
        layoutGuestView = view.findViewById(R.id.layout_guest_view);
        btnOrderHistory = view.findViewById(R.id.btn_order_history);
        btnPersonalInfo = view.findViewById(R.id.btn_personal_info); // Cần thêm ID này vào XML
        btnLogout = view.findViewById(R.id.btn_logout);
        btnLoginRequire = view.findViewById(R.id.btn_login_require);
        rcvSuggest = view.findViewById(R.id.rcv_suggest_products);

        rcvSuggest.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvSuggest.setAdapter(productAdapter);
        loadRandomProducts();

        btnLoginRequire.setOnClickListener(v -> startActivity(new Intent(getContext(), LoginActivity.class)));

        btnOrderHistory.setOnClickListener(v -> {
            if (mAuth.getCurrentUser() != null) startActivity(new Intent(getContext(), OrderHistoryActivity.class));
            else Toast.makeText(getContext(), "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
        });

        if (btnPersonalInfo != null) {
            btnPersonalInfo.setOnClickListener(v -> {
                if (mAuth.getCurrentUser() != null) startActivity(new Intent(getContext(), UserInfoActivity.class));
            });
        }

        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            sessionManager.logoutUser();
            Toast.makeText(getContext(), "Đã đăng xuất!", Toast.LENGTH_SHORT).show();
            displayUserInfo();
        });
    }

    private void displayUserInfo() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            layoutUserView.setVisibility(View.VISIBLE);
            layoutGuestView.setVisibility(View.GONE);
            tvEmail.setText(user.getEmail());

            db.collection("Users").document(user.getUid()).get().addOnSuccessListener(doc -> {
                if(doc.exists()) {
                    String name = doc.getString("name");
                    tvUsername.setText(name != null ? name : "Thành viên");
                }
            });
        } else {
            layoutUserView.setVisibility(View.GONE);
            layoutGuestView.setVisibility(View.VISIBLE);
            tvUsername.setText("Khách");
            tvEmail.setText("Chưa đăng nhập");
        }
    }

    private void loadRandomProducts() {
        db.collection("products").get().addOnSuccessListener(snapshots -> {
            mListProduct.clear();
            ArrayList<Product> tempList = new ArrayList<>();
            for (DocumentSnapshot doc : snapshots) {
                Product p = doc.toObject(Product.class);
                if (p != null) { p.setId(doc.getId()); tempList.add(p); }
            }
            Collections.shuffle(tempList);
            int count = Math.min(tempList.size(), 6);
            for(int i=0; i<count; i++) mListProduct.add(tempList.get(i));
            productAdapter.notifyDataSetChanged();
        });
    }
}