//package tienndph30518.DuAnMau_DuyTien.Dbheper;
//
//import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteException;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//public class Dbheper extends SQLiteOpenHelper {
//    final static String NAME = "QUANLYTHUVIEN";
//    final static int VESION = 1;
//
//    public Dbheper(@Nullable Context context) {
//        super(context, NAME, null, VESION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
////        String THUTHU = "create table THUTHU(matt text primary key, hoten text, matkhau text)";
////        db.execSQL(THUTHU);
//
//        String THUTHUu = "CREATE TABLE THUTHU (matt TEXT PRIMARY KEY, hoten TEXT, matkhau TEXT, isAdmin INTEGER DEFAULT 0)";
//        db.execSQL(THUTHUu);
//
//
//
//        String dbTHANHVIEN = "CREATE TABLE THANHVIEN(matv integer primary key autoincrement, hoten text, namsinh text)";
//        db.execSQL(dbTHANHVIEN);
//
//        String dbLOAISACH = "CREATE TABLE LOAISACH(maloai integer primary key autoincrement, tenloai text)";
//        db.execSQL(dbLOAISACH);
//
//        String dbSACH = "CREATE TABLE SACH(masach integer primary key autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
//        db.execSQL(dbSACH);
//
////        String dbPHIEUMUON = "create table PHIEUMUON(mapm integer primary key autoincrement, matv inetger references THANHVIEN(matv), " +
////                "matt text references THUTHU(matt), masach integer references SACH(masach),ngay text, trasach inetger, tienthue integer)";
//        String dbPHIEUMUON = "CREATE TABLE PHIEUMUON(" +
//                "mapm INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "matv INTEGER REFERENCES THANHVIEN(matv)," +
//                "matt TEXT REFERENCES THUTHU(matt)," +
//                "masach INTEGER REFERENCES SACH(masach)," +
//                "ngay TEXT," +
//                "trasach INTEGER," +
//                "tienthue INTEGER" +
//                ")";
//        db.execSQL(dbPHIEUMUON);
//
//        // db.execSQL(dbPHIEUMUON);
//        String alterQuery = "ALTER TABLE THUTHU ADD COLUMN isAdmin INTEGER DEFAULT 0";
//        try {
//            db.execSQL(alterQuery);
//        } catch (SQLiteException e) {
//            // Xử lý nếu cột đã tồn tại
//
//            db.execSQL("ALTER TABLE THUTHU ADD COLUMN isAdmin INTEGER DEFAULT 0");
//            db.execSQL("UPDATE THUTHU SET isAdmin = 1 WHERE matt = 'admin'");
//            db.execSQL("UPDATE THUTHU SET isAdmin = 0 WHERE matt != 'admin'");
//
//        }
//
//
//
//
//
//
////        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
////        db.execSQL("INSERT INTO SACH VALUES (1, 'Dế mèn phiêu lưu kí ', 2500, 1), (2, 'Ăn Mày Dĩ Vãng', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
////       // db.execSQL("INSERT INTO THUTHU VALUES ('duytien','Nguyễn Văn Anh','123'),('minh','Trần Văn Hùng','123'),('admin','Nguyễn Duy Tiến','123')");
////        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Nguyễn Duy Tiến','2003'),(2,'Trần Kim Mai','2000')");
////        //trả sách: 1: đã trả - 0: chưa trả
////        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'duytien', 1, '28/03/2023', 1, 2500),(2,1,'duytien', 3, '19/04/2023', 0, 2000),(3,2,'minh', 1, '19/05/2023', 1, 1000)");
////        db.execSQL("INSERT INTO THUTHU VALUES ('duytien','Nguyễn Văn Anh','123'),('minh','Trần Văn Hùng','123'),('admin','Nguyễn Duy Tiến','123')");
//
//
//        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
//        db.execSQL("INSERT INTO SACH VALUES (1, 'Dế mèn phiêu lưu kí', 2500, 1), (2, 'Ăn Mày Dĩ Vãng', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
//        db.execSQL("INSERT INTO THUTHU VALUES ('duytien','Nguyễn Văn Anh','123'),('minh','Trần Văn Hùng','123'),('admin','Nguyễn Duy Tiến','123')");
//        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Nguyễn Duy Tiến','2003'),(2,'Trần Kim Mai','2000')");
//        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'duytien', 1, '28/03/2023', 1, 2500),(2,1,'duytien', 3, '19/04/2023', 0, 2000),(3,2,'minh', 1, '19/05/2023', 1, 1000)");
//
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if(oldVersion != newVersion){
//            db.execSQL("drop table if exists LOAISACH");
//            db.execSQL("drop table if exists SACH");
//            db.execSQL("drop table if exists PHIEUMUON");
//            db.execSQL("drop table if exists THUTHU");
//            db.execSQL("drop table if exists THANHVIEN");
//            onCreate(db);
//        }
//    }
//
//
//
//}



package tienndph30518.DuAnMau_DuyTien.Dbheper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbheper extends SQLiteOpenHelper {
    final static String NAME = "QUANLYTHUVIEN";
    final static int VERSION = 1;

    public Dbheper(@Nullable Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableThuThu = "CREATE TABLE IF NOT EXISTS THUTHU (matt TEXT PRIMARY KEY, hoten TEXT, matkhau TEXT, taikhoan integer, isAdmin INTEGER DEFAULT 0)";
        db.execSQL(createTableThuThu);

        String createTableThanhVien = "CREATE TABLE IF NOT EXISTS THANHVIEN (matv INTEGER PRIMARY KEY AUTOINCREMENT, hoten TEXT , namsinh TEXT , nguoidung integer)";
        db.execSQL(createTableThanhVien);

        String createTableLoaiSach = "CREATE TABLE IF NOT EXISTS LOAISACH (maloai INTEGER PRIMARY KEY AUTOINCREMENT, tenloai TEXT, tacgia text)";
        db.execSQL(createTableLoaiSach);

        String createTableSach = "CREATE TABLE IF NOT EXISTS SACH (masach INTEGER PRIMARY KEY AUTOINCREMENT, tensach TEXT, giathue INTEGER, soluong integer, trangsach integer, maloai INTEGER REFERENCES LOAISACH(maloai))";
        db.execSQL(createTableSach);

        String createTablePhieuMuon = "CREATE TABLE IF NOT EXISTS PHIEUMUON (" +
                "mapm INTEGER PRIMARY KEY AUTOINCREMENT," +
                "matv INTEGER REFERENCES THANHVIEN(matv)," +
                "matt TEXT REFERENCES THUTHU(matt)," +
                "masach INTEGER REFERENCES SACH(masach)," +
                "ngay TEXT," +
                "trasach INTEGER," +
                "tienthue INTEGER "+
                ")";
        db.execSQL(createTablePhieuMuon);

        // Kiểm tra tồn tại cột isAdmin
        Cursor cursor = db.rawQuery("PRAGMA table_info(THUTHU)", null);
        boolean columnExists = false;
        if (cursor != null) {
            int columnNameIndex = cursor.getColumnIndex("name");
            while (cursor.moveToNext()) {
                String columnName = cursor.getString(columnNameIndex);
                if (columnName.equals("isAdmin")) {
                    columnExists = true;
                    break;
                }
            }
            cursor.close();
        }

        // Thêm cột isAdmin và cập nhật dữ liệu nếu cột chưa tồn tại
        if (!columnExists) {
            db.execSQL("ALTER TABLE THUTHU ADD COLUMN isAdmin INTEGER DEFAULT 0");
            db.execSQL("UPDATE THUTHU SET isAdmin = 1 WHERE matt = 'admin'");
            db.execSQL("UPDATE THUTHU SET isAdmin = 0 WHERE matt != 'admin'");
        }


        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Thiếu nhi','Phạm Anh Minh'),(2,'Tình cảm','Bố Của Bảnh'),(3, 'Giáo khoa','Nguyễn Văn Lên')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Dế mèn phiêu lưu kí', 2500,2, 25, 1), (2, 'Ăn Mày Dĩ Vãng', 1000,2, 20, 1), (3, 'Lập trình Android', 2000,1, 27, 3)");
        db.execSQL("INSERT INTO THUTHU VALUES ('duytien','Nguyễn Văn Anh','123',88888,0),('minh','Trần Văn Hùng','123',55555,0),('admin','Nguyễn Duy Tiến','123',100000,1)");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Nguyễn Duy Tiến','2003', 05555345),(2,'Trần Kim Mai','2000',7465444)");
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'duytien', 1, '28/04/2023', 1, 2500),(2,1,'duytien', 3, '19/03/2023', 0, 2000),(3,2,'minh', 1, '19/05/2023', 1, 1000)");
        // Thêm dữ liệu mẫu (nếu cần)
    //    insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
        }
    }


    public void updateRoleForUser(String username, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("role", role);

        db.update("THUTHU", values, "matt = ?", new String[]{username});
        db.close();
    }
}