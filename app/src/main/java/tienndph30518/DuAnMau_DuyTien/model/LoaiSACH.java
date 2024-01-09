package tienndph30518.DuAnMau_DuyTien.model;

public class LoaiSACH {
    private int id;
    private String tenloai;
    private String tacgia;

    public LoaiSACH(int id, String tenloai, String tacgia) {
        this.id = id;
        this.tenloai = tenloai;
        this.tacgia = tacgia;
    }

    public String getTacgia() {
        return tacgia;
    }

    public void setTacgia(String tacgia) {
        this.tacgia = tacgia;
    }

    public LoaiSACH(int id, String tenloai) {
        this.id = id;
        this.tenloai = tenloai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}
