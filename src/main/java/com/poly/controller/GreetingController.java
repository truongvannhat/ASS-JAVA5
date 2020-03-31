package com.poly.controller;

import com.poly.model.BaiViet;
import com.poly.model.TaiKhoan;
import com.poly.service.BaiVietService;
import com.poly.service.TaiKhoanService;
import com.poly.validate.NhatKyValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GreetingController {
    @Autowired
    private TaiKhoanService taiKhoanService;

    @Autowired
    private BaiVietService baiVietService;
    //validate
    NhatKyValidate nhatKyValidate = new NhatKyValidate();
    //đối tượng sau khi đăng nhập
    TaiKhoan taiKhoan = new TaiKhoan();
    Boolean islogin = false;


    //login
    @GetMapping(value = {"login.html"})
    private String login(ModelMap model) {
        taiKhoanService.findAll();
        model.put("errorLogin", null);
        return "login.html";
    }

    //Đăng nhập
    @PostMapping("login.html")
    private String login(HttpSession session, @RequestParam("tenTaiKhoan") String user, @RequestParam("matKhau") String password,
                         ModelMap model) {

        if (session.getAttribute("userLogin") != null) {
            return "redirect:/";
        } else {

            taiKhoan = taiKhoanService.findByUsernameAndPassword(user, password);
            System.out.println(user + "  " + password);
            if (taiKhoan != null) {
                session.setAttribute("account", taiKhoan);
                islogin=true;
                return "redirect:/posts.html";
            } else {
                model.put("errorLogin", "Sai tài khoản hoặc mật khẩu");
                return "login.html";
            }

        }
    }



    //hiển thị danh sách bài viết theo tentaikhoan
    @GetMapping(value = {"posts.html"})
    public ModelAndView listBaiViet() {
        if (taiKhoan==null||taiKhoan.getTenTaiKhoan()==null) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("errorLogin", "Vui Lòng Đăng Nhập");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        }
        List<BaiViet> baiViet = baiVietService.findAllBaiVietByTenDangNhap(taiKhoan.getTenTaiKhoan());
        ///////
        if(baiViet.size()>2) {
            BaiViet temp = new BaiViet();
            for (int i = 0; i < baiViet.size() - 1; i++) {
                System.out.println(i);
                for (int j = i; j < baiViet.size(); j++) {
                    System.out.println(j);
                    if (baiViet.get(j).getNgay().after(baiViet.get(i).getNgay())) {//nêu ngày đăng của baiViet i trước baiViet j sẽ trả về true
                        System.out.println(baiViet.get(i).getNgay());
                        temp = baiViet.get(i); //gán giá trị của baiViet j cho 1 đối tượng temp
                        baiViet.set(i, baiViet.get(j));//sắp xếp lại vị trí của baiViet i sẽ thành vị trí của baiViet j
                        baiViet.set(j, temp);// gán vị trí của baiViet j sẽ thành baiViet i bằng đối tượng temp
                    }
                }
            }
        }
        //////
        ModelAndView modelAndView = new ModelAndView("posts");
        modelAndView.addObject("baiViet", baiViet);
        modelAndView.addObject("baiViet1", new BaiViet());
        modelAndView.addObject("taiKhoan", taiKhoan);
        modelAndView.addObject("islogin",islogin);
        return modelAndView;
    }

    //hiển thị thông tin cá nhân
    @GetMapping(value = {"user.html"})
    public ModelAndView viewInfo() {
        if (taiKhoan==null||taiKhoan.getTenTaiKhoan()==null) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("errorLogin", "Vui Lòng Đăng Nhập");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("taiKhoan", taiKhoan);
        modelAndView.addObject("islogin",islogin);
        return modelAndView;
    }

    //tạo bài viết
    @GetMapping("create-post.html")
    private ModelAndView showTaoBaiViet() {

        if (taiKhoan==null||taiKhoan.getTenTaiKhoan()==null) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("errorLogin", "Vui Lòng Đăng Nhập");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        }

        ModelAndView modelAndView = new ModelAndView("create-post");
        BaiViet baiViet = new BaiViet();
        System.out.println(baiViet.getNgay());
        modelAndView.addObject("baiViet", baiViet);
        modelAndView.addObject("islogin",islogin);
        return modelAndView;
    }

    @PostMapping("create-post.html")
    private ModelAndView taoBaiViet(@ModelAttribute("baiViet") BaiViet baiViet) {
        System.out.println(baiViet.getNgay());
        if (!nhatKyValidate.checkSize(1, 50, baiViet.getTieuDe().length())) {
            ModelAndView modelAndView = new ModelAndView("create-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("message", "Độ dài tiêu đề từ 1-50 ký tự");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        } else if (!nhatKyValidate.checkSize(1, 50, baiViet.getMoTa().length())) {
            ModelAndView modelAndView = new ModelAndView("create-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("message", "Độ dài mô tả từ 1-50 ký tự");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        } else if (!nhatKyValidate.checkSize(1, 5000, baiViet.getNoiDung().length())) {
            ModelAndView modelAndView = new ModelAndView("create-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("message", "Độ dài nội dung từ 1-5000 ký tự");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        } else if (!nhatKyValidate.checkSize(1, 500, baiViet.getAnh().length())) {
            ModelAndView modelAndView = new ModelAndView("create-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("message", "Độ dài đường dẫn ảnh từ 1-500 ký tự");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        } else
            baiViet.setTenTaiKhoan(taiKhoan.getTenTaiKhoan());
        if(baiViet.isTrangThai()){
            System.out.println(baiViet.isTrangThai());
        }
        baiVietService.save(baiViet);
        ModelAndView modelAndView = new ModelAndView("create-post");
        modelAndView.addObject("baiViet", new BaiViet());
        modelAndView.addObject("message", "Thêm bài viết thành công !");
        modelAndView.addObject("islogin",islogin);
        return modelAndView;
    }

    //Xóa bài viết
    Optional<BaiViet> baiViet1 = Optional.of(new BaiViet());

    @GetMapping("/delete-post/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        if (taiKhoan==null||taiKhoan.getTenTaiKhoan()==null) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("errorLogin", "Vui Lòng Đăng Nhập");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        }
        baiViet1 = baiVietService.findById(id);
        if (baiViet1 != null) {
            ModelAndView modelAndView = new ModelAndView("delete-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("islogin",islogin);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/404");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        }
    }

    @PostMapping("delete-post")
    public String deletePost() {
        System.out.println(baiViet1.get().getMaBaiViet());
        baiVietService.remove(baiViet1.get().getMaBaiViet());
        return "redirect:posts.html";
    }

    //Sửa bài viết
    Long idpost;

    @GetMapping("/edit-post/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        if (taiKhoan==null||taiKhoan.getTenTaiKhoan()==null) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("islogin",islogin);
            modelAndView.addObject("errorLogin", "Vui Lòng Đăng Nhập");
            return modelAndView;
        }
        baiViet1 = baiVietService.findById(id);
        idpost = baiViet1.get().getMaBaiViet();
        System.out.println(idpost);
        if (baiViet1 != null) {
            ModelAndView modelAndView = new ModelAndView("edit-post");
            modelAndView.addObject("baiViet", baiViet1);
            modelAndView.addObject("islogin",islogin);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/404");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        }
    }

    @PostMapping("/edit-post.html")
    public ModelAndView updateProvince(@ModelAttribute("baiViet") BaiViet baiViet) {
        baiViet.setTenTaiKhoan(taiKhoan.getTenTaiKhoan());

        baiViet.setMaBaiViet(idpost);
        if (!nhatKyValidate.checkSize(1, 50, baiViet.getTieuDe().length())) {
            ModelAndView modelAndView = new ModelAndView("edit-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("message", "Độ dài tiêu đề từ 1-50 ký tự");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        } else if (!nhatKyValidate.checkSize(1, 50, baiViet.getMoTa().length())) {
            ModelAndView modelAndView = new ModelAndView("edit-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("message", "Độ dài mô tả từ 1-50 ký tự");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        } else if (!nhatKyValidate.checkSize(1, 500, baiViet.getNoiDung().length())) {
            ModelAndView modelAndView = new ModelAndView("edit-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("message", "Độ dài nội dung từ 1-500 ký tự");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        } else if (!nhatKyValidate.checkSize(1, 500, baiViet.getAnh().length())) {
            ModelAndView modelAndView = new ModelAndView("edit-post");
            modelAndView.addObject("baiViet", new BaiViet());
            modelAndView.addObject("message", "Độ dài đường dẫn ảnh từ 1-500 ký tự");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        } else
            baiVietService.save(baiViet);
        baiVietService.remove(idpost);
        ModelAndView modelAndView = new ModelAndView("edit-post");
        modelAndView.addObject("baiViet", baiViet);
        modelAndView.addObject("message", "Sửa Bài Viết Thành Công");
        modelAndView.addObject("islogin",islogin);
        return modelAndView;
    }

    //view bài biết
    @GetMapping("/post/{id}")
    public ModelAndView viewPost(@PathVariable Long id) {
        Optional<BaiViet> baiViet = baiVietService.findById(id);
        if (baiViet != null) {
            ModelAndView modelAndView = new ModelAndView("post");
            modelAndView.addObject("baiViet", baiViet);
            modelAndView.addObject("islogin",islogin);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/404");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        }
    }
    @GetMapping("/post-public/{id}")
    public ModelAndView viewPostPublic(@PathVariable Long id) {
        Optional<BaiViet> baiViet = baiVietService.findById(id);
        if (baiViet != null) {
            ModelAndView modelAndView = new ModelAndView("post-public");
            modelAndView.addObject("baiViet", baiViet);
            modelAndView.addObject("islogin",islogin);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/404");
            modelAndView.addObject("islogin",islogin);
            return modelAndView;
        }
    }

    //Đăng Xuất
    @GetMapping(value = {"logout.html"})
    public ModelAndView logout(ModelMap model) {

        taiKhoan = null;
        ModelAndView modelAndView = new ModelAndView("login");
        model.put("errorLogin", "Đã Đăng Xuất");
        return modelAndView;
    }

    //đổi mật khẩu
    @GetMapping(value = {"change-pass.html"})
    private String changePass(ModelMap model) {
        if (taiKhoan==null||taiKhoan.getTenTaiKhoan()==null) {
            model.put("errorLogin", null);
            return "login.html";
        }
        model.put("errorChangePass", null);
        return "change-pass.html";
    }

    @PostMapping("change-pass.html")
    private String changePass(@RequestParam("matKhauCu") String matKhauCu, @RequestParam("matKhauMoi") String matKhauMoi, @RequestParam("nhapLai") String nhapLai, ModelMap model) {
        if (!matKhauCu.equals(taiKhoan.getMatKhau())) {
            model.put("errorChangePass", "Mật Khẩu Cũ Sai");
            return "change-pass.html";
        } else if (!matKhauMoi.equals(nhapLai)) {
            model.put("errorChangePass", "Mật Khẩu Nhập Lại Phải Giống Mật Khẩu Cũ");
            return "change-pass.html";
        } else
            taiKhoan.setMatKhau(matKhauMoi);
        taiKhoanService.save(taiKhoan);
        model.put("errorChangePass", "Đổi Mật Khẩu Thành Công");
        return "change-pass.html";

    }

    //đăng ký
    @GetMapping(value = {"registration.html"})
    private ModelAndView showRegistration() {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("islogin",islogin);
        modelAndView.addObject("taiKhoan", new TaiKhoan());
        return modelAndView;
    }

    @PostMapping("registration.html")
    private ModelAndView registration(@ModelAttribute("taiKhoan") TaiKhoan taiKhoan) {
        System.out.println(taiKhoan.toString());
        taiKhoanService.save(taiKhoan);
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("taiKhoan", new TaiKhoan());
        modelAndView.addObject("errorLogin", "Dang Ky Thanh Cong !");
        return modelAndView;
    }

//    tìm kiếm theo tên bài viết
    @PostMapping("search.html")
    private ModelAndView search(@ModelAttribute("baiViet1") BaiViet baiViet) {
        System.out.println(baiViet.getTieuDe());
        List<BaiViet> baiViet1 = baiVietService.findAllBaiVietByTenTieuDe(baiViet.getTieuDe());
        ModelAndView modelAndView = new ModelAndView("posts");
        modelAndView.addObject("baiViet", baiViet1);
        modelAndView.addObject("islogin",islogin);
        modelAndView.addObject("taiKhoan", taiKhoan);
        return modelAndView;

    }

    public List<BaiViet> top3post(){
            List<BaiViet> top3 = new ArrayList<>();//tạo mảng chứa 3 bài viết mới nhất
        if(baiViet.size()>3) {
            top3.add(baiViet.get(0));//add vào mảng 3 bài viết mới nhất(được sắp xếp từ mảng baiViet khi)
            top3.add(baiViet.get(1));
            top3.add(baiViet.get(2));
        }
        return top3;
    }
    List<BaiViet> baiViet =new ArrayList<>();
    // bài viết công khai
    @GetMapping(value = {"index.html","/"})
    public ModelAndView listBaiVietCongKhai() {

        baiViet = baiVietService.findAllBaiVietByTrangThai();
        ///////
        if(baiViet.size()>2) {
            BaiViet temp = new BaiViet();
            for (int i = 0; i < baiViet.size() - 1; i++) {
                System.out.println(i);
                for (int j = i; j < baiViet.size(); j++) {
                    System.out.println(j);
                    if (baiViet.get(j).getNgay().after(baiViet.get(i).getNgay())) {//nêu ngày đăng của baiViet i trước baiViet j sẽ trả về true
                        System.out.println(baiViet.get(i).getNgay());
                        temp = baiViet.get(i); //gán giá trị của baiViet j cho 1 đối tượng temp
                        baiViet.set(i, baiViet.get(j));//sắp xếp lại vị trí của baiViet i sẽ thành vị trí của baiViet j
                        baiViet.set(j, temp);// gán vị trí của baiViet j sẽ thành baiViet i bằng đối tượng temp
                    }
                }
            }
        }
        //////
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("taiKhoan", taiKhoan);
        modelAndView.addObject("baiViet", baiViet);
        modelAndView.addObject("islogin",islogin);
        modelAndView.addObject("baiViet1", new BaiViet());
        return modelAndView;
    }


}

