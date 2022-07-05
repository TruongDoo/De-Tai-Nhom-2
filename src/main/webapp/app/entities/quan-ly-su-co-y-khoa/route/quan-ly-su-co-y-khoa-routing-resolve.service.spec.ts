import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IQuanLySuCoYKhoa, QuanLySuCoYKhoa } from '../quan-ly-su-co-y-khoa.model';
import { QuanLySuCoYKhoaService } from '../service/quan-ly-su-co-y-khoa.service';

import { QuanLySuCoYKhoaRoutingResolveService } from './quan-ly-su-co-y-khoa-routing-resolve.service';

describe('QuanLySuCoYKhoa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: QuanLySuCoYKhoaRoutingResolveService;
  let service: QuanLySuCoYKhoaService;
  let resultQuanLySuCoYKhoa: IQuanLySuCoYKhoa | undefined;

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
    routingResolveService = TestBed.inject(QuanLySuCoYKhoaRoutingResolveService);
    service = TestBed.inject(QuanLySuCoYKhoaService);
    resultQuanLySuCoYKhoa = undefined;
  });

  describe('resolve', () => {
    it('should return IQuanLySuCoYKhoa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQuanLySuCoYKhoa = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultQuanLySuCoYKhoa).toEqual({ id: 123 });
    });

    it('should return new IQuanLySuCoYKhoa if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQuanLySuCoYKhoa = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultQuanLySuCoYKhoa).toEqual(new QuanLySuCoYKhoa());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as QuanLySuCoYKhoa })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultQuanLySuCoYKhoa = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultQuanLySuCoYKhoa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
