import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IThongTinNguoiBenh, ThongTinNguoiBenh } from '../thong-tin-nguoi-benh.model';
import { ThongTinNguoiBenhService } from '../service/thong-tin-nguoi-benh.service';

import { ThongTinNguoiBenhRoutingResolveService } from './thong-tin-nguoi-benh-routing-resolve.service';

describe('ThongTinNguoiBenh routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: ThongTinNguoiBenhRoutingResolveService;
  let service: ThongTinNguoiBenhService;
  let resultThongTinNguoiBenh: IThongTinNguoiBenh | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(ThongTinNguoiBenhRoutingResolveService);
    service = TestBed.inject(ThongTinNguoiBenhService);
    resultThongTinNguoiBenh = undefined;
  });

  describe('resolve', () => {
    it('should return IThongTinNguoiBenh returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultThongTinNguoiBenh = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultThongTinNguoiBenh).toEqual({ id: 123 });
    });

    it('should return new IThongTinNguoiBenh if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultThongTinNguoiBenh = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultThongTinNguoiBenh).toEqual(new ThongTinNguoiBenh());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as ThongTinNguoiBenh })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultThongTinNguoiBenh = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultThongTinNguoiBenh).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
