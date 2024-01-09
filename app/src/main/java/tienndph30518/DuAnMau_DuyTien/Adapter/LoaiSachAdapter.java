package tienndph30518.DuAnMau_DuyTien.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import tienndph30518.DuAnMau_DuyTien.R;
import tienndph30518.DuAnMau_DuyTien.dao.LoaiSachDao;
import tienndph30518.DuAnMau_DuyTien.model.ItemClick;
import tienndph30518.DuAnMau_DuyTien.model.LoaiSACH;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.MyHover> {
private LoaiSachDao sachDao;

   ArrayList<LoaiSACH>list;
    Context context;
    ItemClick itemClick;

    public LoaiSachAdapter(ArrayList<LoaiSACH> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public LoaiSachAdapter(ArrayList<LoaiSACH> list, Context context , ItemClick itemClick) {
        this.list = list;
        this.context = context;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public MyHover onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisach,parent, false);
        return new MyHover(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHover holder, int position) {
        holder.tvTenLoai.setText("Tên Loai: "+list.get(position).getTenloai());
        holder.tvMaLoai.setText("Mã Loại: "+list.get(position).getId());
        holder.tvTacGia.setText("Tác Giả: "+list.get(position).getTacgia());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sachDao = new LoaiSachDao(context);
                int check = sachDao.xoaSach(list.get(holder.getAdapterPosition()).getId());
                if (check == 1) {
                    list.clear();
                    list = sachDao.getLoaiSach();
                    notifyDataSetChanged();
                    Toast.makeText(context, "Xoá Thành Công", Toast.LENGTH_SHORT).show();
                } else if (check == -1) {
                    Toast.makeText(context, "Không Thể Xoá Vì Có Sách Thuộc Loại Sách", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xoá Thất Bại", Toast.LENGTH_SHORT).show();
                }
            }


        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSACH loaiSACH = list.get(holder.getAdapterPosition());
                itemClick.onClick(loaiSACH);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (list!= null)
            return list.size();
        return 0;
    }

    class MyHover extends RecyclerView.ViewHolder {
        private TextView tvMaLoai, tvTenLoai, tvTacGia;
        private ImageView imageView , ivEdit;
        public MyHover(@NonNull View itemView) {
            super(itemView);
            tvMaLoai = itemView.findViewById(R.id.tvMaloai);
            tvTenLoai = itemView.findViewById(R.id.tvTenloai);
            imageView = itemView.findViewById(R.id.imgDel);
            ivEdit = itemView.findViewById(R.id.imgEdit);
            tvTacGia = itemView.findViewById(R.id.tvTacGia);

        }
    }

}
