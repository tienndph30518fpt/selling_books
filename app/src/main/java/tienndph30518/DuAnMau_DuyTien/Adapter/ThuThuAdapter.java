package tienndph30518.DuAnMau_DuyTien.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.ThuThuDao;
import tienndph30518.DuAnMau_DuyTien.model.ThuThu;

public class ThuThuAdapter extends RecyclerView.Adapter<ThuThuAdapter.Myhover> {

    ArrayList<ThuThu> list = new ArrayList<>();
    private Context context;

    public ThuThuAdapter(ArrayList<ThuThu> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Myhover onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_thuthu, parent, false);
        return new Myhover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myhover holder, int position) {
        holder.matt.setText("Mã Thủ Thư: " + list.get(position).getMatt());
        holder.tentt.setText("Tên Thủ Thư: " + list.get(position).getHoten());
        holder.matkhau.setText("Mật Khẩu: " + list.get(position).getMatkhau());
        holder.taikhoan.setText("Tài Khoản: "+list.get(position).getTaikhoan());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class Myhover extends RecyclerView.ViewHolder {
        TextView matt, tentt, matkhau, taikhoan;

        public Myhover(@NonNull View itemView) {
            super(itemView);
            matkhau = itemView.findViewById(R.id.idmatkhau);
            matt = itemView.findViewById(R.id.idmatt);
            tentt = itemView.findViewById(R.id.idtentt);
            taikhoan = itemView.findViewById(R.id.idtaikhoan);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                    View view = inflater.inflate(R.layout.dialogthuthu_sua, null);
                    EditText edTen = view.findViewById(R.id.edSuatenthu);

                    EditText edMatKhau = view.findViewById(R.id.edSuamatkhau);

                    builder.setView(view);
                    builder.setNegativeButton("Sửa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String ten = edTen.getText().toString().trim();

                            String matkhau = edMatKhau.getText().toString().trim();
                            ThuThu thuThu = list.get(getLayoutPosition());
                            thuThu.setHoten(ten);
                            thuThu.setMatkhau(matkhau);
                            ThuThuDao thuThuDao = new ThuThuDao(context);

                            if (matkhau.isEmpty()|| ten.isEmpty()){
                                Toast.makeText(context, "Không Được Bỏ Chống", Toast.LENGTH_SHORT).show();
                            }
                            if (thuThuDao.suaTenThuThu(thuThu) > 0) {
                                list.clear();
                                list = thuThuDao.getDS();

                                notifyDataSetChanged();
                                Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Sửa thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ThuThuDao thuThuDao = new ThuThuDao(context);
                            ThuThu thu = list.get(getLayoutPosition());

                            if (thuThuDao.deleteTableThuThu(thu) >= 0) {
                                list.clear();
                                list = thuThuDao.getDS();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Xoá Thất Bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }
}
