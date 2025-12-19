package com.example.proj_ecom_mobile.database;

import android.util.Log;
import com.example.proj_ecom_mobile.model.Product;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;

public class Seeder {

    public static void seedProductData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        List<Product> products = new ArrayList<>();

        // ==================================================================
        // 1. NHÓM ÁO (TOPS) - Sơ mi, Thun, Polo, Len
        // ==================================================================

        // Dữ liệu cũ của bạn (Giữ lại để không mất công)
        products.add(new Product("p001", "Áo Sơ Mi Poplin Tay Ngắn", 650000, "Chất liệu vải Poplin thoáng mát, phom dáng rộng rãi thoải mái cho ngày hè.", "https://product.hstatic.net/200000911315/product/11_f9ad351033184b4a9045b6987f1df2cf.png", "Ao"));
        products.add(new Product("p002", "Áo Sơ Mi Đỏ Thẫm Relaxed", 650000, "Thiết kế tối giản với tông màu đỏ thẫm sang trọng, phù hợp đi làm và đi chơi.", "https://product.hstatic.net/200000911315/product/15_3fa92f3bd913427f82f2ecd342044589.jpg", "Ao"));
        products.add(new Product("p017", "Áo Len Dệt Kim Khóa Kéo", 950000, "Áo khoác len nhẹ có khóa kéo tiện lợi, phong cách hiện đại.", "https://static.massimodutti.net/assets/public/9632/424a/d3a64aa3bdeb/a020e3dbe718/01088238775-o1/01088238775-o1.jpg?ts=1759322753170&w=undefined&f=auto", "Ao"));
        products.add(new Product("p019", "Áo Thun Cổ Lọ Cotton", 890000, "Chất liệu Cotton mềm mại, giữ ấm tốt, dễ phối với áo khoác.", "https://cdn.hstatic.net/products/200000911315/background_ac6079a3c4654a16aa24f6c17c8347f2.jpg", "Ao"));
        products.add(new Product("p021", "Áo Polo Dệt Kim", 790000, "Áo Polo phom dáng suông, chất liệu dệt kim cao cấp thoáng khí.", "https://product.hstatic.net/200000911315/product/12_4f4ac6dc46f74b52bff8a95755669b7d.jpg", "Ao"));

