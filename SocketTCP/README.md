# SocketTCP – Luồng hoạt động

## VD1 – Echo Server/Client

**Client nhập:**

```text
hello world
```

↓

**Server nhận:**

```text
hello world
```

↓

**Server chuyển thành chữ in hoa:**

```text
HELLO WORLD
```

↓

**Client nhận:**

```text
HELLO WORLD
```

---

## VD2 – Tính tổng 2 số

**Client nhập số thứ nhất:**

```text
10
```

↓

**Client nhập số thứ hai:**

```text
20
```

↓

**Server nhận:**

```text
a = 10
b = 20
```

↓

**Server tính tổng:**

```text
10 + 20 = 30
```

↓

**Client nhận:**

```text
30
```

---

## VD3 – Server đa luồng (Multi-thread Echo, dựa trên knowleage_base)

**Client A gửi:**

```text
hello
```

**Client B gửi (cùng lúc, ở terminal khác):**

```text
world
```

↓

**Server (Thread chính) chỉ accept() kết nối, không xử lý dữ liệu**

↓

**Mỗi client được giao cho 1 `ClientHandler` (Thread) riêng:**

```text
[Thread-0] nhận "hello" -> trả "HELLO"
[Thread-1] nhận "world" -> trả "WORLD"
```

↓

**Client A nhận:**

```text
HELLO
```

**Client B nhận (không cần chờ Client A xong):**

```text
WORLD
```

> Khác biệt so với VD1: VD1 xử lý tuần tự (1 client xong mới tới client tiếp theo),
> còn VD3 dùng `ClientHandler extends Thread` (lấy ý tưởng từ `knowleage_base/ClientHandler.java`)
> để mỗi client chạy trên 1 luồng riêng, phục vụ được nhiều client cùng lúc.

---

## Tổng quát

### VD1

```text
Client
  ↓
Nhập chuỗi
  ↓
Gửi chuỗi
  ↓
Server
  ↓
Nhận chuỗi
  ↓
Chuyển thành chữ hoa
  ↓
Gửi kết quả
  ↓
Client nhận kết quả
```

### VD2

```text
Client
  ↓
Nhập số 1
  ↓
Nhập số 2
  ↓
Gửi 2 số
  ↓
Server
  ↓
Nhận 2 số
  ↓
Tính tổng
  ↓
Gửi kết quả
  ↓
Client nhận kết quả
```
### VD3

```text
Server (Thread chinh)
  ↓
accept() ket noi moi (khong xu ly du lieu)
  ↓
new ClientHandler(socket).start()   <-- tao Thread rieng cho tung client
  ↓                                       ↓
Thread chinh quay lai accept()      ClientHandler doc/ghi du lieu rieng
(khong bi block)                    (chuyen chu hoa, gui ket qua)
  ↓                                       ↓
Client moi ket noi duoc ngay        ClientHandler dong socket khi client 'exit'
```