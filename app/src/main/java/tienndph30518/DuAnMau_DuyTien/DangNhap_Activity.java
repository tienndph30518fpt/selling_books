package tienndph30518.DuAnMau_DuyTien;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import tienndph30518.DuAnMau_DuyTien.dao.ThuThuDao;

public class DangNhap_Activity extends AppCompatActivity {
    private EditText edtUserName, edtPass;
    Button btnDangNhap, btnHuy;
    public static String matt = "";
    private CheckBox checkBox;
    private ImageView imageView;

    private boolean isPasswordVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        edtPass = findViewById(R.id.edtPass);
        edtUserName = findViewById(R.id.edtUser);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        checkBox = findViewById(R.id.idCheckBook);
        btnHuy = findViewById(R.id.btnHuyDN);
        imageView = findViewById(R.id.imgPassword);
        ThuThuDao thuThuDao = new ThuThuDao(getApplicationContext());


        // check xem khi cả hai EditText của tên người dùng và mật khẩu không trống,
        edtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCheckboxState();
            }
        });

        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCheckboxState();
            }
        });

// xem password
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible){
                imageView.setImageResource(R.drawable.eye);
                edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }else {
                imageView.setImageResource(R.drawable.eyes);
                edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }

            }
        });




        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtUserName.setText("");
                edtPass.setText("");
            }
        });



        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUserName.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if (user.isEmpty()&& pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (thuThuDao.checkDangNhap(user, pass)) {
                    // lưu  SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("matt", user);
                    editor.putString("matkhau", pass);
                    editor.putBoolean("isChecked", checkBox.isChecked());
                    editor.commit();
                    matt = edtUserName.getText().toString().trim();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    Toast.makeText(DangNhap_Activity.this, "User Hoạc Pass Không Đúng", Toast.LENGTH_SHORT).show();
                }


            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        boolean isChecked = sharedPreferences.getBoolean("isChecked", false);

    // Nếu checkbox đã được chọn, lấy tên người dùng và mật khẩu từ SharedPreferences
        if (isChecked) {
            String savedUsername = sharedPreferences.getString("matt", "");
            String savedPassword = sharedPreferences.getString("matkhau", "");

            // Hiển thị thông tin người dùng trong EditText
            edtUserName.setText(savedUsername);
            edtPass.setText(savedPassword);
        }

    }
    private void updateCheckboxState() {
        boolean isUsernameEmpty = edtUserName.getText().toString().trim().isEmpty();
        boolean isPasswordEmpty = edtPass.getText().toString().trim().isEmpty();

        // Tự động tích checkbox nếu cả hai EditText không trống
        // Ngược lại, bỏ tích checkbox
        checkBox.setChecked(!(isUsernameEmpty || isPasswordEmpty));
    }
}