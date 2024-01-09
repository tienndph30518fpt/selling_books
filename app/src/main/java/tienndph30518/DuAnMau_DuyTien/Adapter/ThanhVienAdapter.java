package tienndph30518.DuAnMau_DuyTien.Adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Calendar;

import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.ThanhVienDao;
import tienndph30518.DuAnMau_DuyTien.model.ThanhVien;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.MyViewHover> {

    private ArrayList<ThanhVien> list;
    private Context context;
    private ThanhVienDao vienDao;

    public ThanhVienAdapter(ArrayList<ThanhVien> list, Context context, ThanhVienDao vienDao) {
        this.list = list;
        this.context = context;
        this.vienDao = vienDao;
    }

    public ThanhVienAdapter(ArrayList<ThanhVien> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHover onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qlthanhvien, parent, false);
        return new MyViewHover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHover holder, int position) {
        holder.tvMaTv.setText("Mã: " + list.get(position).getMatv());
        holder.tvTenTv.setText("Tên: " + list.get(position).getHoten());
        holder.tvNamSinh.setText("Năm Sinh: " + list.get(position).getNamsinh());
        holder.tvNguoidung.setText("Số Tài Khoản: "+list.get(position).getTaikhoan());
        if (list.get(position).getTaikhoan()%5==0){
            holder.tvNguoidung.setTextColor(Color.YELLOW);
        }
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCapNhatThanhVien(list.get(holder.getAdapterPosition()));
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //            showDialogDelete(list.get(holder.getAdapterPosition()));


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn Có Muốn Xoá Không");
                builder.setMessage("Thông Báo");
                builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        vienDao = new ThanhVienDao(context);
                        int check = vienDao.xoaThanhVien(list.get(holder.getAdapterPosition()).getMatv());
                        if (check == 1) {
                            Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = vienDao
                                    .getDSThanhVien();
                            notifyDataSetChanged();
                        } else if (check == 0) {
                            Toast.makeText(context, "Xoá Thất Bại", Toast.LENGTH_SHORT).show();
                        } else if (check == -1) {
                            Toast.makeText(context, "Thành Viên Đã Tồn Tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class MyViewHover extends RecyclerView.ViewHolder {
        private TextView tvMaTv, tvTenTv, tvNamSinh, tvNguoidung;
        private ImageView ivEdit, ivDelete;

        public MyViewHover(@NonNull View itemView) {
            super(itemView);
            tvMaTv = itemView.findViewById(R.id.tvMaTV);
            tvTenTv = itemView.findViewById(R.id.tvTenTV);
            tvNamSinh = itemView.findViewById(R.id.tvNamSinh);
            tvNguoidung = itemView.findViewById(R.id.tvNguoidung);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public void showDialogCapNhatThanhVien(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_capnhat_thongtintv, null);
        builder.setView(view);
        EditText edTen = view.findViewById(R.id.edtHoten);
        EditText edNamSinh = view.findViewById(R.id.edtNamsinh);

        edNamSinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialogGio(edNamSinh);

                showDatimDialog(edNamSinh);
            }
        });
        edNamSinh.setFocusable(false);
        TextView tvMatv = view.findViewById(R.id.txtMaTVV);
        tvMatv.setText("Mã TV: " + thanhVien.getMatv());
        edNamSinh.setText(thanhVien.getNamsinh());
        edTen.setText(thanhVien.getHoten());
        vienDao = new ThanhVienDao(context);
        builder.setPositiveButton("Cập Nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ten = edTen.getText().toString().trim();
                String namsinh = edNamSinh.getText().toString().trim();
                int id = thanhVien.getMatv();
                if (vienDao.capNhatThanhhVien(id, ten, namsinh)) {
                    list.clear();
                    list = vienDao.getDSThanhVien();
                    notifyDataSetChanged();
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

    // datim năm Sinh
    public void showDatimDialog(EditText edNamsinh) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String thang = "";
                if (month < 9) {
                    thang = "0" + (month + 1);
                } else {
                    thang = String.valueOf(month + 1);
                }
                String ngay = "";
                if (dayOfMonth < 10) {
                    ngay = "0" + (dayOfMonth);
                } else {
                    ngay = String.valueOf(dayOfMonth);
                }
                edNamsinh.setText(ngay + "/" + thang + "/" + year);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.HOUR_OF_DAY)
        );
        datePickerDialog.show();
    }


    //  xoá thành viên
    public void showDialogDelete(ThanhVien thanhVien) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn Có Muốn Xoá Không");
        builder.setMessage("Thông Báo");
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                vienDao = new ThanhVienDao(context);
                int check = vienDao.xoaThanhVien(thanhVien.getMatv());
                if (check == -1) {
                    Toast.makeText(context, "Xoá Thất bại", Toast.LENGTH_SHORT).show();
                } else if (check == 0) {
                    Toast.makeText(context, "Không Được Phép Xoá", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = vienDao.getDSThanhVien();
                    notifyDataSetChanged();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    public void showDialogGio(EditText edthoigian) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String gio="";
                if (hourOfDay<12){
                    gio = hourOfDay+"AM";
                }else {
                    gio = hourOfDay+"PM";
                }
                edthoigian.setText(gio+":"+minute);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
        );
        timePickerDialog.show();

    }
}
