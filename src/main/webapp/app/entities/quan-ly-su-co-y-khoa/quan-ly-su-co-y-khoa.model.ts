import dayjs from 'dayjs/esm';
import { IThongTinSuCo } from 'app/entities/thong-tin-su-co/thong-tin-su-co.model';
import { IThongTinNguoiBenh } from 'app/entities/thong-tin-nguoi-benh/thong-tin-nguoi-benh.model';
import { INhanVien } from 'app/entities/nhan-vien/nhan-vien.model';
import { IHoSo } from 'app/entities/ho-so/ho-so.model';

export interface IQuanLySuCoYKhoa {
  id?: number;
  maSoSuCo?: string;
  ngayBaoCao?: dayjs.Dayjs | null;
  donViBaoCao?: string | null;
  soThuTu?: string | null;
  tenSuCo?: string | null;
  nhomSuCo?: string | null;
  mucDoSuCo?: string | null;
  thongTinSuCo?: IThongTinSuCo | null;
  thongTinNguoiBenh?: IThongTinNguoiBenh | null;
  quanLySuCoYKhoa?: INhanVien | null;
  hoSos?: IHoSo[] | null;
}

export class QuanLySuCoYKhoa implements IQuanLySuCoYKhoa {
  constructor(
    public id?: number,
    public maSoSuCo?: string,
    public ngayBaoCao?: dayjs.Dayjs | null,
    public donViBaoCao?: string | null,
    public soThuTu?: string | null,
    public tenSuCo?: string | null,
    public nhomSuCo?: string | null,
    public mucDoSuCo?: string | null,
    public thongTinSuCo?: IThongTinSuCo | null,
    public thongTinNguoiBenh?: IThongTinNguoiBenh | null,
    public quanLySuCoYKhoa?: INhanVien | null,
    public hoSos?: IHoSo[] | null
  ) {}
}

export function getQuanLySuCoYKhoaIdentifier(quanLySuCoYKhoa: IQuanLySuCoYKhoa): number | undefined {
  return quanLySuCoYKhoa.id;
}
