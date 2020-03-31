package com.poly.model;

import org.springframework.lang.NonNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "baiviet")
public class BaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MaBaiViet")
    Long MaBaiViet;

    @Column(name = "TenTaiKhoan")
    private String TenTaiKhoan;

    @NonNull
    @Size(min = 1, max = 500)
    @Column(name = "TieuDe")
    private String TieuDe;

    @NonNull
    @Size(min = 1, max = 500)
    @Column(name = "MoTa")
    private String MoTa;

    @NonNull
    @Size(min = 1, max = 15000)
    @Column(name = "NoiDung")
    private String NoiDung;

    @Column(name = "Ngay")
    private Date Ngay;

    @NonNull
    @Size(min = 1, max = 2000)
    @Column(name = "Anh")
    private String Anh;

    @Column(name = "TrangThai")
    private boolean TrangThai = false;
    public BaiViet() {

    }

    public BaiViet(Long MaBaiViet, String TenTaiKhoan, String TieuDe, String MoTa, String NoiDung, Date Ngay, String Anh, Boolean TrangThai){
        this.MaBaiViet = MaBaiViet;
        this.TenTaiKhoan = TenTaiKhoan;
        this.TieuDe = TieuDe;
        this.MoTa = MoTa;
        this.NoiDung = NoiDung;
        this.Ngay = Ngay;
        this.Anh = Anh;
        this.TrangThai = TrangThai;

    }

    @Override
    public String toString(){
        return "BaiViet{"+
                "MaBaiViet='"+MaBaiViet+ '\'' +
                ", TenTaiKhoan='" + TenTaiKhoan + '\'' +
                ", TieuDe='" + TieuDe + '\'' +
                ", MoTa='" + MoTa + '\'' +
                ", NoiDung='" + NoiDung + '\'' +
                ", Ngay='" + Ngay + '\'' +
                ", Anh='" + Anh + '\'' +
                ", TrangThai='" + TrangThai + '\'' +
                '}';
    }

    public Long getMaBaiViet() {
        return MaBaiViet;
    }

    public void setMaBaiViet(Long id) {
        MaBaiViet = MaBaiViet;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getTieuDe() {
        return TieuDe;
    }

    public void setTieuDe(String tieuDe) {
        TieuDe = tieuDe;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public Date getNgay() {
        return Ngay;
    }

    public void setNgay(Date ngay) {
        Ngay = ngay;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean trangThai) {
        TrangThai = trangThai;
    }
}
