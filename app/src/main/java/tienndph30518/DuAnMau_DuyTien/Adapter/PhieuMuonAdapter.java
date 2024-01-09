package tienndph30518.DuAnMau_DuyTien.Adapter;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.midi.MidiDevice;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.CollationElementIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import tienndph30518.DuAnMau_DuyTien.DangNhap_Activity;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.PhieuMuonDao;
import tienndph30518.DuAnMau_DuyTien.dao.SachDao;
import tienndph30518.DuAnMau_DuyTien.dao.ThanhVienDao;
import tienndph30518.DuAnMau_DuyTien.fragment.QuanLyPhieuMuonFragment;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;
import tienndph30518.DuAnMau_DuyTien.model.Sach;
import tienndph30518.DuAnMau_DuyTien.model.ThanhVien;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.MyHover> {
    private ArrayList<PhieuMuon> arr = new ArrayList<>();// danh sach goc

    private Context context;
    private PhieuMuonDao phieuMuonDao;
    private CardView itemPhieuMuon;

String matt="";
    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.arr = list;
        this.context = context;

    }

    @NonNull
    @Override
    public MyHover onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ricycle, parent, false);
        final MyHover holder = new MyHover(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();


                if (position != RecyclerView.NO_POSITION) {
                    PhieuMuon phieuMuon = arr.get(position);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    View view1 = inflater.inflate(R.layout.dialog_phieumuon, null);
                    builder.setView(view1);
                    Spinner spnTen = view1.findViewById(R.id.spnTen);
                    Spinner spnSach = view1.findViewById(R.id.spnSachh);
                    EditText tienn = view1.findViewById(R.id.edtienthue);
                    EditText ngay = view1.findViewById(R.id.edNgay);
                    CheckBox checkBox = view1.findViewById(R.id.ckbSach);

                    ngay.setText(String.valueOf(arr.get(position).getNgay()));
                    tienn.setText(String.valueOf(arr.get(position).getTienthue()));

                    ngay.setFocusable(false);




                    getDatathanhvien(spnTen);
//
                    getDataSach(spnSach);


                    ngay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDatePicker(ngay);
                        }
                    });




                    if(arr.get(position).getTrasach()==1){
                        checkBox.setChecked(true);
                    }else {
                        checkBox.setChecked(false);
                    }

                    // Lấy dữ liệu từ cơ sở dữ liệu và nạp vào listHM
// Tạo adapter cho Spinner



                    builder.setPositiveButton("Sửa ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HashMap<String, Object> hsTV = (HashMap<String, Object>) spnTen.getSelectedItem();
                            int matv = (int) hsTV.get("matv");
                            HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                            int masach = (int) hsSach.get("masach");
                            if(checkBox.isChecked()){
                                arr.get(position).setTrasach(1);
                            }else {
                                arr.get(position).setTrasach(0);
                            }


//                            int tien = (int) hsSach.get("giathue");
                            String tien = tienn.getText().toString().trim();
                            int edtien = Integer.parseInt(tien);
                            String ngayy = ngay.getText().toString().trim();
                            PhieuMuon updatedPhieuMuon = new PhieuMuon();
                            updatedPhieuMuon.setMapm(phieuMuon.getMapm()); // Assuming you have a getter for the mapm field in MyHover class
                            updatedPhieuMuon.setMatv(matv);
                            updatedPhieuMuon.setMatt(phieuMuon.getMatt()); // Assuming you have a getter for the matt field in MyHover class
                            updatedPhieuMuon.setMasach(masach);
                            updatedPhieuMuon.setNgay(ngayy); // Assuming you have a getter for the ngay field in MyHover class
                            updatedPhieuMuon.setTrasach(phieuMuon.getTrasach()); // Assuming you have a getter for the trasach field in MyHover class
                            updatedPhieuMuon.setTienthue(edtien);

                            updatedPhieuMuon.setTrasach(arr.get(position).getTrasach());


                            phieuMuonDao = new PhieuMuonDao(context);
                            boolean success = phieuMuonDao.SuaPhieuMuon(updatedPhieuMuon);

                            if (success) {
                                arr.clear();
                                arr = phieuMuonDao.getDanhSachPM();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle the case when the update fails
                                Toast.makeText(context, "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }).setNegativeButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            phieuMuonDao = new PhieuMuonDao(context);
                            int  phieuMuon1 = arr.get(position).getMapm();
                            if (phieuMuonDao.delete(phieuMuon1)){

                                matt = DangNhap_Activity.matt;
                                PhieuMuonDao thuThuDao = new PhieuMuonDao(context);
                                boolean isAdmin = thuThuDao.isAdmin(matt);
                                if (isAdmin){
                                    arr.clear();
                                    arr = phieuMuonDao.getDanhSachPM();
                                    notifyDataSetChanged();
                                }else {
                                    arr.clear();
                                    arr = phieuMuonDao.getDanhSachTT(matt);
                                    notifyDataSetChanged();
                                }

                                Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Xoá Thất bại", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                }




            }




        });

        return holder;
    }





