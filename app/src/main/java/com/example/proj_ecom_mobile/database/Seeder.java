package com.example.proj_ecom_mobile.database;

import com.example.proj_ecom_mobile.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;

public class Seeder {

    public static void seedDataToFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String[] images = {
                "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
                "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg",
                "https://fakestoreapi.com/img/51Y5NI-I5jL._AC_UX679_.jpg"
        };

        String[] names = {"Balo Du Lich", "Ao Thun Nam", "Ao Khoac Cotton", "Ao Len Mua Dong", "Ao Nu Form Rong"};

        for (int i = 0; i < 10; i++) {
            String id = db.collection("products").document().getId();
            Product p = new Product(
                    id,
                    names[i % names.length] + " #" + (i + 1),
                    150000 + (i * 20000),
                    "Mo ta san pham mau so " + (i + 1),
                    images[i % images.length],
                    "ThoiTrang"
            );
            db.collection("products").document(id).set(p);
        }
    }
}