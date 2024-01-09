package tienndph30518.DuAnMau_DuyTien.fragment;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import androidx.appcompat.widget.SearchView;


import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import tienndph30518.DuAnMau_DuyTien.Adapter.ThanhVienAdapter;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.ThanhVienDao;
import tienndph30518.DuAnMau_DuyTien.model.ThanhVien;


public class QuanLyThanhVien_Fragment extends Fragment {

private ThanhVienDao vienDao;
private  RecyclerView recyclerView;


private ArrayList<ThanhVien> arrayListFill;
private ArrayList<ThanhVien> oriListFill;
    public QuanLyThanhVien_Fragment() {
        // Required empty public constructor
    }


    public static QuanLyThanhVien_Fragment newInstance() {
        QuanLyThanhVien_Fragment fragment = new QuanLyThanhVien_Fragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quan_ly_thanh_vien_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FloatingActionButton actionButton = view.findViewById(R.id.flThanhVien);
         recyclerView = view.findViewById(R.id.recyclerViewQLThanhVien);

        loatData();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
    }
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themthanhvien,null);
        builder.setView(view);
        EditText edTen = view.findViewById(R.id.edtHoten);
        EditText edtNamSinh = view.findViewById(R.id.edtNamsinh);
        edtNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogNamSinh(edtNamSinh);
            }
        });
        edtNamSinh.setFocusable(false);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ten = edTen.getText().toString().trim();
                String namsinh  = edtNamSinh.getText().toString().trim();

                if (ten.isEmpty() || namsinh.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                vienDao = new ThanhVienDao(getContext());
                ThanhVien thanhVien = new ThanhVien();
                int id =thanhVien.getMatv();
                if (vienDao.isDuplicateID(id)){
                    Toast.makeText(getContext(), "Nhập Trùng mã Thành Viên", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (vienDao.themThanhVien(ten,namsinh)){
                    Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    loatData();
                }else {
                    Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void ShowDialogNamSinh(EditText edtNamSinh){
        Calendar calendar   =Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String thang="";
            if (month<10){
                thang = "0"+month;
            }else {
              thang = String.valueOf(month+1);
            }
            String ngay= "";
            if (dayOfMonth<10){
                ngay = "0"+dayOfMonth;
            }else {
                ngay= String.valueOf(dayOfMonth+1);
            }
            edtNamSinh.setText(ngay+"/"+thang+"/"+year);
            }
        },calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();

    }

    public void loatData(){
        vienDao = new ThanhVienDao(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<ThanhVien> list = vienDao.getDSThanhVien();
        ThanhVienAdapter adapter = new ThanhVienAdapter(list, getContext(),vienDao);
        recyclerView.setAdapter(adapter);
    }




    // tìm kiếm theo tên thành viên

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.timkiem, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_seach);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // lấy dữ lệu gốc
        arrayListFill = vienDao.getDSThanhVien();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Lưu Chữ Tạm Thời ở đây
                oriListFill  =new ArrayList<>();
                for (ThanhVien thanhVien: arrayListFill){
                    String thanhVienn = String.valueOf(thanhVien.getHoten());
                    String xoaKyTu = xoakytu(thanhVienn);
                    if (xoaKyTu.toLowerCase().contains(newText.toLowerCase())||
                            xoaKyTu.toLowerCase().replace("đ","d").contains(newText.toLowerCase())){
                         {

                            oriListFill.add(thanhVien);
                        }
                    }
                }

                ThanhVienAdapter adapter = new ThanhVienAdapter(oriListFill, getContext());
                recyclerView.setAdapter(adapter);


                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }


     private String  xoakytu(String input){

        //ó ý nghĩa là chuẩn hóa chuỗi input và loại bỏ các dấu diacritic trong chuỗi đó.
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
         Pattern pattern  =Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
         return pattern.matcher(normalizedString).replaceAll("").toLowerCase();
     }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

}