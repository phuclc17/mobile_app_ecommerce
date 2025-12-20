package com.example.proj_ecom_mobile.activity.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.adapter.CategoryAdapter;
import com.example.proj_ecom_mobile.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView rvCategory;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        rvCategory = findViewById(R.id.rvCategory);
        rvCategory.setLayoutManager(new LinearLayoutManager(this));

        categoryList = new ArrayList<>();
        categoryList.add(new Category("all", "ALL", null));
        categoryList.add(new Category("tops", "TOPS", null));
        categoryList.add(new Category("shirts", "SHIRTS", null));
        categoryList.add(new Category("jackets", "JACKETS & COATS", null));
        categoryList.add(new Category("skirts", "SKIRTS", null));
        categoryList.add(new Category("pants", "PANTS", null));
        categoryList.add(new Category("accessories", "ACCESSORIES", null));

        categoryAdapter = new CategoryAdapter(this, categoryList);
        rvCategory.setAdapter(categoryAdapter);
    }
}
