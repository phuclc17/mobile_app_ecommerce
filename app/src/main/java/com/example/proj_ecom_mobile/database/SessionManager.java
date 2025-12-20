package com.example.proj_ecom_mobile.database;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.proj_ecom_mobile.activity.auth.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    // Khai báo SharedPreferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Chế độ private
    int PRIVATE_MODE = 0;

    // Tên file Sharedpref
    private static final String PREF_NAME = "AndroidHivePref";

    // Các key lưu dữ liệu
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ROLE = "role";

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Tạo session đăng nhập
     */
    public void createLoginSession(String email, String role) {
        // Lưu trạng thái đăng nhập là TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Lưu email và role
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ROLE, role);

        // Commit thay đổi
        editor.commit();
    }

    /**
     * Hàm này bạn đang thiếu -> Copy vào sẽ hết lỗi getKeyEmail
     */
    public String getKeyEmail() {
        return pref.getString(KEY_EMAIL, null);
    }

    public String getKeyRole() {
        return pref.getString(KEY_ROLE, null);
    }

    /**
     * Kiểm tra trạng thái login
     */
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    /**
     * Đăng xuất user
     */
    public void logoutUser() {
        // Xóa hết dữ liệu trong Shared Pref
        editor.clear();
        editor.commit();

        // (Tùy chọn) Chuyển hướng về màn hình đăng nhập sau khi logout
        // Intent i = new Intent(_context, LoginActivity.class);
        // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // _context.startActivity(i);
    }
}