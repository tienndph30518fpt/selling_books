    package tienndph30518.DuAnMau_DuyTien.dao;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.sqlite.SQLiteDatabase;


    import java.util.ArrayList;

    import tienndph30518.DuAnMau_DuyTien.Dbheper.Dbheper;
    import tienndph30518.DuAnMau_DuyTien.model.LoaiSACH;

    public class LoaiSachDao {
        Dbheper dbheper;

        public LoaiSachDao(Context context) {
            dbheper = new Dbheper(context);
        }

        // dùng để lấy loại sách để chuyền lên spiner
        public ArrayList<LoaiSACH> getLoaiSach() {
            ArrayList<LoaiSACH> list = new ArrayList<>();
            SQLiteDatabase db = dbheper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select * from LOAISACH", null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                do {
                    list.add(new LoaiSACH(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                } while (cursor.moveToNext());
            }
            db.close();
            return list;
        }
// thêm thể loại sách mớin
        public boolean themLoaiSach(String tenloai, String tacgia) {
            SQLiteDatabase db = dbheper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("tenloai", tenloai);
            values.put("tacgia",tacgia);
            long check = db.insert("LOAISACH", null, values);
            if (check == -1)
                return false;
            return true;

        }

        // xoá loại sách  ,1 Xoá Thành Công , 0, Xoá Thất Bại, -1 có Một danh Sách Tồn Tại
    //    public int xoaSach(int id) {
    //        SQLiteDatabase db = dbheper.getWritableDatabase();
    //        Cursor cursor = db.rawQuery("select * from SACH where masach=?", new String[]{String.valueOf(id)});
    //        if (cursor.getCount() != 0) {
    //            return -1;
    //        } else {
    //            long check = db.delete("LOAISACH", "maloai=?", new String[]{String.valueOf(id)});
    //            if (check == -1)
    //                return 0;
    //            return 1;
    //        }
    //    }

// xoá sách nếu thể loại sách đó được mượn thì không được xoá nếu chưa được mượn thì có thể xoá
        public int xoaSach(int id) {
            SQLiteDatabase db = dbheper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from SACH where maloai=?", new String[]{String.valueOf(id)});
            if (cursor.getCount() != 0) {
                return -1;
            } else {
                long check = db.delete("LOAISACH", "maloai=?", new String[]{String.valueOf(id)});
                if (check == -1)
                    return 0;
                return 1;
            }
        }
// sửa tên các loại sách
        public boolean suaSach(LoaiSACH loaiSACH) {
            SQLiteDatabase db = dbheper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("tenloai", loaiSACH.getTenloai());
            values.put("tacgia", loaiSACH.getTacgia());
            long check = db.update("LOAISACH", values, "maloai=?", new String[]{String.valueOf(loaiSACH.getId())});
            if (check == -1)
                return false;
            return true;

        }


    }
