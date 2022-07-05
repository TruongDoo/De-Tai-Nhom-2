import { IHoSo } from 'app/entities/ho-so/ho-so.model';

export interface IPhuLuc {
  id?: number;
  soThuTu?: string | null;
  tenBieuMau?: string | null;
  maHieu?: string;
  hoSos?: IHoSo[] | null;
}

export class PhuLuc implements IPhuLuc {
  constructor(
    public id?: number,
    public soThuTu?: string | null,
    public tenBieuMau?: string | null,
    public maHieu?: string,
    public hoSos?: IHoSo[] | null
  ) {}
}

export function getPhuLucIdentifier(phuLuc: IPhuLuc): number | undefined {
  return phuLuc.id;
}
