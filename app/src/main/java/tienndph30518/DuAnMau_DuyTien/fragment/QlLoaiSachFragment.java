package tienndph30518.DuAnMau_DuyTien.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

import tienndph30518.DuAnMau_DuyTien.Adapter.LoaiSachAdapter;
import tienndph30518.DuAnMau_DuyTien.Adapter.SachAdapter;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.LoaiSachDao;
import tienndph30518.DuAnMau_DuyTien.dao.SachDao;
import tienndph30518.DuAnMau_DuyTien.model.ItemClick;
import tienndph30518.DuAnMau_DuyTien.model.LoaiSACH;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;
import tienndph30518.DuAnMau_DuyTien.model.Sach;


public class QlLoaiSachFragment extends Fragment {
    private LoaiSachAdapter adapter;
    private LoaiSachDao sachDao;
    private RecyclerView recyclerView;
    private EditText loaisach , tacgia;
    int maloai;

    ArrayList<LoaiSACH> list ;
    ArrayList<LoaiSACH>oriList ;
    ArrayList<LoaiSACH>arr ;
    public QlLoaiSachFragment() {
        // Required empty public constructor
    }


    public static QlLoaiSachFragment newInstance() {
        QlLoaiSachFragment fragment = new QlLoaiSachFragment();

        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_loai_sach, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewQLLoaiSach);
        loaisach = view.findViewById(R.id.edLoaiSach);
        tacgia = view.findViewById(R.id.edTacGia);
        Button btnThemMoi = view.findViewById(R.id.btnNhapSach);
        Button btnSua = view.findViewById(R.id.btnSuaSach);

        loatData();
        sachDao = new LoaiSachDao(getContext());
        // btn thêm mới loại sách
        btnThemMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edLoaiSach = loaisach.getText().toString().trim();
                String edTacgia = tacgia.getText().toString().trim();


                if (edLoaiSach.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (sachDao.themLoaiSach(edLoaiSach, edTacgia)) {
                    Toast.makeText(getContext(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                    loaisach.setText("");
                    tacgia.setText("");
                    loatData();

                } else {
                    Toast.makeText(getContext(), "Thêm Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
// btn sửa loại sách
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = loaisach.getText().toString();
                String tacgiaa = tacgia.getText().toString();
                 sachDao = new LoaiSachDao(getContext());
                if (ten.isEmpty() || tacgiaa.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                 LoaiSACH loaiSACH = new LoaiSACH(maloai,ten, tacgiaa);
                 if (sachDao.suaSach(loaiSACH)){
                     loatData();
                     loaisach.setText("");
                     tacgia.setText("");
                     Toast.makeText(getContext(), "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                 }else {
                     Toast.makeText(getContext(), "Sửa thất Bại", Toast.LENGTH_SHORT).show();
                 }
            }
        });


        return view;
    }
// loatData lên recycView
    public void loatData() {
        sachDao = new LoaiSachDao(getContext());
        ArrayList<LoaiSACH> list = sachDao.getLoaiSach();

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new LoaiSachAdapter(list, getContext(), new ItemClick() {
            @Override
            public void onClick(LoaiSACH loaiSACH) {
          loaisach.setText(loaiSACH.getTenloai());
          tacgia.setText(loaiSACH.getTacgia());
          maloai = loaiSACH.getId();
            }
        });
        recyclerView.setAdapter(adapter);
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.timkiem, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.action_seach);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
        searchView.setIconified(true);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);


        oriList = sachDao.getLoaiSach();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            // Không cần thực hiện gì trong phương thức này
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {



            arr = new ArrayList<>();
            // Kiểm tra nếu newText là một số nguyên dương
//            if (newText.matches("\\d+")) {
//                int isCheck = Integer.parseInt(newText);
//                // Lặp qua danh sách gốc và thêm các phần tử có số lượng từ 1 đến isCheck vào danh sách tìm kiếm
//                for (LoaiSACH sach : list) {
//                    if (sach.getId() >= 1 && sach.getId() <= isCheck) {
//                        oriList.add(sach);
//                    }
//                }
//            }

            // Lặp qua danh sách gốc và thêm các phần tử phù hợp vào danh sách tìm kiếm
            for (LoaiSACH phieuMuon : oriList) {
                String maSach = String.valueOf(phieuMuon.getTenloai());
                String normalizedMaSach = normalizeString(maSach);
                if (normalizedMaSach.toLowerCase().contains(newText.toLowerCase())||
                normalizedMaSach.toLowerCase().replace("đ","d").contains(newText.toLowerCase())) {
                    arr.add(phieuMuon);
                }
            }

            // Tạo danh sách mới để lưu trữ kết quả tìm kiếm

            // Tạo adapter mới với danh sách tìm kiếm
            LoaiSachAdapter phieuMuonAdapter = new LoaiSachAdapter(arr, getContext());
            // Gán adapter mới cho RecyclerView hoặc ListView của bạn
            recyclerView.setAdapter(phieuMuonAdapter);

            return true;
        }

    });

        super.onCreateOptionsMenu(menu, inflater);

}

    private String normalizeString(String input) {
        //ó ý nghĩa là chuẩn hóa chuỗi input và loại bỏ các dấu diacritic trong chuỗi đó.
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