//    private void showDatePicker(final EditText editText) {
//    Calendar myCalendar = Calendar.getInstance();
//        int year = myCalendar.get(Calendar.YEAR);
//        int month = myCalendar.get(Calendar.MONTH);
//        int dayOfMonth = myCalendar.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
//                new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                        myCalendar.set(Calendar.YEAR, i);
//                        myCalendar.set(Calendar.MONTH, i1);
//                        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
//                        java.util.Date selectedDate = myCalendar.getTime();
//                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                        String time = (dateFormat.format(selectedDate));
//                        editText.setText(time);
//                    }
//                },
//                year, month, dayOfMonth);
//
//        datePickerDialog.show();
//    }
public void showDatePicker(EditText edNamsinh) {
    Calendar calendar = Calendar.getInstance();
    DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String thang = "";
            if (month < 9) {
                thang = "0" + (month + 1);
            } else {
                thang = String.valueOf(month + 1);
            }
            String ngay = "";
            if (dayOfMonth < 10) {
                ngay = "0" + (dayOfMonth);
            } else {
                ngay = String.valueOf(dayOfMonth);
            }
            edNamsinh.setText(ngay + "/" + thang + "/" + year);
        }
    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.HOUR_OF_DAY)
    );
    datePickerDialog.show();
}

    @Override
    public void onBindViewHolder(@NonNull MyHover holder, int position) {
        PhieuMuon pm = arr.get(position);
        holder.tvTrangsach.setText("Trang Sách: "+arr.get(position).getTrangsach());
        holder.tvMaPM.setText("Tiền Thuê: " + arr.get(position).getTienthue());
        holder.tvMaTv.setText("Mã TV: " + arr.get(position).getMatv());
        holder.tvTenTV.setText("Tên TV: " + arr.get(position).getTentv());
        holder.tvMaTT.setText("Mã TT: " + arr.get(position).getMatt());
        holder.tvTenTT.setText("Tên TT: " + arr.get(position).getTentt());
        holder.tvMaSach.setText("Mã Sách: " + arr.get(position).getMasach());
        holder.tvTensach.setText("Tên Sách: " + arr.get(position).getTensach());
        holder.tvNgay.setText("Ngày: " + arr.get(position).getNgay());
        if (arr.get(position).getTienthue()>=3000){
            itemPhieuMuon.setCardBackgroundColor(Color.GREEN);
        }else {

        }
        if (pm.getTrasach() == 1){
            holder.tvTrangThai.setText("Đã trả sách");
            holder.tvTrangThai.setTextColor(Color.parseColor("#23B52A"));
        }else {
            holder.tvTrangThai.setText("Chưa trả sách");
            holder.tvTrangThai.setTextColor(Color.parseColor("#FF5722"));
        }

//        String trangthai = "";
//        if (arr.get(position).getTrasach() == 1) {
//            trangthai = "Đã Trả Sách";
//            // ẩn đi buton nếu đã trả sách
//            holder.btnTraSach.setVisibility(View.GONE);
//        } else {
//            trangthai = "Chưa Trả Sách";
//            holder.btnTraSach.setVisibility(View.VISIBLE);
//        }
//        tvTrangThai.setText("Trạng Thái:" + trangthai);
//
//        holder.tvTien.setText("Mã PM:" + arr.get(position).getTienthue());
//

    }

    @Override
    public int getItemCount() {
        if (arr != null) {
            return arr.size();
        }
        return 0;
    }


    public class MyHover extends RecyclerView.ViewHolder {


        private TextView tvMaPM, tvMaTv, tvMaTT, tvTenTT, tvMaSach, tvTensach, tvNgay, tvTenTV, tvTien, tvX,tvTrangThai, tvTrangsach;
//        private Button btnTraSach ;
        private CheckBox btnTraSach;

        public MyHover(@NonNull View itemView) {
            super(itemView);

            tvMaPM = itemView.findViewById(R.id.txtMapm);
            tvMaTv = itemView.findViewById(R.id.txtMatv);
            tvMaSach = itemView.findViewById(R.id.txtMasach);
            tvMaTT = itemView.findViewById(R.id.txtMaTT);
            tvTenTT = itemView.findViewById(R.id.txtTenTT);
            tvTensach = itemView.findViewById(R.id.txtTenSach);
            tvNgay = itemView.findViewById(R.id.txtNgay);
            tvTrangThai = itemView.findViewById(R.id.txtTrangThai);
            tvTien = itemView.findViewById(R.id.txtTienthue);
            tvTenTV = itemView.findViewById(R.id.txtTenTV);
            tvTrangsach  =itemView.findViewById(R.id.txtTrangsach);
            itemPhieuMuon  =itemView.findViewById(R.id.itemPhieumuon);
//            tvX = itemView.findViewById(R.id.tvX);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Dialog dialog = new Dialog(context);
//                    dialog.setContentView(R.layout.dialog_all_phieumuon);
//                    dialog.show();
//                    TextView tvMapm = (TextView) dialog.findViewById(R.id.ivAllMapm);
//                    TextView tvMatv = (TextView) dialog.findViewById(R.id.ivAllMatv);
//                    TextView tvTentv = (TextView) dialog.findViewById(R.id.ivAllTentv);
//                    TextView tvTentt = (TextView) dialog.findViewById(R.id.ivAllTentt);
//                    TextView tvMasach = (TextView) dialog.findViewById(R.id.ivAllMaSach);
//                    TextView tvMatt = (TextView) dialog.findViewById(R.id.ivAllMatt);
//                    TextView tvTensach = (TextView) dialog.findViewById(R.id.ivAllTenSach);
//                    TextView tvNgay = (TextView) dialog.findViewById(R.id.ivAllNgay);
//                    TextView tvTrangthai = (TextView) dialog.findViewById(R.id.ivAllTrangthai);
//                    TextView tvgia = (TextView) dialog.findViewById(R.id.ivAllGia);
//                    if (arr.size() > getAdapterPosition()) {
//                        PhieuMuon phieuMuon = arr.get(getAdapterPosition());
//                        tvMapm.setText("Mã Phiếu Mượn: " + phieuMuon.getMapm());
//                        tvMatv.setText("Mã Thành Viên: " + phieuMuon.getMatv());
//                        tvTentv.setText("Tên Thành Viên: " + phieuMuon.getTentv());
//                        tvTentt.setText("Tên Thủ Thư: " + phieuMuon.getTentt());
//                        tvMasach.setText("Mã Sách: " + phieuMuon.getMasach());
//                        tvTensach.setText("Tên Sách: " + phieuMuon.getTensach());
//                        tvNgay.setText("Ngày: " + phieuMuon.getNgay());
//                        tvgia.setText("Giá Tien: " + phieuMuon.getTienthue());
//                        tvMatt.setText("Mã Thủ Thư: " + phieuMuon.getMatt());
//
//                        String trangthai = "";
//                        if (phieuMuon.getTrasach() == 1) {
//                            trangthai = "Đã Trả Sách";
//                            // ẩn đi buton nếu đã trả sách
//                            tvTrangthai.setTextColor(Color.GREEN);
//                        } else {
//                            trangthai = "Chưa Trả Sách";
//                            tvTrangthai.setTextColor(Color.RED);
//                        }
//                        tvTrangthai.setText("Trạng Thái:  " + trangthai);
//                    }
//                }
//            });
        }


    }

    public void getDatathanhvien(Spinner spnThanhVien) {
        ThanhVienDao thanhVienDao = new ThanhVienDao(context);
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getHoten());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);

    }


    public void getDataSach(Spinner spnSach) {
        SachDao sachDao = new SachDao(context);
        ArrayList<Sach> list = sachDao.getDanhSachDS();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sc : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sc.getMasach());
            hs.put("tensach", sc.getTensach());
            hs.put("giathue", sc.getGiathue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);

    }




}
