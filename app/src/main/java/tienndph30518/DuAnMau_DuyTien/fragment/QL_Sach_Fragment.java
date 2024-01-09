package tienndph30518.DuAnMau_DuyTien.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import tienndph30518.DuAnMau_DuyTien.Adapter.PhieuMuonAdapter;
import tienndph30518.DuAnMau_DuyTien.Adapter.SachAdapter;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.LoaiSachDao;
import tienndph30518.DuAnMau_DuyTien.dao.SachDao;
import tienndph30518.DuAnMau_DuyTien.model.LoaiSACH;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;
import tienndph30518.DuAnMau_DuyTien.model.Sach;


public class QL_Sach_Fragment extends Fragment {
    private SachAdapter adapter;
    private SachDao sachDao;
    private ArrayList<Sach> oriList;
    private ArrayList<Sach> list;
    private RecyclerView recyclerLoaiSach;
    private FloatingActionButton floatAddLoaiSach;
    private ArrayList<LoaiSACH> arrLoaiSach = new ArrayList<>();
    public QL_Sach_Fragment() {
        // Required empty public constructor
    }


    public static QL_Sach_Fragment newInstance() {
        QL_Sach_Fragment fragment = new QL_Sach_Fragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_l__sach_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerLoaiSach = view.findViewById(R.id.recyclerQlSAch);
        floatAddLoaiSach = view.findViewById(R.id.floatAddLoaiSach);
        loatData();
        floatAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoaiSach();
            }
        });
    }

    public void loatData() {
        sachDao = new SachDao(getContext());
        list = sachDao.getDanhSachDS();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerLoaiSach.setLayoutManager(manager);
        adapter = new SachAdapter(list, getContext(), getLoaisach());
        recyclerLoaiSach.setAdapter(adapter);
    }

    // thêm Mới Loại Sách
    public void showLoaiSach() {
// Kiểm Tra Xem Có Loại Sách Chưa Nếu chưa Có Không Cho Hiển Thị Dialog
        LoaiSachDao sachDAO  =new LoaiSachDao(getContext());
        arrLoaiSach = sachDAO.getLoaiSach();
        if(arrLoaiSach.size() == 0){
            Toast.makeText(getContext(), "Bạn chưa thêm 'Loại sách' ", Toast.LENGTH_SHORT).show();
            return;
        }
        //..

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themsach, null);
        EditText edTenSach = view.findViewById(R.id.edtTenSach);
        EditText edTien = view.findViewById(R.id.edtTien);
        EditText edTrangSach = view.findViewById(R.id.edtTrangSach);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);
        builder.setView(view);
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(), getLoaisach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spnLoaiSach.setAdapter(simpleAdapter);
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach = edTenSach.getText().toString().trim();
                String tien = edTien.getText().toString().trim();
                String trangSach = edTrangSach.getText().toString().trim();
                int TrangSach = Integer.parseInt(trangSach);
                int tienn = Integer.parseInt(tien);


                if (tensach.isEmpty() || tien.isEmpty() || trangSach.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                } else if (Character.isDigit(tensach.charAt(0)) && Character.isDigit(tensach.charAt(1) )) {
                    Toast.makeText(getContext(), "Không Được Nhập Ký Tự Đầu Tiên Bằng Số", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tensach.length() > 15) {
                    Toast.makeText(getContext(), "Không Được Nhập Quá 15 Ký Tự", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tensach.length() < 5) {
                    Toast.makeText(getContext(), "không Được Nhập Tên Sách Nhỏ Hơn 5 Ký Tự", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tien.isEmpty()) {
                    Toast.makeText(getContext(), " Giá Tiền Không Được Để Chống", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();// ép kiểu về HashMap để lấy đc HashMap
                int spnLoai = (int) hs.get("maloai");//ép kiẻu HashMap về int để lấy id
                sachDao = new SachDao(getContext());
                boolean check = sachDao.theSach(tensach, tienn, spnLoai, TrangSach);
                if (check) {
                    Toast.makeText(getContext(), "Thêm Sách Thành Công", Toast.LENGTH_SHORT).show();
                    loatData();
                } else {
                    Toast.makeText(getContext(), "Thêm Sách Thất Bại", Toast.LENGTH_SHORT).show();
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

    // lấy thể loại sách lên spiner
    public ArrayList<HashMap<String, Object>> getLoaisach() {
        LoaiSachDao loaiSachDao = new LoaiSachDao(getContext());
        ArrayList<LoaiSACH> list = loaiSachDao.getLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (LoaiSACH loaiSACH : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maloai", loaiSACH.getId());
            hs.put("tenloai", loaiSACH.getTenloai());
            listHM.add(hs);
        }
        return listHM;
    }


    // hàm dùng để tìm kiếm thông tin theo trang sách
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.timkiem, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_seach);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setIconified(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


// Trước khi thực hiện tìm kiếm, lưu trữ danh sách gốc
        list = sachDao.getDanhSachDS();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Không cần thực hiện gì trong phương thức này
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                oriList = new ArrayList<>();
                // Kiểm tra nếu newText là một số nguyên dương
                if (newText.matches("\\d+")) {
                    int isCheck = Integer.parseInt(newText);
                    // Lặp qua danh sách gốc và thêm các phần tử có số lượng từ 1 đến isCheck vào danh sách tìm kiếm
                    for (Sach sach : list) {
                        if (sach.getSoLuong() >= 1 && sach.getTraangsach() <= isCheck) {
                            oriList.add(sach);
                        }
                    }
                }
                // Tạo danh sách mới để lưu trữ kết quả tìm kiếm

                // Tạo adapter mới với danh sách tìm kiếm
                SachAdapter phieuMuonAdapter = new SachAdapter(oriList, getContext());
                // Gán adapter mới cho RecyclerView hoặc ListView của bạn
                recyclerLoaiSach.setAdapter(phieuMuonAdapter);

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


}