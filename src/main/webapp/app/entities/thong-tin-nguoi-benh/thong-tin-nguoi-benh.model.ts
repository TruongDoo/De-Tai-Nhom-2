import { IQuanLySuCoYKhoa } from 'app/entities/quan-ly-su-co-y-khoa/quan-ly-su-co-y-khoa.model';

export interface IThongTinNguoiBenh {
  id?: number;
  hoVaTen?: string | null;
  soBenhAn?: string | null;
  gioiTinh?: boolean | null;
  quanLySuCoYKhoas?: IQuanLySuCoYKhoa[] | null;
}

export class ThongTinNguoiBenh implements IThongTinNguoiBenh {
  constructor(
    public id?: number,
    public hoVaTen?: string | null,
    public soBenhAn?: string | null,
    public gioiTinh?: boolean | null,
    public quanLySuCoYKhoas?: IQuanLySuCoYKhoa[] | null
  ) {
    this.gioiTinh = this.gioiTinh ?? false;
  }
}

export function getThongTinNguoiBenhIdentifier(thongTinNguoiBenh: IThongTinNguoiBenh): number | undefined {
  return thongTinNguoiBenh.id;
}
