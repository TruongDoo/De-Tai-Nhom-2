import { IQuanLySuCoYKhoa } from 'app/entities/quan-ly-su-co-y-khoa/quan-ly-su-co-y-khoa.model';

export interface INhanVien {
  id?: number;
  chucVu?: string | null;
  hoTen?: string | null;
  email?: string | null;
  diaChi?: string | null;
  soDienThoai?: string | null;
  quanLySuCoYKhoas?: IQuanLySuCoYKhoa[] | null;
}

export class NhanVien implements INhanVien {
  constructor(
    public id?: number,
    public chucVu?: string | null,
    public hoTen?: string | null,
    public email?: string | null,
    public diaChi?: string | null,
    public soDienThoai?: string | null,
    public quanLySuCoYKhoas?: IQuanLySuCoYKhoa[] | null
  ) {}
}

export function getNhanVienIdentifier(nhanVien: INhanVien): number | undefined {
  return nhanVien.id;
}