        // Dữ liệu MỚI (Lấy cảm hứng từ Gian Saigon)
        products.add(new Product("p022", "Áo Sơ Mi Linen Cổ Tàu", 720000, "Vải Linen tự nhiên thoáng mát, cổ lãnh tụ (Band collar) tinh tế.", "https://image.hm.com/assets/hm/bd/7f/bd7f64a25fac0aed14a4c66063fc384afe5cbe40.jpg?imwidth=2160", "Ao"));
        products.add(new Product("p023", "Áo Sơ Mi Oxford Xanh Nhạt", 680000, "Vải Oxford dày dặn đứng form, màu xanh pastel nhẹ nhàng.", "https://product.hstatic.net/200000911315/product/sm_95f3cfacbbd34cb291bab5ecefc88e50.jpg", "Ao"));
        products.add(new Product("p024", "Áo Thun Heavy Cotton Đen", 450000, "Áo phông định lượng cao (250gsm), không bai dão, form Boxy.", "https://cdn.hstatic.net/products/200000911315/4_8ee3f6b490d24cbebbff9aa443de4f6e.jpg", "Ao"));
        products.add(new Product("p025", "Áo Thun Heavy Cotton Trắng", 450000, "Áo phông trắng cơ bản, chất vải dày dặn, thấm hút mồ hôi.", "https://cdn.hstatic.net/products/200000911315/3_e6573260b5934759ac1c7d3af88b67dc.jpg", "Ao"));
        products.add(new Product("p026", "Áo Polo Len Cổ V", 750000, "Thiết kế cổ V không cúc (Skipper Polo), phong cách Retro.", "https://static.massimodutti.net/assets/public/e1e0/737c/da8d4529876d/a0a1e087fbc4/01064234700-o1/01064234700-o1.jpg?ts=1759322749505&w=undefined&f=auto", "Ao"));
        products.add(new Product("p027", "Áo Sơ Mi Kẻ Sọc Xanh", 690000, "Họa tiết kẻ sọc nhỏ (Stripe), vải lụa nến mềm mại.", "https://lados.vn/wp-content/uploads/2024/07/2-soc-xanh-ld8137.jpg", "Ao"));
        products.add(new Product("p028", "Áo Thun Dài Tay Waffle", 550000, "Chất vải tổ ong (Waffle) giữ nhiệt nhẹ, phom Regular.", "https://product.hstatic.net/200000911315/product/10_694a42bc44764002856d70f59ac27a03.png", "Ao"));
        products.add(new Product("p029", "Áo Sơ Mi Nhung Tăm Nâu", 780000, "Chất liệu Corduroy (Nhung tăm) ấm áp, màu nâu Vintage.", "https://product.hstatic.net/200000911315/product/5_a772c357436842d7bb137e737bc033b7.png", "Ao"));
        products.add(new Product("p030", "Áo Ghi-lê Len Vặn Thừng", 650000, "Họa tiết Cable Knit cổ điển, phối layer với sơ mi cực đẹp.", "https://sakurafashion.vn/upload/sanpham/large/76234-ao-len-gile-nu-van-thung-3.jpg", "Ao"));
        products.add(new Product("p031", "Áo Sơ Mi Denim Wash", 850000, "Chất bò giấy mềm, màu xanh chàm đã qua xử lý wash.", "https://product.hstatic.net/200000588671/product/dsc05994_4fc80e8027854991982d872e12dbee48_grande.jpg", "Ao"));
        products.add(new Product("p032", "Áo Thun Raglan Tay Lỡ", 480000, "Tay áo phối màu tương phản, phong cách thể thao.", "https://bizweb.dktcdn.net/100/399/392/products/ao-polo-nam-tay-raglan-phoi-mau-hi-ao-thun-nam-co-be-cao-cap-chat-cotton-5.jpg?v=1745288586097", "Ao"));
        products.add(new Product("p033", "Áo Sơ Mi Cuba Họa Tiết", 750000, "Cổ Cuba (Cuban collar) phóng khoáng, họa tiết nhiệt đới chìm.", "https://product.hstatic.net/200000911315/product/11_f9ad351033184b4a9045b6987f1df2cf.png", "Ao"));
        products.add(new Product("p034", "Áo Hoodie Trơn Màu Be", 890000, "Nỉ bông định lượng 350gsm, mũ trùm lớn đứng form.", "https://4menshop.com/images/thumbs/2023/04/ao-khoac-ni-tron-ak013-mau-be-15915.jpg", "Ao"));
        products.add(new Product("p035", "Áo Sweater Nỉ Da Cá Xám", 650000, "Nỉ da cá (French Terry) thoáng khí, màu xám tiêu basic.", "https://lados.vn/wp-content/uploads/2024/09/z5007947807474-f6e9c_1024x1024.jpg", "Ao"));
        products.add(new Product("p036", "Áo Tank Top Gân Tăm", 320000, "Áo ba lỗ ôm body, chất thun gân co giãn, mặc lót hoặc tập gym.", "https://product.hstatic.net/200000911315/product/5_8828200c57404a6591ae5db7f2619de7.png", "Ao"));
        products.add(new Product("p037", "Áo Sơ Mi Flannel Kẻ Caro", 720000, "Vải dạ mỏng kẻ caro đỏ đen, phong cách Grunge.", "https://product.hstatic.net/200000911315/product/4_f7927ec016824afd8f33f66d9d7708e0.png", "Ao"));
        products.add(new Product("p038", "Áo Thun Oversize In Hình", 520000, "Phom rộng, in hình Graphic nghệ thuật sau lưng.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqT298r0y29Z5k9rRbD07mPSbT5LiqhufDpw&s", "Ao"));
        products.add(new Product("p039", "Áo Polo Zip Kéo", 790000, "Cổ Polo thay cúc bằng khóa Zip kim loại hiện đại.", "https://product.hstatic.net/200000911315/product/24_fd9bce4d905842799cffaf1453e77f5c.jpg", "Ao"));
        products.add(new Product("p040", "Áo Len Cổ Tròn Màu Rêu", 850000, "Màu xanh rêu trầm, len lông cừu tổng hợp mềm mịn.", "https://4men.com.vn/thumbs/2022/11/ao-len-co-lo-tron-al008-mau-reu-21964-p.JPG", "Ao"));
        products.add(new Product("p041", "Áo Sơ Mi Lụa Đen", 880000, "Chất lụa rủ, bóng nhẹ, sang trọng cho tiệc tối.", "https://product.hstatic.net/200000911315/product/30_6c300250d0c4433c97d7dafbd49d6e68.jpg", "Ao"));
        products.add(new Product("p042", "Áo Thun Henley Cổ Nút", 490000, "Cổ áo có hàng nút dài, tay dài, chất thun gân.", "https://s3.ap-southeast-1.amazonaws.com/thegmen.vn/2023/11/1700708665961l2vue6.jpg", "Ao"));
        products.add(new Product("p043", "Áo Cardigan Mỏng Mùa Thu", 750000, "Len mỏng nhẹ, khoác ngoài tiện lợi khi trời se lạnh.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm1SpZ_CY-v3AYGPL1j8P9YUQx6CnnOP4kMw&s", "Ao"));
        products.add(new Product("p044", "Áo Sơ Mi Overshirt Kaki", 950000, "Vải Kaki dày, có thể mặc như áo khoác sơ mi.", "https://yame.vn/cdn/shop/files/ao-s-mi-no-style-m64-xam-1174878715.jpg?v=1760790394", "Ao"));
        products.add(new Product("p045", "Áo Thun Polo Sọc Ngang", 550000, "Họa tiết kẻ ngang nhỏ, phong cách trẻ trung.", "https://4men.com.vn/thumbs/2021/05/ao-polo-soc-ngang-mau-xanh-po038-19566-p.jpg", "Ao"));

