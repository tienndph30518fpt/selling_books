package tienndph30518.DuAnMau_DuyTien.fragment;

import static android.content.Intent.getIntent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Pattern;

import tienndph30518.DuAnMau_DuyTien.Adapter.PhieuMuonAdapter;
import tienndph30518.DuAnMau_DuyTien.DangNhap_Activity;
import tienndph30518.DuAnMau_DuyTien.MainActivity;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.LoaiSachDao;
import tienndph30518.DuAnMau_DuyTien.dao.PhieuMuonDao;
import tienndph30518.DuAnMau_DuyTien.dao.SachDao;
import tienndph30518.DuAnMau_DuyTien.dao.ThanhVienDao;
import tienndph30518.DuAnMau_DuyTien.dao.ThuThuDao;
import tienndph30518.DuAnMau_DuyTien.model.LoaiSACH;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;
import tienndph30518.DuAnMau_DuyTien.model.Sach;
import tienndph30518.DuAnMau_DuyTien.model.ThanhVien;

public class QuanLyPhieuMuonFragment extends Fragment {
    private PhieuMuonDao phieuMuonDao;
    private PhieuMuonAdapter Adapter;
    private RecyclerView recyclerView;
    String matt = "";

private ArrayList<LoaiSACH> arrLoaiSach = new ArrayList<>();
private ArrayList<ThanhVien> arrThanhVien = new ArrayList<>();
    // Tạo một biến để lưu trữ danh sách gốc
    ArrayList<PhieuMuon> originalList;

    // Tạo một biến để lưu trữ danh sách sau khi tìm kiếm
    ArrayList<PhieuMuon> filteredList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlphieumuon, container, false);

        recyclerView = view.findViewById(R.id.idRecyclerQlPhieuMuon);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatActionButon);
        //giao diện
        loatData();
        // dùng để vẽ các đường gạch chân  dưới mỗi item
        RecyclerView.ItemDecoration itemDecoration  = new DividerItemDecoration(getContext()    ,DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(itemDecoration);




        // nút showbutton
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }




// dia để thêm phiếu mượn
    public void showDialog() {
        LoaiSachDao sachDAO  =new LoaiSachDao(getContext());
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        arrThanhVien = thanhVienDao.getDSThanhVien();
        arrLoaiSach = sachDAO.getLoaiSach();
        if(arrLoaiSach.size() == 0 || arrThanhVien.size()==0){
            Toast.makeText(getContext(), "Bạn chưa thêm 'Loại sách' ", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_them_phieumuon, null);
        Spinner spnThanhVien = view.findViewById(R.id.spnthanhvien);
        Spinner spnSach = view.findViewById(R.id.spnsach);
        // EditText edtTien = view.findViewById(R.id.edTien);
        getDatathanhvien(spnThanhVien);
        getDataSach(spnSach);
        builder.setView(view);
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        // sử lý việc thiêm phiếu mượn
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // lấy danh sách thành viên
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("matv");
                // lấy mã sách
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("masach");

                int tien = (int) hsSach.get("giathue");
                themPhieuMuon(matv, masach, tien);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
// lấy dữ liệu tên thành viên để đưa lên spinner
    public void getDatathanhvien(Spinner spnThanhVien) {
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getHoten());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnThanhVien.setAdapter(simpleAdapter);

    }



// lấy dữ liệu để đưa lên spiner sách
    public void getDataSach(Spinner spnSach) {
        SachDao sachDao = new SachDao(getContext());
        ArrayList<Sach> list = sachDao.getDanhSachDS();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sc : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sc.getMasach());
            hs.put("tensach", sc.getTensach());
            hs.put("giathue", sc.getGiathue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);

    }

// hàm thêm phiếu mượn
    public void themPhieuMuon(int matv, int masach, int tien) {
        // lấy mã thu thư
        SharedPreferences preferences = getContext().getSharedPreferences("THONGTIN", Context.MODE_PRIVATE);
        String matt = preferences.getString("matt", "");
        // lấy ngày hiện tại
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());giờ
        String ngay = simpleDateFormat.format(date);
        PhieuMuon phieuMuon = new PhieuMuon(matv, matt, masach, ngay, 0, tien);
        boolean kiemtra = phieuMuonDao.themPhieuMuon(phieuMuon);
        if (kiemtra) {
            Toast.makeText(getContext(), "Thêm Phiếu Mượn Thành công", Toast.LENGTH_SHORT).show();
            loatData();

        } else {
            Toast.makeText(getContext(), "Thêm Phiếu Mượn Thất bại", Toast.LENGTH_SHORT).show();
        }

    }

//    public void loatData(){
//        phieuMuonDao = new PhieuMuonDao(getContext());
//        ArrayList<PhieuMuon> list = phieuMuonDao.getDanhSachPM();
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(manager);
//        Adapter = new PhieuMuonAdapter(list, getContext());
//        recyclerView.setAdapter(Adapter);
//    }


//    public void loatData() {
//        matt = DangNhap_Activity.matt;
//        phieuMuonDao = new PhieuMuonDao(getContext());
//        ArrayList<PhieuMuon> list = phieuMuonDao.getDanhSachTT(matt);
//        LinearLayoutManager manager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(manager);
//        Adapter = new PhieuMuonAdapter(list, getContext());
//        recyclerView.setAdapter(Adapter);

// load Dữ liệu lên recycView với hai trường hợp là thủ thư và admin
    public void loatData() {
        matt = DangNhap_Activity.matt;
        PhieuMuonDao thuThuDao = new PhieuMuonDao(getContext());
        boolean isAdmin = thuThuDao.isAdmin(matt);

        if (isAdmin) {
            // Lấy danh sách tất cả phiếu mượn
            phieuMuonDao = new PhieuMuonDao(getContext());
            ArrayList<PhieuMuon> list = phieuMuonDao.getDanhSachPM();
            // Tiếp tục xử lý dữ liệu như ban đầu
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);
            Adapter = new PhieuMuonAdapter(list, getContext());
            recyclerView.setAdapter(Adapter);
        } else {
            phieuMuonDao = new PhieuMuonDao(getContext());
            // Người dùng không phải là admin, chỉ lấy danh sách theo người đăng nhập

            ArrayList<PhieuMuon> list = phieuMuonDao.getDanhSachTT(matt);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);
            Adapter = new PhieuMuonAdapter(list, getContext());
            recyclerView.setAdapter(Adapter);
        }
    }

    //nút tìm kiếm
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.timkiem, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_seach);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconified(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);



// Trước khi thực hiện tìm kiếm, lưu trữ danh sách gốc
        originalList = phieuMuonDao.getDanhSachPM();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Không cần thực hiện gì trong phương thức này
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Tạo danh sách mới để lưu trữ kết quả tìm kiếm

                filteredList = new ArrayList<>();

                // Lặp qua danh sách gốc và thêm các phần tử phù hợp vào danh sách tìm kiếm
                for (PhieuMuon phieuMuon : originalList) {
                    String maSach = String.valueOf(phieuMuon.getMatt());
                    String normalizedMaSach = normalizeString(maSach);
                    if (normalizedMaSach.toLowerCase().contains(newText.toLowerCase())) {
                        filteredList.add(phieuMuon);
                    }
                }

                // Tạo adapter mới với danh sách tìm kiếm
                PhieuMuonAdapter phieuMuonAdapter = new PhieuMuonAdapter(filteredList, getContext());
                // Gán adapter mới cho RecyclerView hoặc ListView của bạn
                recyclerView.setAdapter(phieuMuonAdapter);

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }
    private String normalizeString(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("").toLowerCase();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }




}







