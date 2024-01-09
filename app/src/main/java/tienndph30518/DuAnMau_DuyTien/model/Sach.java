package tienndph30518.DuAnMau_DuyTien.model;

public class Sach {
    private int masach;
    private String tensach;
    private int giathue;
    private int maloai;
    private int soluongdaumuon;
    private String tenloai;
    private int SoLuong;
    private int traangsach;

    public Sach(int masach, String tensach, int giathue, int maloai,  String tenloai, int soLuong, int traangsach) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.tenloai = tenloai;
        this.SoLuong = soLuong;
        this.traangsach = traangsach;
    }

    public Sach(int masach, String tensach, int giathue, int maloai, String tenloai, int soLuong) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;

        this.tenloai = tenloai;
        SoLuong = soLuong;
    }


    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public Sach(int masach, String tensach, int giathue, int maloai, String tenloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
        this.tenloai = tenloai;
    }

    public Sach(int masach, String tensach, int soluongdaumuon) {
        this.masach = masach;
        this.tensach = tensach;
        this.soluongdaumuon = soluongdaumuon;
    }

    public Sach() {
    }

    public int getTraangsach() {
        return traangsach;
    }

    public void setTraangsach(int traangsach) {
        this.traangsach = traangsach;
    }

    public Sach(int masach, String tensach, int giathue, int maloai) {
        this.masach = masach;
        this.tensach = tensach;
        this.giathue = giathue;
        this.maloai = maloai;
    }

    public int getMasach() {
        return masach;
    }

    public void setMasach(int masach) {
        this.masach = masach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getGiathue() {
        return giathue;
    }

    public void setGiathue(int giathue) {
        this.giathue = giathue;
    }

    public int getMaloai() {
        return maloai;
    }

    public void setMaloai(int maloai) {
        this.maloai = maloai;
    }

    public int getSoluongdaumuon() {
        return soluongdaumuon;
    }

    public void setSoluongdaumuon(int soluongdaumuon) {
        this.soluongdaumuon = soluongdaumuon;
    }

    public String getTenloai() {
        return tenloai;
    }

    public void setTenloai(String tenloai) {
        this.tenloai = tenloai;
    }
}