        // ==================================================================
        // 2. NHÓM QUẦN (BOTTOMS) - Tây, Jeans, Short, Kaki
        // ==================================================================

        // Dữ liệu cũ
        products.add(new Product("p003", "Quần Tây Ống Suông Nâu", 850000, "Quần âu ống suông lịch lãm, chất liệu vải pha len mềm rủ.", "https://via.placeholder.com/300?text=Quan+Tay+Nau", "Quan"));
        products.add(new Product("p004", "Quần Xếp Ly Ống Rộng", 890000, "Thiết kế xếp ly tinh tế, tạo độ phồng tự nhiên và thoải mái khi di chuyển.", "https://via.placeholder.com/300?text=Quan+Xep+Ly", "Quan"));
        products.add(new Product("p006", "Quần Jeans Ống Đứng", 890000, "Quần Jeans màu xanh cổ điển, phom đứng tôn dáng.", "https://via.placeholder.com/300?text=Quan+Jeans", "Quan"));
        products.add(new Product("p010", "Quần Short Bermuda", 750000, "Quần short dài ngang gối, phong cách trẻ trung năng động.", "https://via.placeholder.com/300?text=Quan+Short", "Quan"));
        products.add(new Product("p015", "Quần Jeans Màu Kem", 890000, "Màu kem nhã nhặn, dễ dàng phối với các loại áo tối màu.", "https://via.placeholder.com/300?text=Jeans+Kem", "Quan"));

