package com.example.proj_ecom_mobile.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.proj_ecom_mobile.model.CartItem;
import com.example.proj_ecom_mobile.model.Product;
import java.util.ArrayList;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "EcomStore.db";
    private static final int DB_VERSION = 2;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createProductTable = "CREATE TABLE products (id TEXT PRIMARY KEY, name TEXT, price REAL, image TEXT, description TEXT, category TEXT)";
        db.execSQL(createProductTable);

        String createCartTable = "CREATE TABLE cart (productId TEXT, productName TEXT, productPrice REAL, productImage TEXT, quantity INTEGER, size TEXT)";
        db.execSQL(createCartTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS products");
        db.execSQL("DROP TABLE IF EXISTS cart");
        onCreate(db);
    }

    public void queryData(String sql) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
    }

    public void syncProducts(ArrayList<Product> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL("DELETE FROM products");
            for (Product p : list) {
                ContentValues values = new ContentValues();
                values.put("id", p.getId());
                values.put("name", p.getName());
                values.put("price", p.getPrice());
                values.put("image", p.getImageUrl());
                values.put("description", p.getDescription());
                values.put("category", p.getCategory());
                db.insert("products", null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public ArrayList<Product> getOfflineProducts() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM products", null);
        if (c.moveToFirst()) {
            do {
                Product p = new Product();
                p.setId(c.getString(0));
                p.setName(c.getString(1));
                p.setPrice(c.getDouble(2));
                p.setImageUrl(c.getString(3));
                p.setDescription(c.getString(4));
                p.setCategory(c.getString(5));
                list.add(p);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public void addToCart(CartItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM cart WHERE productId = ? AND size = ?", new String[]{item.getProductId(), item.getSize()});

        if (cursor.moveToFirst()) {
            int currentQty = cursor.getInt(4);
            int newQty = currentQty + item.getQuantity();
            ContentValues values = new ContentValues();
            values.put("quantity", newQty);
            db.update("cart", values, "productId = ? AND size = ?", new String[]{item.getProductId(), item.getSize()});
        } else {
            ContentValues values = new ContentValues();
            values.put("productId", item.getProductId());
            values.put("productName", item.getProductName());
            values.put("productPrice", item.getProductPrice());
            values.put("productImage", item.getProductImage());
            values.put("quantity", item.getQuantity());
            values.put("size", item.getSize());
            db.insert("cart", null, values);
        }
        cursor.close();
        db.close();
    }

    public ArrayList<CartItem> getCartItems() {
        ArrayList<CartItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cart", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new CartItem(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM cart");
        db.close();
    }

    public void deleteCartItem(String productId, String size) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "productId = ? AND size = ?", new String[]{productId, size});
        db.close();
    }

    public void updateQuantity(String productId, String size, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);
        db.update("cart", values, "productId = ? AND size = ?", new String[]{productId, size});
        db.close();
    }
}