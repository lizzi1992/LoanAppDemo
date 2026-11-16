package com.example.loanappdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loanappdemo.db.DatabaseHelper;
import com.example.loanappdemo.model.Contract;

public class ContractDao {
    private DatabaseHelper dbHelper;

    public ContractDao(Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }
//审批通过后生成合同
    public boolean AddContract(Contract contract) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("contract_id",contract.getContractId());
        values.put("apply_id",contract.getApplyId());
        values.put("contract_amount",contract.getContractAmount());
        values.put("sign_time", contract.getSignTime());
        values.put("status", contract.getStatus());
        long rowId=db.insert(DatabaseHelper.TABLE_CONTRACT,null,values);
        db.close();
        return rowId!=-1;
    }
    //根据applyId查合同
    public Contract getContractByApplyId(String applyId){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query(DatabaseHelper.TABLE_CONTRACT,null,"apply_id=?",new String[]{applyId},null,null,null);
        Contract contract=null;
        if(cursor.moveToFirst()){
            contract=new Contract();
            contract.setContractId(cursor.getString(cursor.getColumnIndexOrThrow("contract_id")));
            contract.setApplyId(cursor.getString(cursor.getColumnIndexOrThrow("apply_id")));
            contract.setContractAmount(cursor.getDouble(cursor.getColumnIndexOrThrow("contract_amount")));
            contract.setSignTime(cursor.getString(cursor.getColumnIndexOrThrow("sign_time")));
            contract.setStatus(cursor.getInt(cursor.getColumnIndexOrThrow("status")));
        }
        cursor.close();
        db.close();
        return contract;
    }

    //(改）更新状态确认签约
    public boolean signContract(String contractId,String signTime){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1); // 1已签
        values.put("sign_time", signTime);

        // 条件：合同ID匹配，且当前状态必须是 0（未签）
        int rows = db.update(DatabaseHelper.TABLE_CONTRACT, values, "contract_id = ? AND status = 0", new String[]{contractId});
        db.close();
        return rows > 0;
    }
}