        // Dữ liệu MỚI
        products.add(new Product("p046", "Quần Tây Gurkha Cạp Cao", 980000, "Thiết kế đai lưng Gurkha (không cần thắt lưng), cạp cao tôn dáng.", "https://via.placeholder.com/300?text=Gurkha", "Quan"));
        products.add(new Product("p047", "Quần Chinos Màu Be", 750000, "Vải Kaki co giãn nhẹ, phom Slim-fit gọn gàng.", "https://via.placeholder.com/300?text=Chinos+Be", "Quan"));
        products.add(new Product("p048", "Quần Jeans Đen Rách Gối", 920000, "Màu đen tuyền, chi tiết rách gối bụi bặm (Streetwear).", "https://via.placeholder.com/300?text=Jeans+Rach", "Quan"));
        products.add(new Product("p049", "Quần Baggy Jeans Xanh Nhạt", 850000, "Màu Wash sáng, ống rộng phần đùi, bo nhẹ gấu.", "https://via.placeholder.com/300?text=Baggy+Jeans", "Quan"));
        products.add(new Product("p050", "Quần Short Tây Xếp Ly", 650000, "Quần đùi vải tây, có xếp ly 2 bên (Double Pleated).", "https://via.placeholder.com/300?text=Short+Tay", "Quan"));
        products.add(new Product("p051", "Quần Cargo Túi Hộp Đen", 950000, "Phong cách quân đội (Utility), nhiều túi hộp tiện lợi.", "https://via.placeholder.com/300?text=Cargo+Den", "Quan"));
        products.add(new Product("p052", "Quần Jogger Nỉ Bo Gấu", 590000, "Quần nỉ thể thao, bo gấu, thích hợp mặc nhà hoặc tập gym.", "https://via.placeholder.com/300?text=Jogger", "Quan"));
        products.add(new Product("p053", "Quần Linen Dây Rút Trắng", 720000, "Vải đũi trắng tinh khiết, lưng thun dây rút thoải mái đi biển.", "https://via.placeholder.com/300?text=Linen+Trang", "Quan"));
        products.add(new Product("p054", "Quần Tây Ống Loe Nhẹ", 880000, "Thiết kế Flared loe nhẹ phần gấu, phong cách thập niên 70.", "https://via.placeholder.com/300?text=Quan+Loe", "Quan"));
        products.add(new Product("p055", "Quần Short Kaki Túi Sau", 550000, "Thiết kế cơ bản, dài trên gối, màu xanh Navy.", "https://via.placeholder.com/300?text=Short+Kaki", "Quan"));
        products.add(new Product("p056", "Quần Jeans Raw Denim", 1200000, "Vải bò thô chưa qua xử lý, form cứng cáp, màu chàm đậm.", "https://via.placeholder.com/300?text=Raw+Denim", "Quan"));
        products.add(new Product("p057", "Quần Tây Side Tab Xám", 890000, "Điều chỉnh eo bằng đai hông (Side tab), màu xám ghi.", "https://via.placeholder.com/300?text=Side+Tab", "Quan"));
        products.add(new Product("p058", "Quần Nhung Tăm Ống Suông", 820000, "Vải Corduroy sọc dọc, màu nâu đất ấm áp.", "https://via.placeholder.com/300?text=Quan+Nhung", "Quan"));
        products.add(new Product("p059", "Quần Short Thun Nỉ", 450000, "Chất liệu nỉ da cá, lưng thun, mặc nhà cực êm.", "https://via.placeholder.com/300?text=Short+Ni", "Quan"));
        products.add(new Product("p060", "Quần Kaki Túi Mổ", 780000, "Túi sau dạng mổ viền, form Straight đứng dáng.", "https://via.placeholder.com/300?text=Kaki+Tui+Mo", "Quan"));
        products.add(new Product("p061", "Quần Jeans Trắng", 890000, "Màu trắng ngà (Off-white), phối chỉ nổi màu nâu.", "https://via.placeholder.com/300?text=Jeans+Trang", "Quan"));
        products.add(new Product("p062", "Quần Tây Caro Prince of Wales", 950000, "Họa tiết kẻ caro xám cổ điển Anh Quốc.", "https://via.placeholder.com/300?text=Quan+Caro", "Quan"));
        products.add(new Product("p063", "Quần Short Dù Đi Biển", 390000, "Vải dù mau khô, họa tiết nhiệt đới rực rỡ.", "https://via.placeholder.com/300?text=Short+Du", "Quan"));
        products.add(new Product("p064", "Quần Tây Ống Côn (Tapered)", 850000, "Rộng ở đùi, thu nhỏ dần về phía gấu quần.", "https://via.placeholder.com/300?text=Quan+Con", "Quan"));
        products.add(new Product("p065", "Quần Yếm Denim (Overalls)", 1100000, "Phong cách Workwear, yếm bò màu xanh đậm.", "https://via.placeholder.com/300?text=Quan+Yem", "Quan"));

