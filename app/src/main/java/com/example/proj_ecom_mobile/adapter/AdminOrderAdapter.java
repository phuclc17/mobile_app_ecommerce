package com.example.proj_ecom_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.model.Order;
import com.example.proj_ecom_mobile.model.CartItem;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdminOrderAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Order> list;

    public AdminOrderAdapter(Context context, ArrayList<Order> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }
    @Override
    public Object getItem(int i) { return list.get(i); }
    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) view = LayoutInflater.from(context).inflate(R.layout.item_admin_order, viewGroup, false);

        Order o = list.get(i);
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        ((TextView) view.findViewById(R.id.tv_admin_id)).setText("Đơn: " + o.getId().substring(0, 6));
        ((TextView) view.findViewById(R.id.tv_admin_email)).setText("KH: " + o.getUserEmail());
        ((TextView) view.findViewById(R.id.tv_admin_total)).setText("Tổng: " + formatter.format(o.getTotalPrice()) + "đ");
        ((TextView) view.findViewById(R.id.tv_admin_status)).setText("Trạng thái: " + o.getStatus());

        TextView tvProducts = view.findViewById(R.id.tv_admin_products);
        StringBuilder productInfo = new StringBuilder();
        if (o.getItems() != null) {
            for (CartItem item : o.getItems()) {
                productInfo.append("- ").append(item.getProductName())
                        .append(" (Size: ").append(item.getSize())
                        .append(") x").append(item.getQuantity()).append("\n");
            }
        }
        tvProducts.setText(productInfo.toString());

        TextView tvPayment = view.findViewById(R.id.tv_admin_payment);
        TextView tvQR = view.findViewById(R.id.tv_admin_transfer_code);

        tvPayment.setText("Thanh toán: " + o.getPaymentMethod());
        if ("QR".equals(o.getPaymentMethod())) {
            tvQR.setVisibility(View.VISIBLE);
            tvQR.setText("ND: " + o.getTransferContent());
        } else {
            tvQR.setVisibility(View.GONE);
        }
        return view;
    }
}