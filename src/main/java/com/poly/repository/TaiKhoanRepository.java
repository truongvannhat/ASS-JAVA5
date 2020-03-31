package com.poly.repository;

import com.poly.model.TaiKhoan;

import java.util.List;

public interface TaiKhoanRepository extends Repository<TaiKhoan> {

    public TaiKhoan findByUsernameAndPassword(String user, String pass);

}