        // ==================================================================
        // 3. NHÓM ÁO KHOÁC (OUTERWEAR) - Blazer, Jacket, Mangto
        // ==================================================================

        // Dữ liệu cũ
        products.add(new Product("p005", "Áo Khoác Dạ Dáng Rộng", 980000, "Áo khoác dạ phom rộng thời thượng, giữ ấm tuyệt đối.", "https://via.placeholder.com/300?text=Ao+Khoac+Da", "AoKhoac"));
        products.add(new Product("p007", "Áo Mang-to Hai Hàng Khuy", 3890000, "Áo choàng dài cổ điển, điểm nhấn sang trọng cho mùa đông.", "https://via.placeholder.com/300?text=Mang+To", "AoKhoac"));
        products.add(new Product("p009", "Áo Khoác An Nam", 1090000, "Lấy cảm hứng từ trang phục truyền thống, cách tân hiện đại.", "https://via.placeholder.com/300?text=Ao+Khoac+An+Nam", "AoKhoac"));
        products.add(new Product("p013", "Áo Khoác Lửng Cổ Da Lộn", 980000, "Dáng áo ngắn (cropped) giúp 'hack' chiều cao hiệu quả.", "https://via.placeholder.com/300?text=Ao+Khoac+Lung", "AoKhoac"));
        products.add(new Product("p014", "Áo Khoác Denim Workwear", 980000, "Chất liệu Denim bền bỉ, màu xanh Cerulean lạ mắt.", "https://via.placeholder.com/300?text=Ao+Khoac+Jean", "AoKhoac"));
        products.add(new Product("p018", "Áo Blazer Oversized Nâu", 3490000, "Blazer phom rộng phóng khoáng, phong cách Hàn Quốc.", "https://via.placeholder.com/300?text=Blazer+Nau", "AoKhoac"));
        products.add(new Product("p020", "Áo Khoác Dù Có Mũ", 2450000, "Chất liệu Nylon chống thấm nước nhẹ, tiện lợi khi đi mưa phùn.", "https://via.placeholder.com/300?text=Ao+Khoac+Du", "AoKhoac"));

