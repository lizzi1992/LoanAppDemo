package com.example.loanappdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.loanappdemo.db.DatabaseHelper;
import com.example.loanappdemo.model.LoanApplication;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDao {
    private DatabaseHelper dbHelper;

    public ApplicationDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // 提交借款申请（高同学调用）
    public boolean addApplication(LoanApplication app) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("apply_id", app.getApplyId());
        values.put("user_id", app.getUserId());
        values.put("product_id", app.getProductId());
        values.put("apply_amount", app.getApplyAmount());
        values.put("apply_term", app.getApplyTerm());
        values.put("status", "待审核"); // 默认状态
        values.put("apply_time", app.getApplyTime());

        long rowId = db.insert(DatabaseHelper.TABLE_APPLY, null, values);
        db.close();
        return rowId != -1;
    }

    // 模拟审批状态更新
    public boolean updateApplicationStatus(String applyId, String newStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        int rows = db.update(DatabaseHelper.TABLE_APPLY, values, "apply_id = ?", new String[]{applyId});
        db.close();
        return rows > 0;
    }

    // 查询某人的所有申请（王梓萌调用）
    public List<LoanApplication> getApplicationsByUserId(String userId) {
        List<LoanApplication> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_APPLY, null, "user_id=?", new String[]{userId}, null, null, "apply_time DESC");

        if (cursor.moveToFirst()) {
            do {
                LoanApplication app = new LoanApplication();
                app.setApplyId(cursor.getString(cursor.getColumnIndexOrThrow("apply_id")));
                app.setUserId(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                app.setProductId(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
                app.setApplyAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("apply_amount")));
                app.setApplyTerm(cursor.getInt(cursor.getColumnIndexOrThrow("apply_term")));
                app.setStatus(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                app.setApplyTime(cursor.getString(cursor.getColumnIndexOrThrow("apply_time")));
                list.add(app);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}