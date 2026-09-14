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
