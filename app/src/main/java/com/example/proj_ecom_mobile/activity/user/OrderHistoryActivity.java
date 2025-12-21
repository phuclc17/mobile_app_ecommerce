package com.example.proj_ecom_mobile.activity.user;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.adapter.OrderAdapter;
import com.example.proj_ecom_mobile.model.Order;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderAdapter adapter;
    private ArrayList<Order> orderList;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_products);

        recyclerView = findViewById(R.id.recycler_category_products);
        btnBack = findViewById(R.id.btn_back);

        orderList = new ArrayList<>();
        adapter = new OrderAdapter(this, orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(v -> finish());

        loadOrders();
    }

    private void loadOrders() {
        String uid = FirebaseAuth.getInstance().getUid();
        if(uid == null) return;

        FirebaseFirestore.getInstance().collection("orders")
                .whereEqualTo("userId", uid)
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if(error != null) return;
                    orderList.clear();
                    if(value != null){
                        for(DocumentSnapshot doc : value){
                            Order o = doc.toObject(Order.class);
                            orderList.add(o);
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }
}