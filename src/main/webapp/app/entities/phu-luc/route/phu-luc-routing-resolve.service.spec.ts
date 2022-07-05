import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IPhuLuc, PhuLuc } from '../phu-luc.model';
import { PhuLucService } from '../service/phu-luc.service';

import { PhuLucRoutingResolveService } from './phu-luc-routing-resolve.service';

describe('PhuLuc routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: PhuLucRoutingResolveService;
  let service: PhuLucService;
  let resultPhuLuc: IPhuLuc | undefined;

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
    routingResolveService = TestBed.inject(PhuLucRoutingResolveService);
    service = TestBed.inject(PhuLucService);
    resultPhuLuc = undefined;
  });

  describe('resolve', () => {
    it('should return IPhuLuc returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPhuLuc = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPhuLuc).toEqual({ id: 123 });
    });

    it('should return new IPhuLuc if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPhuLuc = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPhuLuc).toEqual(new PhuLuc());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as PhuLuc })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultPhuLuc = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultPhuLuc).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
