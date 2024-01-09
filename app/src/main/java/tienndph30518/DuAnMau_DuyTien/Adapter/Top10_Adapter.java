package tienndph30518.DuAnMau_DuyTien.Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.DangNhap_Activity;
import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.PhieuMuonDao;
import tienndph30518.DuAnMau_DuyTien.dao.ThongKeDao;
import tienndph30518.DuAnMau_DuyTien.model.PhieuMuon;
import tienndph30518.DuAnMau_DuyTien.model.Sach;
import tienndph30518.DuAnMau_DuyTien.model.ThanhVien;

public class Top10_Adapter extends RecyclerView.Adapter<Top10_Adapter.MyviewHoder> {
    private Context context;
    private ArrayList<Sach> list;
    ThongKeDao thongKeDao;
    String matt="";
    public Top10_Adapter(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyviewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_top10,parent,false);
        return new MyviewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHoder holder, int position) {
        holder.tvMaSach.setText("Mã Sách: "+String.valueOf(list.get(position).getMasach()));
        holder.tvTenSach.setText("Tên Sách: "+list.get(position).getTensach());
        holder.tvSoLuong.setText("Số Lượng: "+String.valueOf(list.get(position).getSoluongdaumuon()));
    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    class MyviewHoder extends RecyclerView.ViewHolder {
        private TextView tvMaSach, tvTenSach, tvSoLuong;
        public MyviewHoder(@NonNull View itemView) {
            super(itemView);
            tvMaSach = itemView.findViewById(R.id.tvMaSach);
            tvTenSach = itemView.findViewById(R.id.tvTenSach);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Thông Báo");
                    builder.setMessage("Bạn Có Muốn Xoá Không");
                    builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            matt = DangNhap_Activity.matt;
                            PhieuMuonDao thuThuDao = new PhieuMuonDao(context);
                            boolean isAdmin = thuThuDao.isAdmin(matt);

                            thongKeDao = new ThongKeDao(context);
                          Sach sach = list.get(getLayoutPosition());
                          list.remove(sach);
                          notifyItemRemoved(getLayoutPosition());
//                            int id   = phieumuon.getMapm();
//                            int check = thongKeDao.thongKeDoanhThu(id);
                            if (thongKeDao.XOADOANHTHU(sach) > 0) {
                                if (isAdmin){
                                    list.clear();
                                    list = thongKeDao.getTop10();
                                    notifyDataSetChanged();
                                }else {
                                    list.clear();
                                    list = thongKeDao.getTop1012(matt);
                                    notifyDataSetChanged();
                                }

                                Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Xoá Thất Bại" , Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    Dialog dialog  =builder.create();
                    dialog.show();
                }
            });
        }
    }






}
