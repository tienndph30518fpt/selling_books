package tienndph30518.DuAnMau_DuyTien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Dbheper.Dbheper;
import tienndph30518.DuAnMau_DuyTien.model.ThanhVien;

public class ThanhVienDao {
    Dbheper dbheper;

    public ThanhVienDao(Context context) {
        dbheper = new Dbheper(context);

    }

    public ArrayList<ThanhVien> getDSThanhVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themThanhVien(String hoten, String namsinh) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("namsinh", namsinh);
        long check = db.insert("THANHVIEN", null, values);
        if (check == -1)
            return false;
        return true;
    }

    public boolean capNhatThanhhVien(int id, String hoten, String namsinh) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("namsinh", namsinh);
        long check = db.update("THANHVIEN", values, "matv=?", new String[]{String.valueOf(id)});
        if (check == -1)
            return false;
        return true;
    }
//    public  int xoaThanhVien(int matv){
//        SQLiteDatabase db =dbheper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from THANHVIEN where matv=?", new String[]{String.valueOf(matv)});
//        if (cursor.getCount()!=0){
//            return -1;
//        }
//        long check= db.delete("THANHVIEN","matv=?",new String[]{String.valueOf(matv)});
//        if (check==-1)
//            return 0;
//        return 1;
//
//    }

    public int xoaThanhVien(int matv) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from PHIEUMUON where matv=?", new String[]{String.valueOf(matv)});
        if (cursor.getCount() != 0) {
            return -1;
        } else {
            long check = db.delete("THANHVIEN", "matv=?", new String[]{String.valueOf(matv)});
            if (check == -1)
                return 0;
            return 1;
        }
    }

    public boolean isDuplicateID(int id) {
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THANHVIEN WHERE matv = ?", new String[]{String.valueOf(id)});
        boolean isDuplicate = cursor.getCount() > 0;
        cursor.close();
        return isDuplicate;
    }


}