        // Dữ liệu MỚI
        products.add(new Product("p066", "Áo Blazer Đen Basic", 2500000, "Thiết kế 2 cúc cơ bản, đệm vai mỏng, dễ phối đồ.", "https://via.placeholder.com/300?text=Blazer+Den", "AoKhoac"));
        products.add(new Product("p067", "Áo Bomber Da Lộn", 1500000, "Da lộn nhân tạo (Suede) mềm mịn, bo chun cổ tay.", "https://via.placeholder.com/300?text=Bomber+Da", "AoKhoac"));
        products.add(new Product("p068", "Áo Khoác Harrington Be", 1200000, "Cổ đứng, lót kẻ caro bên trong, phong cách Anh Quốc.", "https://via.placeholder.com/300?text=Harrington", "AoKhoac"));
        products.add(new Product("p069", "Áo Khoác Gió Phối Màu", 850000, "Chất liệu dù mỏng nhẹ, phối mảng màu Retro 90s.", "https://via.placeholder.com/300?text=Khoac+Gio", "AoKhoac"));
        products.add(new Product("p070", "Áo Varsity Jacket", 1350000, "Áo khoác bóng chày, tay da PU, thân nỉ bông.", "https://via.placeholder.com/300?text=Varsity", "AoKhoac"));
        products.add(new Product("p071", "Áo Khoác Trucker Kaki", 950000, "Dáng áo xe tải (Trucker) khỏe khoắn, vải Kaki dày.", "https://via.placeholder.com/300?text=Trucker", "AoKhoac"));
        products.add(new Product("p072", "Áo Gile Phao Trần Bông", 680000, "Giữ ấm thân mình, cổ cao, thích hợp mặc lót trong.", "https://via.placeholder.com/300?text=Gile+Phao", "AoKhoac"));
        products.add(new Product("p073", "Áo Khoác Kimono Cách Tân", 1100000, "Dáng áo Nhật Bản, buộc dây eo, chất vải đũi.", "https://via.placeholder.com/300?text=Kimono", "AoKhoac"));
        products.add(new Product("p074", "Áo Vest Kẻ Sọc Dọc", 2800000, "Họa tiết Pinstripe sọc dọc mảnh, tôn chiều cao.", "https://via.placeholder.com/300?text=Vest+Ke", "AoKhoac"));
        products.add(new Product("p075", "Áo Khoác Lông Cừu (Fleece)", 990000, "Chất liệu lông cừu nhân tạo xù nhẹ, cực ấm.", "https://via.placeholder.com/300?text=Fleece", "AoKhoac"));
        products.add(new Product("p076", "Áo Măng Tô Kaki Dáng Ngắn", 1800000, "Trench coat dáng lửng ngang đùi, màu be cổ điển.", "https://via.placeholder.com/300?text=Mangto+Ngan", "AoKhoac"));
        products.add(new Product("p077", "Áo Khoác Da Biker", 3500000, "Da thật, nhiều khóa kéo kim loại, phong cách ngầu.", "https://via.placeholder.com/300?text=Biker+Jacket", "AoKhoac"));
        products.add(new Product("p078", "Áo Khoác Safari Túi Hộp", 1250000, "4 túi hộp phía trước, có đai thắt eo, màu rêu.", "https://via.placeholder.com/300?text=Safari", "AoKhoac"));
        products.add(new Product("p079", "Áo Cardigan Dệt Kim Dày", 1050000, "Len dày vặn thừng, cúc gỗ, phong cách Đà Lạt.", "https://via.placeholder.com/300?text=Cardigan+Day", "AoKhoac"));
        products.add(new Product("p080", "Áo Khoác Coach Jacket", 850000, "Dáng áo huấn luyện viên, cúc bấm, in chữ sau lưng.", "https://via.placeholder.com/300?text=Coach", "AoKhoac"));

        // ==================================================================
        // 4. NHÓM PHỤ KIỆN (ACCESSORIES) - Túi, Ví, Thắt lưng
        // ==================================================================

        // Dữ liệu cũ
        products.add(new Product("p011", "Túi Tote Da Lộn", 890000, "Túi xách cỡ lớn đựng vừa laptop, chất liệu da lộn mềm.", "https://via.placeholder.com/300?text=Tui+Tote", "PhuKien"));
        products.add(new Product("p012", "Ví Đựng Thẻ Da Bò", 250000, "Nhỏ gọn, tiện lợi, làm từ da bò thật màu đỏ rượu.", "https://via.placeholder.com/300?text=Vi+Da", "PhuKien"));
        products.add(new Product("p016", "Thắt Lưng Da Cổ Điển", 650000, "Phụ kiện không thể thiếu cho quý ông, khóa kim loại không gỉ.", "https://via.placeholder.com/300?text=That+Lung", "PhuKien"));

