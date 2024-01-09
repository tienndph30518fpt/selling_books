package tienndph30518.DuAnMau_DuyTien.Adapter;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.SachDao;
import tienndph30518.DuAnMau_DuyTien.model.Sach;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.MyHoverView> {
    private ArrayList<Sach> list;
    private Context context;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDao sachDao;

    public SachAdapter(ArrayList<Sach> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public SachAdapter(ArrayList<Sach> list, Context context, ArrayList<HashMap<String, Object>> listHM) {
        this.list = list;
        this.context = context;
        this.listHM = listHM;
    }

    @NonNull
    @Override
    public MyHoverView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_loaisach, parent, false);
        final SachAdapter.MyHoverView holder = new SachAdapter.MyHoverView(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                AlertDialog.Builder builder  = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                View view = inflater.inflate(R.layout.item_suatranhsach, null);
                builder.setView(view);
                EditText trangsach = view.findViewById(R.id.edSuaTrangSach);
                trangsach.setText(String.valueOf(list.get(position).getTraangsach()));



                builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sach = trangsach.getText().toString().trim();
                        int sachh = Integer.parseInt(sach);
                        sachDao = new SachDao(context);
                        Sach sach1 = list.get(position);
                        sach1.setTraangsach(sachh);
                        if (sachDao.suaTrangSach(sach1)>0){
                            list.clear();
                            list = sachDao.getDanhSachDS();
                            notifyDataSetChanged();
                            Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Sửa Thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        return holder;
//        return new MyHoverView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHoverView holder, int position) {
        // đổi màu item
//        if (position % 2==0){
//            holder.itemView.setBackgroundColor(Color.RED);
//        }else {
//            holder.itemView.setBackgroundColor(Color.GREEN);
//        }
        holder.tvTenLoai.setText("Tên Loại: " + list.get(position).getTenloai());
        holder.tvMaloai.setText("Mã Loại: " + list.get(position).getMaloai());
        holder.tvMaSach.setText("Mã Sách: " + list.get(position).getMasach());
        holder.tvTenSach.setText("Tên Loại: " + list.get(position).getTensach());
        holder.trangsach.setText("Trang Sách: "+list.get(position).getTraangsach());


        // gia thuê
        int giathue = list.get(position).getGiathue();
        holder.tvGiaThue.setText("Giá Thuê: " + list.get(position).getGiathue());
        if (giathue>=2500){
            holder.tvGiaThue.setTypeface(null , Typeface.BOLD);
        }else {
            holder.tvGiaThue.setTextColor(Color.RED);
        }

        holder.tvSoLuong.setText("Số Lượng Sach: "+list.get(position).getSoLuong());
        // click vào ảnh để sửa
        holder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSua(list.get(holder.getAdapterPosition()));
            }
        });
        // click vào ảnh để xoá
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachDao = new SachDao(context);
                int check = sachDao.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check) {
                    case 1:
                        Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list = sachDao.getDanhSachDS();
                        notifyDataSetChanged();
                        break;
                    case 0:
                        Toast.makeText(context, "Xoá Thất Bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không Được Phép Xoá Vì Đã Có Trong Danh Sách Phiếu Mượn", Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        break;
                }
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

    public class MyHoverView extends RecyclerView.ViewHolder {
        private TextView tvMaSach, tvTenSach, tvGiaThue, tvTenLoai, tvMaloai, tvSoLuong, trangsach;
        private ImageView imgXoa, imgSua;

        public MyHoverView(@NonNull View itemView) {
            super(itemView);

            tvTenLoai = itemView.findViewById(R.id.tvTenloai);
            tvMaSach = itemView.findViewById(R.id.tvMasach);
            tvTenSach = itemView.findViewById(R.id.tvTensach);
            tvGiaThue = itemView.findViewById(R.id.tvGiathue);
            tvMaloai = itemView.findViewById(R.id.tvMaloai);
            imgSua = itemView.findViewById(R.id.imgSuaLoaiSach);
            imgXoa = itemView.findViewById(R.id.imgXoaLoaiSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoluongSach);
            trangsach = itemView.findViewById(R.id.tvTrangsach);
            // click vào mỗi item để sửa
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AlertDialog.Builder builder  = new AlertDialog.Builder(context);
//                    LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//                    View view = inflater.inflate(R.layout.item_suatranhsach, null);
//                    builder.setView(view);
//                    EditText trangsach = view.findViewById(R.id.edSuaTrangSach);
////                    trangsach.setText(list.get(getLayoutPosition()).getTraangsach());
//
//
//                    builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            String sach = trangsach.getText().toString().trim();
//                            int sachh = Integer.parseInt(sach);
//                            sachDao = new SachDao(context);
//                            Sach sach1 = list.get(getLayoutPosition());
//                            sach1.setTraangsach(sachh);
//                            if (sachDao.suaTrangSach(sach1)>0){
//                                list.clear();
//                                list = sachDao.getDanhSachDS();
//                                notifyDataSetChanged();
//                                Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(context, "Sửa Thất bại", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }).setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    Dialog dialog = builder.create();
//                    dialog.show();
                }


            });
        }
    }

    // dialog thêm loại sách
    public void showDialogSua(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suasach, null);
        builder.setView(view);
        TextView tvmaSach = view.findViewById(R.id.masach);
        EditText edtTensach = view.findViewById(R.id.edt_Tensach);
        EditText edtTien = view.findViewById(R.id.edt_Tien);
        EditText edtTrangSach = view.findViewById(R.id.edt_TrangSach);
        Spinner spnSach = view.findViewById(R.id.spnLoaiS);

        // xét giá trị lên các ô
        tvmaSach.setText("Mã Sách: " + sach.getMasach());
        edtTensach.setText(sach.getTensach());
        edtTien.setText(String.valueOf(sach.getGiathue()));
        edtTrangSach.setText(String.valueOf(sach.getTraangsach()));
        //lấy giá trị cho spinner
        SimpleAdapter simpleAdapter = new SimpleAdapter(context, listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);

        int index = 0;
        int position = -1;

        for (HashMap<String, Object> item : listHM) {
            if ((int) item.get("maloai") == sach.getMaloai()) {
                position = index;
            }
            index++;

        }
        spnSach.setSelection(position);
        builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String tensach = edtTensach.getText().toString().trim();
                String tien = edtTien.getText().toString().trim();
//                String trangsach = edtTrangSach.getText().toString().trim();
                String trangsach = edtTrangSach.getText().toString().trim();
                int TrangSach = Integer.parseInt(trangsach);
                int tienn = Integer.parseInt(tien);
                HashMap<String, Object> hs = (HashMap<String, Object>) spnSach.getSelectedItem();// ép kiểu về HashMap để lấy đc HashMap
                int spnLoai = (int) hs.get("maloai");//ép kiẻu HashMap về int để lấy id
                sachDao = new SachDao(context);
                boolean check = sachDao.SuaSach(sach.getMasach(), tensach, tienn, spnLoai, TrangSach);
                if (check) {
                    Toast.makeText(context, "Thêm Sách Thành Công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list = sachDao.getDanhSachDS();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Thêm Sách Thất Bại", Toast.LENGTH_SHORT).show();
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


}
