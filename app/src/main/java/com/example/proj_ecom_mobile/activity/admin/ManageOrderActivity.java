package com.example.proj_ecom_mobile.activity.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.adapter.AdminOrderAdapter;
import com.example.proj_ecom_mobile.model.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.ArrayList;

public class ManageOrderActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fabAdd;
    private ImageView btnBack;
    private TextView tvTitle;
    private FirebaseFirestore db;
    private ArrayList<Order> orderList;
    private AdminOrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);

        db = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.list_view_data);
        fabAdd = findViewById(R.id.fab_add);
        btnBack = findViewById(R.id.btn_back_admin);
        tvTitle = findViewById(R.id.tv_header_title);

        tvTitle.setText("Quản lý Đơn hàng");
        fabAdd.setVisibility(View.GONE);

        orderList = new ArrayList<>();
        adapter = new AdminOrderAdapter(this, orderList);
        listView.setAdapter(adapter);

        loadOrders();

        btnBack.setOnClickListener(v -> finish());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Order order = orderList.get(position);
            showStatusDialog(order);
        });
    }

    private void loadOrders() {
        db.collection("orders").orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) return;
                    orderList.clear();
                    if (value != null) {
                        for (DocumentSnapshot doc : value) {
                            Order o = doc.toObject(Order.class);
                            if (o != null) {
                                o.setId(doc.getId());
                                orderList.add(o);
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
    }

    private void showStatusDialog(Order order) {
        String[] statuses = {"Chờ xác nhận", "Đang xử lý", "Đang giao", "Đã giao", "Đã hủy"};
        new AlertDialog.Builder(this)
                .setTitle("Cập nhật trạng thái")
                .setItems(statuses, (dialog, which) -> {
                    updateStatus(order.getId(), statuses[which]);
                })
                .show();
    }

    private void updateStatus(String orderId, String newStatus) {
        db.collection("orders").document(orderId).update("status", newStatus)
                .addOnSuccessListener(v -> Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show());
    }
}