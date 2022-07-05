import dayjs from 'dayjs/esm';
import { IQuanLySuCoYKhoa } from 'app/entities/quan-ly-su-co-y-khoa/quan-ly-su-co-y-khoa.model';

export interface IThongTinSuCo {
  id?: number;
  viTriXayRaSuCo?: string | null;
  ngayXayRaSuCo?: dayjs.Dayjs | null;
  thoiGian?: dayjs.Dayjs | null;
  quanLySuCoYKhoas?: IQuanLySuCoYKhoa[] | null;
}

export class ThongTinSuCo implements IThongTinSuCo {
  constructor(
    public id?: number,
    public viTriXayRaSuCo?: string | null,
    public ngayXayRaSuCo?: dayjs.Dayjs | null,
    public thoiGian?: dayjs.Dayjs | null,
    public quanLySuCoYKhoas?: IQuanLySuCoYKhoa[] | null
  ) {}
}

export function getThongTinSuCoIdentifier(thongTinSuCo: IThongTinSuCo): number | undefined {
  return thongTinSuCo.id;
}
