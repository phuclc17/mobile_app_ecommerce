package com.example.proj_ecom_mobile.activity.user;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;

import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.database.Seeder;
import com.example.proj_ecom_mobile.fragment.HomeFragment;
import com.example.proj_ecom_mobile.fragment.ProfileFragment;
import com.example.proj_ecom_mobile.fragment.SearchFragment;
import com.example.proj_ecom_mobile.model.CartItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // --- KHAI BÁO BIẾN GIỎ HÀNG (QUAN TRỌNG) ---
    public static ArrayList<CartItem> manggiohang;
    // -------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo giỏ hàng nếu chưa có
        if (manggiohang == null) {
            manggiohang = new ArrayList<>();
        }

        Seeder.seedProductData(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, new HomeFragment())
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (id == R.id.nav_search) {
                    selectedFragment = new SearchFragment();
                } else if (id == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, selectedFragment)
                            .commit();
                }
                return true;
            }
        });
    }
}