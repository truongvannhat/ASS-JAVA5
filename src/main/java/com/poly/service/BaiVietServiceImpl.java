package com.poly.service;

import com.poly.model.BaiViet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BaiVietServiceImpl implements BaiVietService{
    @Autowired
    private com.poly.repository.BaiVietRepository baiVietRepository;

    @Autowired
    private com.poly.repository.BaiVietRepositoryPaing baiVietRepositoryPaing;


    @Override
    public List<BaiViet> findAll() {
        return null;
    }

    @Override
    public Optional<BaiViet> findById(Long id) {
        return baiVietRepositoryPaing.findById(id);
    }

    @Override
    public void save(BaiViet baiViet) {
        baiVietRepositoryPaing.save(baiViet);
    }

    @Override
    public void remove(Long id) {
    baiVietRepositoryPaing.deleteById(id);
    }

    @Override
    public void update(BaiViet baiViet) {
        baiVietRepositoryPaing.save(baiViet);
    }

    @Override
    public List<BaiViet> findAllBaiVietByTenDangNhap(String TenDangNhap) {
        return baiVietRepository.findAllBaiVietByTenDangNhap(TenDangNhap);
    }

    @Override
    public List<BaiViet> findAllBaiVietByTenTieuDe(String tieuDe) {
        return baiVietRepository.findAllBaiVietByTieuDe(tieuDe);
    }

    @Override
    public List<BaiViet> findAllBaiVietByTrangThai() {
        return baiVietRepository.findAllBaiVietByTrangThai();
    }
}
