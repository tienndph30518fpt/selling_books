package tienndph30518.DuAnMau_DuyTien.fragment;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.Adapter.PhieuMuonAdapter;
import tienndph30518.DuAnMau_DuyTien.DangNhap_Activity;
import tienndph30518.DuAnMau_DuyTien.Dbheper.Dbheper;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;


public class ThemTK_Fragment extends Fragment {
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtFullName;
    private Button btnRegister;
    private ImageView imageView, imageView1;
    private Dbheper database;

    private boolean isPasswordVisible = false;

    public ThemTK_Fragment() {
        // Required empty public constructor
    }


    public static ThemTK_Fragment newInstance() {
        ThemTK_Fragment fragment = new ThemTK_Fragment();
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
        return inflater.inflate(R.layout.fragment_them_t_k_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtUsername = view.findViewById(R.id.edt_username);
        edtPassword = view.findViewById(R.id.edt_password);
        edtFullName = view.findViewById(R.id.edt_full_name);
        btnRegister = view.findViewById(R.id.btn_register);
        imageView = view.findViewById(R.id.imgPassword);
        imageView1 = view.findViewById(R.id.imggPassword);
        // Xử lý sự kiện khi nhấn nút đăng ký
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        // img xem mật khẩu số 1
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    imageView.setImageResource(R.drawable.eye);
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    imageView.setImageResource(R.drawable.eyes);
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });
// xem mật khẩu số 2
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPasswordVisible = !isPasswordVisible;
                if (isPasswordVisible) {
                    imageView1.setImageResource(R.drawable.eye);
                    edtFullName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    imageView1.setImageResource(R.drawable.eyes);
                    edtFullName.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });
    }
// kiểm tra xem mật khẩu và tài khoản đúng logic chưa
    private void registerUser() {


        Dbheper db = new Dbheper(getContext());


        // Lấy thông tin từ các trường nhập liệu
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String fullName = edtFullName.getText().toString().trim();

        // Kiểm tra các trường nhập liệu có hợp lệ không
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.length() < 3) {
            Toast.makeText(getContext(), "Tài khoản phải có ít nhất 3 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 3) {
            Toast.makeText(getContext(), "Mật khẩu phải có ít nhất 3 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(fullName)) {
            Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra xem tài khoản đã tồn tại chưa
        if (isUsernameExists(username)) {
            Toast.makeText(getContext(), "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thực hiện thêm tài khoản vào cơ sở dữ liệu
        addUserToDatabase(username, password, fullName);

        String role = "user"; // Thay đổi giá trị role tùy theo cách bạn định nghĩa tài khoản admin và tài khoản người dùng

// Cập nhật vai trò cho tài khoản mới


        db.updateRoleForUser(username, role);

        // Ví dụ: chuyển đến Fragment đăng nhập
        // Hiển thị thông báo đăng ký thành công
        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), DangNhap_Activity.class);
        startActivity(intent);

        // Sau khi đăng ký thành công, có thể chuyển đến Fragment hoặc Activity khác


    }


    private boolean isUsernameExists(String username) {
        // Kiểm tra xem tài khoản đã tồn tại trong cơ sở dữ liệu chưa
        database = new Dbheper(getContext());
        SQLiteDatabase db = database.getWritableDatabase();
        String query = "SELECT COUNT(*) FROM THUTHU WHERE matt = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        return false;
    }


    // thêm tài khoản mật khẩu moi
    private void addUserToDatabase(String username, String password, String fullName) {
        // Thêm tài khoản vào cơ sở dữ liệu
        database = new Dbheper(getContext());
        SQLiteDatabase db = database.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("matt", username);
        values.put("matkhau", password);
        values.put("hoten", fullName);
        db.insert("THUTHU", null, values);
    }



    // tim kiem
    

}

