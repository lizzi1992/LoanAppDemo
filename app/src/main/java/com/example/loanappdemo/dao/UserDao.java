package com.example.loanappdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.loanappdemo.db.DatabaseHelper;
import com.example.loanappdemo.model.User;

public class UserDao {
    private DatabaseHelper dbHelper;

    public UserDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }

    // 注册新用户
    public boolean addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", user.getUserId());
        values.put("user_name", user.getUserName());
        values.put("phone", user.getPhone());
        values.put("id_card", user.getIdCard());
        values.put("status", user.getStatus());

        long rowId = db.insert(DatabaseHelper.TABLE_USER, null, values);
        db.close();
        return rowId != -1;
    }

    // 根据手机号查询用户（登录用）
    public User getUserByPhone(String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, null, "phone=?", new String[]{phone}, null, null, null);
        User user = null;
        if (cursor.moveToFirst()) {
            user = new User();
            user.setUserId(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
            user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("user_name")));
            user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            user.setIdCard(cursor.getString(cursor.getColumnIndexOrThrow("id_card")));
            user.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")));
        }
        cursor.close();
        db.close();
        return user;
    }
}