package com.example.proj_ecom_mobile.activity.admin;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.model.Voucher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManageVoucherActivity extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fabAdd;
    private ImageView btnBack;
    private TextView tvTitle;
    private FirebaseFirestore db;
    private ArrayList<Voucher> voucherList;
    private ArrayList<String> displayList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);

        db = FirebaseFirestore.getInstance();
        listView = findViewById(R.id.list_view_data);
        fabAdd = findViewById(R.id.fab_add);
        btnBack = findViewById(R.id.btn_back_admin);
        tvTitle = findViewById(R.id.tv_header_title);

        tvTitle.setText("Quản lý Voucher");

        voucherList = new ArrayList<>();
        displayList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        listView.setAdapter(adapter);

        loadVouchers();

        btnBack.setOnClickListener(v -> finish());
        fabAdd.setOnClickListener(v -> showVoucherDialog(null));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Voucher v = voucherList.get(position);
            showOptions(v);
        });
    }

    private void loadVouchers() {
        db.collection("Vouchers").addSnapshotListener((value, error) -> {
            if (error != null) return;
            voucherList.clear();
            displayList.clear();
            DecimalFormat fmt = new DecimalFormat("###,###");
            for (DocumentSnapshot doc : value) {
                Voucher v = doc.toObject(Voucher.class);
                if (v != null) {
                    v.setId(doc.getId());
                    voucherList.add(v);

                    String valStr = v.getType().equals("PERCENT") ? v.getValue() + "%" : fmt.format(v.getValue()) + "đ";
                    displayList.add(v.getCode() + " | Giảm: " + valStr + "\nCòn lại: " + v.getQuantity());
                }
            }
            adapter.notifyDataSetChanged();
        });
    }

    private void showOptions(Voucher v) {
        String[] options = {"Sửa", "Xóa"};
        new AlertDialog.Builder(this)
                .setTitle("Mã: " + v.getCode())
                .setItems(options, (dialog, which) -> {
                    if (which == 0) showVoucherDialog(v);
                    else deleteVoucher(v.getId());
                })
                .show();
    }

    private void showVoucherDialog(Voucher v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_voucher, null);
        builder.setView(view);

        EditText edtCode = view.findViewById(R.id.edt_voucher_code);
        EditText edtValue = view.findViewById(R.id.edt_voucher_value);
        EditText edtQty = view.findViewById(R.id.edt_voucher_quantity);
        RadioGroup rgType = view.findViewById(R.id.rg_voucher_type);
        RadioButton rbFixed = view.findViewById(R.id.rb_type_fixed);
        RadioButton rbPercent = view.findViewById(R.id.rb_type_percent);

        if (v != null) {
            edtCode.setText(v.getCode());
            edtValue.setText(String.valueOf(v.getValue()));
            edtQty.setText(String.valueOf(v.getQuantity()));
            if ("PERCENT".equals(v.getType())) rbPercent.setChecked(true);
            else rbFixed.setChecked(true);
            edtCode.setEnabled(false);
        }

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String code = edtCode.getText().toString().trim().toUpperCase();
            String valStr = edtValue.getText().toString();
            String qtyStr = edtQty.getText().toString();
            String type = rbPercent.isChecked() ? "PERCENT" : "FIXED";

            if (code.isEmpty() || valStr.isEmpty() || qtyStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            double value = Double.parseDouble(valStr);
            long quantity = Long.parseLong(qtyStr);

            Map<String, Object> data = new HashMap<>();
            data.put("code", code);
            data.put("type", type);
            data.put("value", value);
            data.put("quantity", quantity);

            if (v == null) {
                db.collection("Vouchers").add(data);
            } else {
                db.collection("Vouchers").document(v.getId()).update(data);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void deleteVoucher(String id) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa Voucher")
                .setMessage("Bạn chắc chắn muốn xóa mã này?")
                .setPositiveButton("Xóa", (d, w) -> db.collection("Vouchers").document(id).delete())
                .setNegativeButton("Hủy", null)
                .show();
    }
}