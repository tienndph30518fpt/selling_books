package tienndph30518.DuAnMau_DuyTien.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import java.util.ArrayList;
import java.util.Calendar;

import tienndph30518.DuAnMau_DuyTien.Adapter.Top10_Adapter;
import tienndph30518.DuAnMau_DuyTien.DangNhap_Activity;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.PhieuMuonDao;
import tienndph30518.DuAnMau_DuyTien.dao.ThongKeDao;
import tienndph30518.DuAnMau_DuyTien.model.Sach;


public class ThongKeDoanhThu_Fragment extends Fragment {
private ThongKeDao thongKeDao;
private     EditText  edNgayKetThuc;
String matt ="";
    public ThongKeDoanhThu_Fragment() {
        // Required empty public constructor
    }


    public static ThongKeDoanhThu_Fragment newInstance(String param1, String param2) {
        ThongKeDoanhThu_Fragment fragment = new ThongKeDoanhThu_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thong_ke_doanh_thu_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText edNgayBatdau  = view.findViewById(R.id.edNgayBatDau);
        edNgayBatdau.setFocusable(false);
         edNgayKetThuc = view.findViewById(R.id.edNgayKetThuc);
        edNgayKetThuc.setFocusable(false);
        Button btnThongKe= view.findViewById(R.id.btnThongKe);
        TextView tvKetqua = view.findViewById(R.id.tvKetQua);
        Calendar calendar = Calendar.getInstance();
        // dataPicker chọn ngày bắt đầu thống kê
         edNgayBatdau.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerDialog  datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         String ngay ="";
                        String thang ="";
                        if (dayOfMonth<10){
                            ngay ="0"+dayOfMonth;
                        }else {
                            ngay = String.valueOf(dayOfMonth);
                        }

                        if (month+1<10){
                            thang = "0"+(month+1);
                        }else {
                            thang = String.valueOf(month+1);
                        }
                       edNgayBatdau.setText(year+"/"+thang+"/"+ngay);
                     }
                 }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.HOUR_OF_DAY));
                 datePickerDialog.show();
             }
         });

         // datapicker để người dùng chọn ngày tháng năm ngày kết thúc thống kê
         edNgayKetThuc.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 DatePickerDialog  datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                         String ngay ="";
                         String thang ="";
                         if (dayOfMonth<10){
                             ngay ="0"+dayOfMonth;
                         }else {
                             ngay = String.valueOf(dayOfMonth);
                         }

                         if (month+1<10){
                             thang = "0"+(month+1);
                         }else {
                             thang = String.valueOf(month+1);
                         }
                         edNgayKetThuc.setText(year+"/"+thang+"/"+ngay);
                     }
                 }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.HOUR_OF_DAY));
                 datePickerDialog.show();
             }
         });

         // btn kiểm tra doanh thu
         btnThongKe.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String ngayketthuc = edNgayKetThuc.getText().toString().trim();
                 String ngaybatdau = edNgayBatdau.getText().toString().trim();
                 if (ngaybatdau.isEmpty() || ngaybatdau.isEmpty()) {
                     Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 matt = DangNhap_Activity.matt;
                 PhieuMuonDao thuThuDao = new PhieuMuonDao(getContext());
                 boolean isAdmin = thuThuDao.isAdmin(matt);

                 if (isAdmin){
                     thongKeDao = new ThongKeDao(getContext());
                     int check = thongKeDao.thongke(ngaybatdau,ngayketthuc);
                     tvKetqua.setText(check+": VND");
                 }else {
                     thongKeDao = new ThongKeDao(getContext());
                     int check = thongKeDao.thongkeTT(ngaybatdau,ngayketthuc, matt);
                     tvKetqua.setText(check+": VND");
                 }

             }
         });

    }


}