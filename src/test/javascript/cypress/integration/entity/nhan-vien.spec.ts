import { entityItemSelector } from '../../support/commands';
import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('NhanVien e2e test', () => {
  const nhanVienPageUrl = '/nhan-vien';
  const nhanVienPageUrlPattern = new RegExp('/nhan-vien(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const nhanVienSample = {};

  let nhanVien: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/nhan-viens+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/nhan-viens').as('postEntityRequest');
    cy.intercept('DELETE', '/api/nhan-viens/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (nhanVien) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/nhan-viens/${nhanVien.id}`,
      }).then(() => {
        nhanVien = undefined;
      });
    }
  });

  it('NhanViens menu should load NhanViens page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('nhan-vien');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('NhanVien').should('exist');
    cy.url().should('match', nhanVienPageUrlPattern);
  });

  describe('NhanVien page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(nhanVienPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create NhanVien page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/nhan-vien/new$'));
        cy.getEntityCreateUpdateHeading('NhanVien');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nhanVienPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/nhan-viens',
          body: nhanVienSample,
        }).then(({ body }) => {
          nhanVien = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/nhan-viens+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/nhan-viens?page=0&size=20>; rel="last",<http://localhost/api/nhan-viens?page=0&size=20>; rel="first"',
              },
              body: [nhanVien],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(nhanVienPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details NhanVien page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('nhanVien');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nhanVienPageUrlPattern);
      });

      it('edit button click should load edit NhanVien page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NhanVien');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nhanVienPageUrlPattern);
      });

      it('last delete button click should delete instance of NhanVien', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('nhanVien').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', nhanVienPageUrlPattern);

        nhanVien = undefined;
      });
    });
  });

  describe('new NhanVien page', () => {
    beforeEach(() => {
      cy.visit(`${nhanVienPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('NhanVien');
    });

    it('should create an instance of NhanVien', () => {
      cy.get(`[data-cy="chucVu"]`).type('Connecticut architect Concrete').should('have.value', 'Connecticut architect Concrete');

      cy.get(`[data-cy="hoTen"]`).type('Investment Tunisian').should('have.value', 'Investment Tunisian');

      cy.get(`[data-cy="email"]`).type('Berneice.Franecki@hotmail.com').should('have.value', 'Berneice.Franecki@hotmail.com');

      cy.get(`[data-cy="diaChi"]`).type('end-to-end Hat Account').should('have.value', 'end-to-end Hat Account');

      cy.get(`[data-cy="soDienThoai"]`).type('Incredible Mouse deliverables').should('have.value', 'Incredible Mouse deliverables');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        nhanVien = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', nhanVienPageUrlPattern);
    });
  });
});
