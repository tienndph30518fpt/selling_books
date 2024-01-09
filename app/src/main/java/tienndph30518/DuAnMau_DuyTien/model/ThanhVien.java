package tienndph30518.DuAnMau_DuyTien.model;

public class ThanhVien {
    private int matv;
    private String hoten;
    private String namsinh;
    private int taikhoan;

    public ThanhVien(int matv, String hoten, String namsinh, int taikhoan) {
        this.matv = matv;
        this.hoten = hoten;
        this.namsinh = namsinh;
        this.taikhoan = taikhoan;
    }

    public int getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(int taikhoan) {
        this.taikhoan = taikhoan;
    }

    public ThanhVien(int matv, String hoten, String namsinh) {
        this.matv = matv;
        this.hoten = hoten;
        this.namsinh = namsinh;
    }

    public ThanhVien() {
    }

    public int getMatv() {
        return matv;
    }

    public void setMatv(int matv) {
        this.matv = matv;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getNamsinh() {
        return namsinh;
    }

    public void setNamsinh(String namsinh) {
        this.namsinh = namsinh;
    }


}

