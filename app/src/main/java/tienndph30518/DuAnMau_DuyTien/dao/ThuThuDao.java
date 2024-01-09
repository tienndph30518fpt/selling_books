package tienndph30518.DuAnMau_DuyTien.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Dbheper.Dbheper;
import tienndph30518.DuAnMau_DuyTien.model.ThanhVien;
import tienndph30518.DuAnMau_DuyTien.model.ThuThu;


public class
ThuThuDao {
    Dbheper dbheper;

    public ThuThuDao(Context context) {
        dbheper = new Dbheper(context);
    }

    // đăng Nhập
    public boolean checkDangNhap(String matt, String matkhau) {
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from THUTHU where matt=? and matkhau=?", new String[]{matt, matkhau});
        if (cursor.getCount() != 0) {
            return true;

        } else {
            return false;
        }
    }

    @SuppressLint("SuspiciousIndentation")
    public int capNhatMatKhau(String user, String oldPass, String newPass) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from THUTHU where matt=? and matkhau=? ", new String[]{user, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues values = new ContentValues();
            values.put("matkhau", newPass);
            long check = db.update("THUTHU", values, "matt=?", new String[]{user});
            if (check == -1)
                return -1;

            return 1;

        }
        return 0;
    }


    // lấy toàn bộ danh sách thủ thư
    public ArrayList<ThuThu> getDS(){
        SQLiteDatabase db = dbheper.getReadableDatabase();
        ArrayList<ThuThu>list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from THUTHU",null);
        if (cursor.getCount() !=0){
            cursor.moveToFirst();
            do {
               list.add(new ThuThu(cursor.getString(0),cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int suaTenThuThu(ThuThu thuThu){
        SQLiteDatabase db =dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", thuThu.getHoten());

        values.put("matkhau", thuThu.getMatkhau());
        return db.update("THUTHU",values,"matt=?", new String[]{String.valueOf(thuThu.getMatt())});
    }
    public long xoaThuthu(String matt){

            SQLiteDatabase db = dbheper.getWritableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE matt=?", new String[]{String.valueOf(matt)});
            if (cursor.getCount() != 0) {
                return -1;
            }
            int rowsDeleted = db.delete("THUTHU", "thuthu=?", new String[]{String.valueOf(matt)});
            if (rowsDeleted == -1) {
                return 0;
            } else {
                return 1;
            }
        }


    public int deleteTableThuThu(ThuThu thu) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
      return   db.delete("THUTHU", "matt=?", new String[]{String.valueOf(thu.getMatt())});

    }

}



