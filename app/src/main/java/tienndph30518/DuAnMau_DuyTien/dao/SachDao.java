package tienndph30518.DuAnMau_DuyTien.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Dbheper.Dbheper;
import tienndph30518.DuAnMau_DuyTien.model.Sach;

public class SachDao {
    Dbheper dbheper;

    public SachDao(Context context) {
        dbheper = new Dbheper(context);


    }

    //lấy Toàn bộ đầu sách có trong thu viện
//    public ArrayList<Sach> getDanhSachDS() {
//        ArrayList<Sach> arr = new ArrayList<>();
//        SQLiteDatabase db = dbheper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select  sc.masach, sc.tensach, sc.giathue, sc.maloai,lo.tenloai, sc.soluong  from SACH sc , LOAISACH lo where sc.maloai = lo.maloai", null);
//        if (cursor.getCount() != 0) {
//            cursor.moveToFirst();
//            do {
//                arr.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5)));
//            } while (cursor.moveToNext());
//        }
//        return arr;
//    }


    //     (SELECT COUNT(*) FROM SACH WHERE maloai = sc.maloai) dùng để đếm số lượng maloai và gán số lượng đó cho AS soluong

    public ArrayList<Sach> getDanhSachDS() {
        ArrayList<Sach> arr = new ArrayList<>();
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sc.masach, sc.tensach, sc.giathue, sc.maloai, lo.tenloai, (SELECT COUNT(*) FROM SACH WHERE maloai = sc.maloai) AS soluong ,sc.trangsach FROM SACH sc, LOAISACH lo WHERE sc.maloai = lo.maloai", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                arr.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5),cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arr;
    }

//    public ArrayList<Sach> getDanhSachDS() {
//        ArrayList<Sach> arr = new ArrayList<>();
//        SQLiteDatabase db = dbheper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT sc.masach, sc.tensach, sc.giathue, sc.maloai, lo.tenloai,sc.soluong ,sc.trangsach FROM SACH sc, LOAISACH lo WHERE sc.maloai = lo.maloai", null);
//        if (cursor.getCount() != 0) {
//            cursor.moveToFirst();
//            do {
//                arr.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5),cursor.getInt(6)));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return arr;
//    }

    public boolean theSach(String tensach, int giatien, int maloai, int Trangsach) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", tensach);
        values.put("maloai", maloai);
        values.put("giathue", giatien);
        values.put("trangsach",Trangsach );
        long check = db.insert("SACH", null, values);
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }
public int suaTrangSach(Sach sach){
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trangsach" , sach.getTraangsach());
        return db.update("SACH",values,"masach=?", new String[]{String.valueOf(sach.getMasach())});
}
    public int suaSoluong(Sach sach){
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("soluong" , sach.getSoLuong());
        return db.update("SACH",values,"masach=?", new String[]{String.valueOf(sach.getMasach())});
    }
    public boolean SuaSach(int masach, String tensach, int giatien, int maloai, int trangsach) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", tensach);
        values.put("maloai", maloai);
        values.put("giathue", giatien);
        values.put("trangsach", trangsach);
        long check = db.update("SACH", values, "masach=?", new String[]{String.valueOf(masach)});
        if (check == -1) {
            return false;
        } else {
            return true;
        }
    }

//    public int xoaSach(int masach) {
//        SQLiteDatabase db = dbheper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM PHIEUMUON WHERE masach=?", new String[]{String.valueOf(masach)});
//        if (cursor.getCount() != 0) {
//            return -1;
//        }
////         long check = db.delete("PHIEUMUON","masach", new String[]{String.valueOf(masach)});
//        long check = db.delete("PHIEUMUON", "masach=?", new String[]{String.valueOf(masach)});
//
//        if (check == -1)
//            return 0;
//        return 1;
//    }

    public int xoaSach(int masach) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PHIEUMUON WHERE masach=?", new String[]{String.valueOf(masach)});
        if (cursor.getCount() != 0) {
            return -1;
        }
        int rowsDeleted = db.delete("SACH", "masach=?", new String[]{String.valueOf(masach)});
        if (rowsDeleted == -1) {
            return 0;
        } else {
            return 1;
        }
    }

}


