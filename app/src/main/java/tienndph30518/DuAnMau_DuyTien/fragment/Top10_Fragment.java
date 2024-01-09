package tienndph30518.DuAnMau_DuyTien.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Adapter.Top10_Adapter;
import tienndph30518.DuAnMau_DuyTien.DangNhap_Activity;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.PhieuMuonDao;
import tienndph30518.DuAnMau_DuyTien.dao.ThongKeDao;
import tienndph30518.DuAnMau_DuyTien.model.Sach;


public class Top10_Fragment extends Fragment {
private Top10_Adapter adapter;

String  matt="";
    RecyclerView recyclerView;
    public Top10_Fragment() {
        // Required empty public constructor
    }


    public static Top10_Fragment newInstance() {
        Top10_Fragment fragment = new Top10_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top10, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.idRecyclerView);
       // đưa dữ liệu lên recycView

        LoatData();
    }

    public void LoatData(){
        matt = DangNhap_Activity.matt;
        PhieuMuonDao thuThuDao = new PhieuMuonDao(getContext());
        boolean isAdmin = thuThuDao.isAdmin(matt);

        if (isAdmin){


            ThongKeDao thongKeDao = new ThongKeDao(getContext());
            ArrayList<Sach> list = thongKeDao.getTop10();
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);
            adapter = new Top10_Adapter(getContext(),list);
            recyclerView.setAdapter(adapter);
        }else {
            ThongKeDao thongKeDao = new ThongKeDao(getContext());
            ArrayList<Sach> list = thongKeDao.getTop1012(matt);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(manager);
            adapter = new Top10_Adapter(getContext(),list);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LoatData();
    }
}