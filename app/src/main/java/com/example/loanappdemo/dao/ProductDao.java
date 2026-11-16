package com.example.loanappdemo.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.loanappdemo.db.DatabaseHelper;
import com.example.loanappdemo.model.LoanProduct;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private DatabaseHelper dbHelper;

    public ProductDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // 获取所有上架产品（给高同学列表页用）
    public List<LoanProduct> getAvailableProducts() {
        List<LoanProduct> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // 查询 status=1（1代表上架）的产品
        Cursor cursor = db.query(DatabaseHelper.TABLE_PRODUCT, null, "status=?", new String[]{"1"}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                LoanProduct p = new LoanProduct();
                p.setProductId(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
                p.setProductName(cursor.getString(cursor.getColumnIndexOrThrow("product_name")));
                p.setMinAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("min_amount")));
                p.setMaxAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("max_amount")));
                p.setInterestRate(cursor.getDouble(cursor.getColumnIndexOrThrow("interest_rate")));
                p.setTerm(cursor.getInt(cursor.getColumnIndexOrThrow("term")));
                p.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")));
                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
