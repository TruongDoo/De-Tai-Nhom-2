import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'nhan-vien',
        data: { pageTitle: 'codejhipsterApp.nhanVien.home.title' },
        loadChildren: () => import('./nhan-vien/nhan-vien.module').then(m => m.NhanVienModule),
      },
      {
        path: 'thong-tin-su-co',
        data: { pageTitle: 'codejhipsterApp.thongTinSuCo.home.title' },
        loadChildren: () => import('./thong-tin-su-co/thong-tin-su-co.module').then(m => m.ThongTinSuCoModule),
      },
      {
        path: 'thong-tin-nguoi-benh',
        data: { pageTitle: 'codejhipsterApp.thongTinNguoiBenh.home.title' },
        loadChildren: () => import('./thong-tin-nguoi-benh/thong-tin-nguoi-benh.module').then(m => m.ThongTinNguoiBenhModule),
      },
      {
        path: 'quan-ly-su-co-y-khoa',
        data: { pageTitle: 'codejhipsterApp.quanLySuCoYKhoa.home.title' },
        loadChildren: () => import('./quan-ly-su-co-y-khoa/quan-ly-su-co-y-khoa.module').then(m => m.QuanLySuCoYKhoaModule),
      },
      {
        path: 'ho-so',
        data: { pageTitle: 'codejhipsterApp.hoSo.home.title' },
        loadChildren: () => import('./ho-so/ho-so.module').then(m => m.HoSoModule),
      },
      {
        path: 'phu-luc',
        data: { pageTitle: 'codejhipsterApp.phuLuc.home.title' },
        loadChildren: () => import('./phu-luc/phu-luc.module').then(m => m.PhuLucModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
