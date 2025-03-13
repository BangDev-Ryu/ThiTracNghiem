# Quản lý Thi trắc nghiệm

## Project 2025

### I. Quản lý thi trắc nghiệm

-   Người dự thi có thể đăng ký, đăng nhập và làm bài thi trắc nghiệm với nhiều topic/môn thi/bài học, xem kết quả thi.
-   Câu hỏi dạng multiple choice (có từ 2 đến 5 lựa chọn), mỗi câu hỏi được đánh giá 3 mức độ: dễ (easy - 1 điểm), trung bình (medium - 2 điểm), khó (difficult - 3 điểm). Câu hỏi, đáp án có thể là hình ảnh (qPictures, awPictures).
-   Một bài thi (Test) có cấu trúc, nội dung được lấy từ một hoặc nhiều topic bài học/lĩnh vực, được thiết lập thời gian thi (testTime), số lượng câu hỏi ở nhiều mức độ khác nhau, số lượt làm bài (num_limit).
-   Mỗi đề thi (Exams) được tổng hợp ngẫu nhiên từ bài thi (testCode) có mã đề thi khác nhau (exCode). Khi dự thi, người thi có thể chọn một mã đề để làm bài thi. Một bài thi có từ 1 đến 10 đề thi (exOrder: A, B, C, D, E, ...).

### II. Quản lý thi trắc nghiệm (Admin Functions)

-   Câu hỏi được import từ Excel.
-   Tìm kiếm câu hỏi (theo nội dung câu hỏi, ID, chủ đề...).
-   Admin có thể chỉnh sửa từng câu hỏi.
-   Thống kê kết quả thi theo bài thi, số lượng dự thi, số lượng đạt (>=50%), số lượng không đạt.
-   Tạo đề thi để in (export DOCX).
-   Admin quản lý user: import danh sách dự thi, reset password.
-   Người dự thi: đăng ký thông tin, chỉnh sửa thông tin cá nhân.

## Cơ sở dữ liệu (CSDL)

-   **Topics**: Các chủ đề/bài học.
-   **Questions**: Câu hỏi.
-   **Answers**: Các lựa chọn của câu hỏi.
-   **Test**: Cấu trúc bài thi.
-   **Exams**: Bài thi theo cấu trúc Test.
-   **Result**: Kết quả thi của users.
-   **Users**: Người dùng.
-   **Logs**: Lưu vết thao tác của người thi.

## Tables (Trường dữ liệu chính)

### `tracnghiem_questions`

-   `tplD`: int(11)
-   `qID`: int(11)
-   `tpTitle`: text
-   `qContent`: text

### `tracnghiem_test`

-   `testID`: int(11)
-   `testCode`: varchar(20)
-   `testTitle`: text
-   `testTime`: int(11)
-   `num_easy`: int(11)

### `tracnghiem_exams`

-   `exOrder`: varchar(1)
-   `exCode`: varchar(20)
-   `ex_quesIDs`: text
-   `tpID`: int(11)

### `tracnghiem_answers`

-   `awID`: int(11)
-   `awContent`: text
-   `qID`: int(11)
-   `isRight`: tinyint(4)

### `tracnghiem_users`

-   `userID`: int(11)
-   `userName`: varchar(40)
-   `userEmail`: varchar(20)
-   `userPassword`: varchar(40)
-   `userFullName`: varchar(40)
-   `isAdmin`: tinyint(4)

## Đề cương báo cáo project

-   **Trang bìa**: Ghi tên Project, các thành viên.
-   **Trang 2**: Mục lục.
-   **Trang 3**: Phân công nhiệm vụ của thành viên.
-   **Trang 4**: Chức năng 1: Quản lý user.
    -   Xử lý 1: Hiển thị danh sách.
        -   Sơ đồ tuần tự.
        -   Chụp màn hình GUI.
        -   Copy code 3 class: DAL, BLL, UI.
    -   Xử lý 2: ...
    -   Xử lý 3: ...
    -   Lặp lại cấu trúc này cho các chức năng
-   **Trang kế tiếp**: Chức năng 2: Quản lý câu hỏi.
