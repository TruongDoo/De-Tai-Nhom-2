import dayjs from 'dayjs/esm';
import { IQuanLySuCoYKhoa } from 'app/entities/quan-ly-su-co-y-khoa/quan-ly-su-co-y-khoa.model';
import { IPhuLuc } from 'app/entities/phu-luc/phu-luc.model';

export interface IHoSo {
  id?: number;
  soThuTu?: string | null;
  tenHoSo?: string | null;
  noiLuu?: string | null;
  thoiGianLuu?: dayjs.Dayjs | null;
  quanLySuCoYKhoa?: IQuanLySuCoYKhoa | null;
  phuLuc?: IPhuLuc | null;
}

export class HoSo implements IHoSo {
  constructor(
    public id?: number,
    public soThuTu?: string | null,
    public tenHoSo?: string | null,
    public noiLuu?: string | null,
    public thoiGianLuu?: dayjs.Dayjs | null,
    public quanLySuCoYKhoa?: IQuanLySuCoYKhoa | null,
    public phuLuc?: IPhuLuc | null
  ) {}
}

export function getHoSoIdentifier(hoSo: IHoSo): number | undefined {
  return hoSo.id;
}