        // Dữ liệu MỚI
        products.add(new Product("p081", "Túi Đeo Chéo Canvas", 450000, "Vải bố Canvas bền bỉ, quai đeo bản to.", "https://via.placeholder.com/300?text=Tui+Cheo", "PhuKien"));
        products.add(new Product("p082", "Balo Laptop Da PU", 1200000, "Da tổng hợp cao cấp, chống nước, đựng laptop 15 inch.", "https://via.placeholder.com/300?text=Balo+Da", "PhuKien"));
        products.add(new Product("p083", "Mũ Bucket Vải Dù", 320000, "Mũ tai bèo vành nhỏ, màu đen trơn.", "https://via.placeholder.com/300?text=Mu+Bucket", "PhuKien"));
        products.add(new Product("p084", "Mũ Lưỡi Trai Logo Thêu", 350000, "Nón kết Kaki, thêu logo chữ ký nhỏ tinh tế.", "https://via.placeholder.com/300?text=Mu+Ket", "PhuKien"));
        products.add(new Product("p085", "Tất Cổ Cao Trắng (Set 5)", 200000, "Cotton 100% thấm hút tốt, cổ cao thời trang.", "https://via.placeholder.com/300?text=Tat+Trang", "PhuKien"));
        products.add(new Product("p086", "Vòng Tay Bạc Cuff", 850000, "Vòng tay bạc dạng hở, thiết kế tối giản.", "https://via.placeholder.com/300?text=Vong+Tay", "PhuKien"));
        products.add(new Product("p087", "Dây Chuyền Bạc Mặt Tròn", 950000, "Dây xích mảnh, mặt dây chuyền hình đồng xu.", "https://via.placeholder.com/300?text=Day+Chuyen", "PhuKien"));
        products.add(new Product("p088", "Kính Mát Gọng Vuông", 550000, "Gọng nhựa Acetate đen bóng, tròng chống tia UV.", "https://via.placeholder.com/300?text=Kinh+Mat", "PhuKien"));
        products.add(new Product("p089", "Cà Vạt Lụa Bản Nhỏ", 250000, "Cà vạt đen trơn bản 5cm, phong cách Hàn Quốc.", "https://via.placeholder.com/300?text=Ca+Vat", "PhuKien"));
        products.add(new Product("p090", "Khăn Choàng Len Mùa Đông", 450000, "Len cashmere mềm, màu xám ghi trung tính.", "https://via.placeholder.com/300?text=Khan+Len", "PhuKien"));
        products.add(new Product("p091", "Túi Du Lịch Duffle", 1500000, "Túi trống cỡ lớn, da bò sáp, dùng cho chuyến đi 3 ngày.", "https://via.placeholder.com/300?text=Tui+Duffle", "PhuKien"));
        products.add(new Product("p092", "Giày Loafer Da Bóng", 1800000, "Giày lười da bóng (Penny Loafer), đế cao 3cm.", "https://via.placeholder.com/300?text=Loafer", "PhuKien"));
        products.add(new Product("p093", "Dép Sandal Quai Hậu", 850000, "Dép xăng-đan đế trấu hoặc cao su, quai da.", "https://via.placeholder.com/300?text=Sandal", "PhuKien"));
        products.add(new Product("p094", "Mũ Len Beanie", 250000, "Mũ len dệt kim ôm đầu, giữ ấm tai.", "https://via.placeholder.com/300?text=Beanie", "PhuKien"));
        products.add(new Product("p095", "Thắt Lưng Da Nâu Bản To", 680000, "Da bò nguyên miếng màu nâu, khóa đồng đúc.", "https://via.placeholder.com/300?text=Day+Nit+Nau", "PhuKien"));
        products.add(new Product("p096", "Ví Cầm Tay Clutch", 1100000, "Ví dài cầm tay, đựng vừa điện thoại và giấy tờ.", "https://via.placeholder.com/300?text=Clutch", "PhuKien"));
        products.add(new Product("p097", "Găng Tay Da Cảm Ứng", 450000, "Găng tay da lót nỉ, đầu ngón tay dùng được điện thoại.", "https://via.placeholder.com/300?text=Gang+Tay", "PhuKien"));
        products.add(new Product("p098", "Túi Bao Tử (Bum Bag)", 380000, "Đeo hông hoặc đeo chéo ngực, vải dù chống nước.", "https://via.placeholder.com/300?text=Bum+Bag", "PhuKien"));
        products.add(new Product("p099", "Dây Đeo Thẻ Da", 150000, "Dây đeo thẻ nhân viên bằng da, màu sắc trang nhã.", "https://via.placeholder.com/300?text=Day+The", "PhuKien"));
        products.add(new Product("p100", "Móc Khóa Da Handmade", 90000, "Móc khóa da bò dập tên, phụ kiện nhỏ xinh.", "https://via.placeholder.com/300?text=Moc+Khoa", "PhuKien"));


        // Vòng lặp đẩy lên Firebase (Giữ nguyên logic cũ của bạn)
        for (Product p : products) {
            db.collection("products").document(p.getId()).set(p)
                    .addOnSuccessListener(aVoid -> Log.d("Seeder", "Đã thêm: " + p.getName()))
                    .addOnFailureListener(e -> Log.e("Seeder", "Lỗi: " + e.getMessage()));
        }
    }
}