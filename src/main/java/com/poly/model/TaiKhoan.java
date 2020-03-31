package com.poly.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Table(name = "taikhoan")
public class TaiKhoan {
    @NonNull
    @Size(min = 3, max = 20)
    @Id
    @Column(name = "TenTaiKhoan")
    private String TenTaiKhoan;

    @NonNull
    @Size(min = 3, max = 50)
    @Column(name = "MatKhau")
    private String MatKhau;

    @NonNull
    @Size(min = 3, max = 50)
    @Column(name = "HoTen")
    private String HoTen;

    @NonNull
    @Size(min = 3, max = 50)
    @Column(name = "Email")
    private String Email;

    @NonNull
    @Size(min = 3, max = 1000)
    @Column(name = "GioiThieu")
    private String GioiThieu;

    @NonNull
    @Size(min = 3, max = 2000)
    @Column(name = "AnhDaiDien")
    private String AnhDaiDien;

    public TaiKhoan() {

    }

    public TaiKhoan(String TenTaiKhoan, String MatKhau, String HoTen, String Email, String GioiThieu, String AnhDaiDien){
        this.TenTaiKhoan = TenTaiKhoan;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.Email = Email;
        this.GioiThieu = GioiThieu;
        this.AnhDaiDien = AnhDaiDien;
    }

    @Override
    public String toString(){
        return "TaiKhoan{"+
                "TenTaiKhoan"+TenTaiKhoan+
                ", MatKhau='" + MatKhau + '\'' +
                ", HoTen='" + HoTen + '\'' +
                ", Email='" + Email + '\'' +
                ", GioiThieu='" + GioiThieu + '\'' +
                ", AnhDaiDien='" + AnhDaiDien + '\'' +
                '}';
    }

    @NonNull
    public String getGioiThieu() {
        return GioiThieu;
    }

    public void setGioiThieu(@NonNull String gioiThieu) {
        GioiThieu = gioiThieu;
    }

    @NonNull
    public String getAnhDaiDien() {
        return AnhDaiDien;
    }

    public void setAnhDaiDien(@NonNull String anhDaiDien) {
        AnhDaiDien = anhDaiDien;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
