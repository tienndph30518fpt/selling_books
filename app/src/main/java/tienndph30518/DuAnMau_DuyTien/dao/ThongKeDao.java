package tienndph30518.DuAnMau_DuyTien.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Dbheper.Dbheper;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;
import tienndph30518.DuAnMau_DuyTien.model.Sach;

public class ThongKeDao {
    Dbheper dbheper;

    public ThongKeDao(Context context) {
        dbheper = new Dbheper(context);
    }


    // thống kê tất cả tài khoản của admin
//    public ArrayList<Sach> getTop10() {
//        ArrayList<Sach> list = new ArrayList<>();
//        SQLiteDatabase db = dbheper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select pm.masach, sc.tensach , count(pm.masach) from PHIEUMUON pm, SACH sc where pm.masach = sc.masach group by pm.masach, sc.tensach order by count(pm.masach) desc limit 10", null);
//        if (cursor.getCount() != 0) {
//        }
//        cursor.moveToFirst();
//        do {
//            list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
//        } while (cursor.moveToNext());
//        db.close();
//        return list;
//    }



  // thống kê khi là tài khoản thử thư
//    public ArrayList<Sach> getTop1012(String matt) {
//        ArrayList<Sach> list = new ArrayList<>();
//        SQLiteDatabase db = dbheper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select pm.masach, sc.tensach , count(pm.masach) " +
//                "from PHIEUMUON pm, SACH sc ,THUTHU tt where pm.masach = sc.masach and pm.matt = tt.matt and tt.matt=? group by pm.masach, sc.tensach order by count(pm.masach) desc limit 10", new String[]{String.valueOf(matt)});
//        if (cursor.getCount() != 0) {
//        }
//        cursor.moveToFirst();
//        do {
//            list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
//        } while (cursor.moveToNext());
//        db.close();
//        return list;
//    }


    public ArrayList<Sach> getTop10() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select pm.masach, sc.tensach , count(pm.masach) from PHIEUMUON pm, SACH sc where pm.masach = sc.masach group by pm.masach, sc.tensach order by count(pm.masach) desc limit 10", null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                } while (cursor.moveToNext());
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }

        return list;
    }






    public ArrayList<Sach> getTop1012(String matt) {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select pm.masach, sc.tensach , count(pm.masach) " +
                    "from PHIEUMUON pm, SACH sc ,THUTHU tt where pm.masach = sc.masach and pm.matt = tt.matt and tt.matt=? group by pm.masach, sc.tensach order by count(pm.masach) desc limit 10", new String[]{String.valueOf(matt)});

            if (cursor.getCount() != 0) {
                cursor.moveToFirst();

                do {
                    list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
                } while (cursor.moveToNext());
            }
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            // Xử lý ngoại lệ tại đây (ví dụ: thông báo lỗi cho người dùng)

        } finally {
            if (cursor != null) {
                cursor.close();
            }

            db.close();
        }

        return list;
    }



    public int thongke(String ngaybatdau, String ngayketthuc){
        ngaybatdau =ngaybatdau.replace("/","");
         ngayketthuc =ngayketthuc.replace("/","");
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(tienthue) FROM PHIEUMUON WHERE substr(ngay,7)||substr(ngay,4,2)||substr(ngay,1,2) between ? and ?", new String[]{ngaybatdau, ngayketthuc});
        if (cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
    public int thongkeTT(String ngaybatdau, String ngayketthuc, String matt) {
        ngaybatdau = ngaybatdau.replace("/", "");
        ngayketthuc = ngayketthuc.replace("/", "");
        SQLiteDatabase db = dbheper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(tienthue) FROM PHIEUMUON pm, THUTHU tt WHERE substr(pm.ngay, 7, 4)||substr(pm.ngay, 4, 2)||substr(pm.ngay, 1, 2) BETWEEN ? AND ? AND pm.matt = ? AND tt.matt = ?",
                new String[]{ngaybatdau, ngayketthuc, matt, matt});
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        return 0;
    }
    public int XOADOANHTHU(Sach phieuMuon) {
        SQLiteDatabase db = dbheper.getReadableDatabase();
        String whereClause = "mapm = ?";
        String[] whereArgs = new String[]{String.valueOf(phieuMuon.getMasach())};

        return db.delete("PHIEUMUON", whereClause, whereArgs);
    }

}
