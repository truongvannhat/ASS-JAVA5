package com.poly.repository;

import com.poly.model.BaiViet;
import com.poly.model.TaiKhoan;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.util.List;

public class BaiVietRepositoryImpl implements BaiVietRepository {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<BaiViet> findAllBaiVietByTenDangNhap(String TenDangNhap) {
        String query = "Select c from BaiViet c where c.TenTaiKhoan = :tenDangNhap";
        TypedQuery<BaiViet> BaiVietTypedQuery=entityManager.createQuery(query.toString(),BaiViet.class);
        BaiVietTypedQuery.setParameter("tenDangNhap",TenDangNhap);
        return BaiVietTypedQuery.getResultList();
    }

    @Override
    public List<BaiViet> findAllBaiVietByTieuDe(String TieuDe) {
        TieuDe = "%"+TieuDe;
        String query = "Select c from BaiViet c where c.TieuDe like :tieuDe";
        TypedQuery<BaiViet> BaiVietTypedQuery=entityManager.createQuery(query.toString(),BaiViet.class);
        BaiVietTypedQuery.setParameter("tieuDe",TieuDe);
        return BaiVietTypedQuery.getResultList();
    }

    @Override
    public List<BaiViet> findAllBaiVietByTrangThai() {
        String query = "Select c from BaiViet c where c.TrangThai = :trangThai";
        TypedQuery<BaiViet> BaiVietTypedQuery=entityManager.createQuery(query.toString(),BaiViet.class);
        BaiVietTypedQuery.setParameter("trangThai",true);
        return BaiVietTypedQuery.getResultList();
    }

    @Override
    public List<BaiViet> findAll() {
        return null;
    }

    @Override
    public BaiViet findById(Long maBaiViet) {
        try {
            String query = "Select c from BaiViet c where c.MaBaiViet = :maBaiViet";
            TypedQuery<BaiViet> accountTypedQuery=entityManager.createQuery(query.toString(),BaiViet.class);
            accountTypedQuery.setParameter("MaBaiViet",maBaiViet);
            return accountTypedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void save(BaiViet model) {
//        Query query = entityManager.createNativeQuery("INSERT INTO BaiViet(MaBaiViet,TenTaiKhoan, TieuDe, Mota, NoiDung, Ngay, Anh) " +
//                " VALUES(default,?,?,?,?,?,?)");
//        query.setParameter(1, model.getTenTaiKhoan());
//        query.setParameter(2, model.getTieuDe());
//        query.setParameter(3, model.getMoTa());
//        query.setParameter(4, model.getNoiDung());
//        query.setParameter(5, model.getNgay());
//        query.setParameter(6, model.getAnh());
//        query.executeUpdate();

//        entityManager.persist(model);
    }

    @Override
    public void remove(BaiViet model) {
    entityManager.remove(model);
    }

    @Override
    public void update(Long id, BaiViet model) {
        entityManager.merge(model);
    }
}
