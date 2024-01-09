package tienndph30518.DuAnMau_DuyTien;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
//import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.HandlerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Adapter.PhieuMuonAdapter;
import tienndph30518.DuAnMau_DuyTien.dao.PhieuMuonDao;
import tienndph30518.DuAnMau_DuyTien.dao.ThuThuDao;
import tienndph30518.DuAnMau_DuyTien.fragment.QL_Sach_Fragment;
import tienndph30518.DuAnMau_DuyTien.fragment.QlLoaiSachFragment;
import tienndph30518.DuAnMau_DuyTien.fragment.QuanLyPhieuMuonFragment;
import tienndph30518.DuAnMau_DuyTien.fragment.QuanLyTHuThu_Fragment;
import tienndph30518.DuAnMau_DuyTien.fragment.QuanLyThanhVien_Fragment;
import tienndph30518.DuAnMau_DuyTien.fragment.ThemTK_Fragment;
import tienndph30518.DuAnMau_DuyTien.fragment.ThongKeDoanhThu_Fragment;
import tienndph30518.DuAnMau_DuyTien.fragment.Top10_Fragment;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;
import tienndph30518.DuAnMau_DuyTien.model.ThanhVien;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FrameLayout frameLayout;
    private View header;
    TextView tvUrse;
    String matt = "";
    private DrawerLayout drawerLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.idFramneLayout);
        toolbar = findViewById(R.id.idToolBar);
        navigationView = findViewById(R.id.idNavigationView);
        drawerLayout = findViewById(R.id.idDrawerLayout);

        // lấy urse để đưa lên header
        SharedPreferences preference = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String urse = preference.getString("matt", "");

        header = navigationView.getHeaderView(0);
        tvUrse = header.findViewById(R.id.idxinchao);
        tvUrse.setText("Welcome   " + urse);


        // Chuyển Đổi Các Chức Năng Của ActionBar cho thằng Toolbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        // hiển thị fragment mặc định khi vào app
        QuanLyPhieuMuonFragment fragment = new QuanLyPhieuMuonFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.idFramneLayout, fragment).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.mQLPhieuMuon) {
                    fragment = new QuanLyPhieuMuonFragment();

                } else if (item.getItemId() == R.id.mQLLoaiSach) {
                    fragment = new QlLoaiSachFragment();

                } else if (item.getItemId() == R.id.mQLSach) {
                    fragment = new QL_Sach_Fragment();
                } else if (item.getItemId() == R.id.mQLThanhVien) {
                    fragment = new QuanLyThanhVien_Fragment();
                } else if (item.getItemId() == R.id.mTop10) {
                    fragment = new Top10_Fragment();
                } else if (item.getItemId() == R.id.mQLDoanhThu) {
                    fragment = new ThongKeDoanhThu_Fragment();
                } else if (item.getItemId()==R.id.mThemTK) {
                    fragment = new ThemTK_Fragment();


                } else if (item.getItemId()==R.id.mQLTHUTHU) {
                    fragment = new QuanLyTHuThu_Fragment();

                } else if (item.getItemId() == R.id.mDoiMatKhau) {
                    dialogDoiMatKhau();
                } else if (item.getItemId() == R.id.mDangXuat) {
                    Intent intent = new Intent(getApplicationContext(), DangNhap_Activity.class);
                    // tự động xoá đi các activity đang mở trước đó

                    // Khi Đăng Xuất Thì Sẽ Xoá Trắng Toàn Bộ urse và pass
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MainActivity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("matt"); // Xóa key "username"
                    editor.remove("matkhau"); // Xóa key "password"
                    editor.apply();

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    fragment = new QuanLyPhieuMuonFragment();

                }
                if (fragment != null) {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.idFramneLayout, fragment).commit();


                }

                // khi trọn một fragment bất kỳ thì sẽ tự động đóng cái NavigationView đó lại
                drawerLayout.closeDrawer(GravityCompat.START);
                toolbar.setTitle(item.getTitle());
                return false;
            }
        });


        // kiểm tra xem nếu là admin thì được xem thống kê với doanh thu còn thủ thư thì không
        matt = DangNhap_Activity.matt;
        PhieuMuonDao thuThuDao = new PhieuMuonDao(this);
        boolean isAdmin = thuThuDao.isAdmin(matt);
        if (!isAdmin) {
            Menu menu = navigationView.getMenu();
//            menu.findItem(R.id.mTop10).setVisible(false);
//            menu.findItem(R.id.mQLDoanhThu).setVisible(false);
            menu.findItem(R.id.mThemTK).setVisible(false);
            menu.findItem(R.id.mQLTHUTHU).setVisible(false);
        }
    }


    // chượt ra trượt vào của NavigationView
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    // đổi mật khẩu
    public void dialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this).setNegativeButton("Huỷ", null).
                setPositiveButton("Cập Nhật", null);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimk, null);
        builder.setView(view);
        EditText odlPass = view.findViewById(R.id.edtNhapMatKhauCu);
        EditText newPass = view.findViewById(R.id.edtNhapMatKhauMoi);
        EditText RenewPass = view.findViewById(R.id.edtNhapLaiMatKhauMoi);


        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = odlPass.getText().toString().trim();
                String newPasss = newPass.getText().toString().trim();
                String renewPass = RenewPass.getText().toString().trim();
                if (newPasss.equals(renewPass)) {
                    // lấy ra mật khẩu cũ
                    SharedPreferences preference = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    String matt = preference.getString("matt", "");
                    ThuThuDao thuThuDao = new ThuThuDao(getApplicationContext());

                    int check = thuThuDao.capNhatMatKhau(matt, oldPass, newPasss);

                    if (oldPass.equals("") || newPasss.equals("") || renewPass.equals("")) {
                        Toast.makeText(MainActivity.this, "Không Được để chống Hãy Nhập Đầy Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (check == 1) {
                        Toast.makeText(MainActivity.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DangNhap_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else if (check == 0) {
                        Toast.makeText(MainActivity.this, "Mật Khẩu Cũ Không Đúng", Toast.LENGTH_SHORT).show();
                    } else if (check == -1) {
                        Toast.makeText(MainActivity.this, "Cập Nhật Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Mật Khẩu Không Trùng Nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.timkiem, menu);
//        SearchManager  searchManager  =(SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        searchView  =(SearchView)menu.findItem(R.id.action_seach).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);

//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        MenuItem searchItem = menu.findItem(R.id.action_seach);
//        searchView = (SearchView) searchItem.getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                PhieuMuonDao phieuMuonDao  = new PhieuMuonDao(MainActivity.this);
//                ArrayList<PhieuMuon>list = phieuMuonDao.getDanhSachPM();
//                PhieuMuonAdapter phieuMuonAdapter  =new PhieuMuonAdapter(list,MainActivity.this);
//                phieuMuonAdapter.getFilter().filter(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                PhieuMuonDao phieuMuonDao  = new PhieuMuonDao(MainActivity.this);
//                ArrayList<PhieuMuon>list = phieuMuonDao.getDanhSachPM();
//                PhieuMuonAdapter phieuMuonAdapter  =new PhieuMuonAdapter(list,MainActivity.this);
//                phieuMuonAdapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//        return true;
//    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.timkiem, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_seach).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        return true;
//    }

}