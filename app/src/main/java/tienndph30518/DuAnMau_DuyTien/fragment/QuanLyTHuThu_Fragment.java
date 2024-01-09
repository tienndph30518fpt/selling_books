package tienndph30518.DuAnMau_DuyTien.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Adapter.ThuThuAdapter;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.ThuThuDao;
import tienndph30518.DuAnMau_DuyTien.model.ThuThu;


public class QuanLyTHuThu_Fragment extends Fragment {
    RecyclerView recyclerView;

    public QuanLyTHuThu_Fragment() {
        // Required empty public constructor
    }

    public static QuanLyTHuThu_Fragment newInstance() {
        QuanLyTHuThu_Fragment fragment = new QuanLyTHuThu_Fragment();

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

        return inflater.inflate(R.layout.fragment_quan_ly_t_hu_thu_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView  =view.findViewById(R.id.idRecyclerThuThu);
        loatData();

    }
    public void loatData(){
        ThuThuDao thuThuDao  = new ThuThuDao(getContext());
        ArrayList<ThuThu>list = thuThuDao.getDS();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ThuThuAdapter adapter = new ThuThuAdapter(list,getContext());
        recyclerView.setAdapter(adapter);
    }
}