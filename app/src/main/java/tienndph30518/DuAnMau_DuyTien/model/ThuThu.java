package tienndph30518.DuAnMau_DuyTien.model;

public class ThuThu {
    private String matt;
    private String hoten;
    private String matkhau;
private int taikhoan;

    public int getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(int taikhoan) {
        this.taikhoan = taikhoan;
    }

    public ThuThu(String matt, String hoten, String matkhau, int taikhoan) {
        this.matt = matt;
        this.hoten = hoten;
        this.matkhau = matkhau;
        this.taikhoan = taikhoan;
    }

    public ThuThu(String matt, String hoten, String matkhau) {
        this.matt = matt;
        this.hoten = hoten;
        this.matkhau = matkhau;
    }

    public ThuThu() {
    }

    public String getMatt() {
        return matt;
    }

    public void setMatt(String matt) {
        this.matt = matt;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
