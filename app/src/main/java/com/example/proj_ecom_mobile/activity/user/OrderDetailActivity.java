package com.example.proj_ecom_mobile.activity.user;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.adapter.CartAdapter;
import com.example.proj_ecom_mobile.model.CartItem;
import com.example.proj_ecom_mobile.model.Order;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvId, tvDate, tvStatus, tvTotal, tvName, tvPhone, tvAddress;
    private RecyclerView recyclerView;
    private CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        initView();

        Order order = (Order) getIntent().getSerializableExtra("order_item");
        if(order != null) setupData(order);

        btnBack.setOnClickListener(v -> finish());
    }

    private void initView() {
        btnBack = findViewById(R.id.btn_back_detail);
        tvId = findViewById(R.id.tv_order_id);
        tvDate = findViewById(R.id.tv_order_date);
        tvStatus = findViewById(R.id.tv_order_status);
        tvTotal = findViewById(R.id.tv_order_total);
        tvName = findViewById(R.id.tv_ship_name);
        tvPhone = findViewById(R.id.tv_ship_phone);
        tvAddress = findViewById(R.id.tv_ship_address);
        recyclerView = findViewById(R.id.recycler_order_items);
    }

    private void setupData(Order order) {
        tvId.setText("Mã đơn: " + (order.getId().length() > 8 ? order.getId().substring(0, 8) : order.getId()));
        tvDate.setText("Ngày đặt: " + order.getDate());
        tvStatus.setText(order.getStatus());

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        tvTotal.setText(formatter.format(order.getTotalPrice()) + "đ");

        tvName.setText("Người nhận: " + (order.getShippingName() != null ? order.getShippingName() : order.getUserEmail()));
        tvPhone.setText("SĐT: " + (order.getShippingPhone() != null ? order.getShippingPhone() : "N/A"));
        tvAddress.setText("Địa chỉ: " + (order.getShippingAddress() != null ? order.getShippingAddress() : "N/A"));

        if (order.getItems() != null) {
            ArrayList<CartItem> listItems = new ArrayList<>(order.getItems());
            adapter = new CartAdapter(this, listItems);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }
    }
}