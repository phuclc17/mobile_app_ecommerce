package com.example.proj_ecom_mobile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.activity.user.CartActivity;
import com.example.proj_ecom_mobile.database.SQLHelper;
import com.example.proj_ecom_mobile.model.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private ArrayList<CartItem> cartList;
    private SQLHelper sqlHelper;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    public CartAdapter(Context context, ArrayList<CartItem> cartList) {
        this.context = context;
        this.cartList = cartList;
        this.sqlHelper = new SQLHelper(context);
        this.mAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Liên kết với file item_cart.xml bạn vừa gửi
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartList.get(position);

        // 1. Hiển thị dữ liệu
        holder.txtName.setText(item.getProductName());
        holder.txtSize.setText("Size: " + item.getSize());
        holder.txtQty.setText(String.valueOf(item.getQuantity()));

        DecimalFormat formatter = new DecimalFormat("###,###,###");
        holder.txtPrice.setText(formatter.format(item.getProductPrice()) + "đ");

        Glide.with(context)
                .load(item.getProductImage())
                .placeholder(R.drawable.ic_home) // Đảm bảo bạn có ảnh này hoặc đổi ảnh khác
                .into(holder.imgProduct);

        // 2. Xử lý nút GIẢM (-)
        holder.btnMinus.setOnClickListener(v -> {
            int currentQty = item.getQuantity();
            if (currentQty > 1) {
                int newQty = currentQty - 1;
                updateItemQuantity(item, newQty, holder, position);
            } else {
                Toast.makeText(context, "Số lượng tối thiểu là 1", Toast.LENGTH_SHORT).show();
            }
        });

        // 3. Xử lý nút TĂNG (+)
        holder.btnPlus.setOnClickListener(v -> {
            int newQty = item.getQuantity() + 1;
            updateItemQuantity(item, newQty, holder, position);
        });

        // 4. Xử lý nút XÓA (Thùng rác)
        holder.btnDelete.setOnClickListener(v -> {
            FirebaseUser user = mAuth.getCurrentUser();

            if (user == null) {
                // --- KHÁCH: Xóa SQL ---
                sqlHelper.deleteCartItem(item.getProductId(), item.getSize());
                removeItemFromList(position);
                Toast.makeText(context, "Đã xóa (Khách)", Toast.LENGTH_SHORT).show();
            } else {
                // --- THÀNH VIÊN: Xóa Firebase ---
                // ID Document phải khớp với lúc lưu: UserID + ProductID + Size
                String docId = user.getUid() + "_" + item.getProductId() + "_" + item.getSize();

                db.collection("Cart").document(docId)
                        .delete()
                        .addOnSuccessListener(aVoid -> {
                            // Xóa thành công trên mạng -> Xóa trên list hiển thị
                            removeItemFromList(position);
                            Toast.makeText(context, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Lỗi xóa: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }

    // Hàm cập nhật số lượng chung cho cả SQL và Firebase
    private void updateItemQuantity(CartItem item, int newQty, CartViewHolder holder, int position) {
        FirebaseUser user = mAuth.getCurrentUser();

        // Cập nhật giao diện ngay lập tức cho mượt
        item.setQuantity(newQty);
        holder.txtQty.setText(String.valueOf(newQty));
        updateTotalActivity(); // Cập nhật tổng tiền

        if (user == null) {
            // Update SQL
            sqlHelper.updateQuantity(item.getProductId(), item.getSize(), newQty);
        } else {
            // Update Firebase
            String docId = user.getUid() + "_" + item.getProductId() + "_" + item.getSize();
            db.collection("Cart").document(docId)
                    .update("quantity", newQty)
                    .addOnFailureListener(e -> Toast.makeText(context, "Lỗi cập nhật SL", Toast.LENGTH_SHORT).show());
        }
    }

    // Hàm xóa item khỏi danh sách hiển thị
    private void removeItemFromList(int position) {
        if (position >= 0 && position < cartList.size()) {
            cartList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartList.size());
            updateTotalActivity();
        }
    }

    // Hàm gọi Activity cập nhật tổng tiền
    private void updateTotalActivity() {
        if (context instanceof CartActivity) {
            ((CartActivity) context).updateTotalPrice();
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtSize, txtQty, btnMinus, btnPlus;
        ImageView imgProduct, btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ đúng ID trong item_cart.xml của bạn
            imgProduct = itemView.findViewById(R.id.cart_item_img);
            txtName = itemView.findViewById(R.id.cart_item_name);
            txtPrice = itemView.findViewById(R.id.cart_item_price);
            txtSize = itemView.findViewById(R.id.cart_item_size);
            txtQty = itemView.findViewById(R.id.cart_item_qty);

            btnDelete = itemView.findViewById(R.id.btn_delete_item);
            btnMinus = itemView.findViewById(R.id.btn_minus);
            btnPlus = itemView.findViewById(R.id.btn_plus);
        }
    }
}