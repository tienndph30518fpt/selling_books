package tienndph30518.DuAnMau_DuyTien.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Dbheper.Dbheper;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;

public class PhieuMuonDao {
    Dbheper dbheper;

    public PhieuMuonDao(Context context) {
        dbheper = new Dbheper(context);

    }

    public ArrayList<PhieuMuon> getDanhSachPM() {
        ArrayList<PhieuMuon> arr = new ArrayList<>();
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue , sc.trangsach " +
                "FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc " +
                "WHERE pm.matv = tv.matv and pm.matt = tt.matt AND pm.masach = sc.masach order by pm.mapm desc  ", null);

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                arr.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10)));
            } while (cursor.moveToNext());
        }

//      ORDER BY pm.ngay ASC  sắp xep ngày từ thấp đến cao
        return arr;
    }




    // xắp sếp ngày tháng theo ngày trả theo thứ tự tăng dần
//public ArrayList<PhieuMuon> getDanhSachPM() {
//    ArrayList<PhieuMuon> arr = new ArrayList<>();
//    SQLiteDatabase db = dbheper.getReadableDatabase();
//    Cursor cursor = db.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue, sc.trangsach " +
//            "FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc " +
//            "WHERE pm.matv = tv.matv AND pm.matt = tt.matt AND pm.masach = sc.masach AND pm.trasach = 1 " +
//            "ORDER BY pm.ngay ASC", null);
//
//
//    if (cursor.getCount() != 0) {
//        cursor.moveToFirst();
//        do {
//            arr.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10)));
//        } while (cursor.moveToNext());
//    }
//
////      ORDER BY pm.ngay ASC  sắp xep ngày từ thấp đến cao
//    return arr;
//}



    public ArrayList<PhieuMuon> getDanhSachTT(String matt) {
        ArrayList<PhieuMuon> arr = new ArrayList<>();
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue, sc.trangsach " +
                "FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc " +
                "WHERE pm.matv = tv.matv AND pm.matt = tt.matt AND pm.masac  = sc.masach AND tt.matt=? ", new String[]{matt});

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                arr.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9), cursor.getInt(10)));
            } while (cursor.moveToNext());
        }

//        Câu truy vấn chỉ những người chưa trả thì mới hiển thịđây
//        Cursor cursor = db.rawQuery("SELECT pm.mapm, pm.matv, tv.hoten, pm.matt, tt.hoten, pm.masach, sc.tensach, pm.ngay, pm.trasach, pm.tienthue " +
//                "FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc " +
//                "WHERE pm.matv = tv.matv AND pm.matt = tt.matt AND pm.masach = sc.masach AND tt.matt=? AND pm.trasach = 0", new String[]{matt});


        return arr;
    }



    public boolean thayDoiTrangThai(int mapm) {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("trasach", 1);
        long check = db.update("PHIEUMUON", values, "mapm=?", new String[]{String.valueOf(mapm)});
        if (check == -1)
            return false;
        return true;
    }

    public boolean themPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        values.put("mapm", phieuMuon.getMapm());
        values.put("matv", phieuMuon.getMatv());
        values.put("matt", phieuMuon.getMatt());
        values.put("masach", phieuMuon.getMasach());
        values.put("ngay", phieuMuon.getNgay());
        values.put("trasach", phieuMuon.getTrasach());
        values.put("tienthue", phieuMuon.getTienthue());

        long check = db.insert("PHIEUMUON", null, values);
        if (check == -1) {
            return false;

        }else {
            return true;
        }
    }



    public boolean SuaPhieuMuon(PhieuMuon phieuMuon) {
        SQLiteDatabase db = dbheper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matv", phieuMuon.getMatv());
        values.put("masach", phieuMuon.getMasach());
        values.put("tienthue", phieuMuon.getTienthue());
        values.put("trasach", phieuMuon.getTrasach());
        values.put("ngay",phieuMuon.getNgay());
        int check = db.update("PHIEUMUON", values, "mapm=?", new String[]{String.valueOf(phieuMuon.getMapm())});

        db.close();

        return check > 0;
    }

public  boolean delete(int id){
        SQLiteDatabase db = dbheper.getReadableDatabase();
        long check = db.delete("PHIEUMUON", "mapm=?", new String[]{String.valueOf(id)});
        if (check==-1){
            return false;
        }
        else {
            return true;
        }
}

    public boolean isAdmin(String matt) {
        // Thực hiện truy vấn để kiểm tra xem người dùng có mã thủ thư `matt` có là admin hay không
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND isAdmin = 1", new String[]{matt});
        boolean isAdmin = cursor.getCount() > 0;
        cursor.close();
        return isAdmin;
    }


}





