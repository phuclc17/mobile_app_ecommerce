package com.example.proj_ecom_mobile.activity.user;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;

import com.example.proj_ecom_mobile.R;
import com.example.proj_ecom_mobile.fragment.HomeFragment;
import com.example.proj_ecom_mobile.fragment.ProfileFragment;
import com.example.proj_ecom_mobile.fragment.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // --- KÍCH HOẠT SEEDER THÔNG MINH ---
        // Truyền 'this' (context) vào để kiểm tra bộ nhớ
        // Nó sẽ tự động bỏ qua nếu đã chạy rồi.

        // -------------------------------------

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