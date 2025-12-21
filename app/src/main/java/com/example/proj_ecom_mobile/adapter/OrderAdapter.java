package com.example.proj_ecom_mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proj_ecom_mobile.model.Order;
import com.example.proj_ecom_mobile.activity.user.OrderDetailActivity;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private Context context;
    private ArrayList<Order> orderList;

    public OrderAdapter(Context context, ArrayList<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orderList.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        holder.text1.setText("Đơn hàng: " + (order.getDate() != null ? order.getDate() : "N/A"));
        String status = order.getStatus();
        String total = formatter.format(order.getTotalPrice()) + "đ";
        holder.text2.setText("Trạng thái: " + status + " | Tổng: " + total);

        if ("Đã hủy".equals(status)) holder.text2.setTextColor(Color.RED);
        else if ("Đã giao".equals(status)) holder.text2.setTextColor(Color.GREEN);
        else holder.text2.setTextColor(Color.DKGRAY);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);
            intent.putExtra("order_item", order);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
            text2 = itemView.findViewById(android.R.id.text2);
        }
    }
}