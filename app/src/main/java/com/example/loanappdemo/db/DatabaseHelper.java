package com.example.loanappdemo.db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LoanRiskSystem.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USER = "user";
    public static final String TABLE_PRODUCT = "loan_product";
    public static final String TABLE_APPLY = "loan_apply";
    public static final String TABLE_CONTRACT = "contract";

    private static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " (" +
            "user_id TEXT PRIMARY KEY, " +
            "user_name TEXT, " +
            "phone TEXT UNIQUE, " +
            "id_card TEXT, " +
            "status INTEGER DEFAULT 0)";

    private static final String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + " (" +
            "product_id TEXT PRIMARY KEY, " +
            "product_name TEXT, " +
            "min_amount REAL, " +
            "max_amount REAL, " +
            "interest_rate REAL, " +
            "term INTEGER, " +
            "status INTEGER DEFAULT 1)";

    private static final String CREATE_TABLE_APPLY = "CREATE TABLE " + TABLE_APPLY + " (" +
            "apply_id TEXT PRIMARY KEY, " +
            "user_id TEXT, " +
            "product_id TEXT, " +
            "apply_amount REAL, " +
            "apply_term INTEGER, " +
            "status TEXT, " +
            "apply_time TEXT)";

    private static final String CREATE_TABLE_CONTRACT = "CREATE TABLE " + TABLE_CONTRACT + " (" +
            "contract_id TEXT PRIMARY KEY, " +
            "apply_id TEXT, " +
            "contract_amount REAL, " +
            "sign_time TEXT, " +
            "status INTEGER DEFAULT 0)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_APPLY);
        db.execSQL(CREATE_TABLE_CONTRACT);

        // 初始化一条测试产品数据，供测试用
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + " VALUES ('P001', '极速贷', 1000, 50000, 0.05, 12, 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Demo阶段暂不需要写更新逻辑
    }
}
