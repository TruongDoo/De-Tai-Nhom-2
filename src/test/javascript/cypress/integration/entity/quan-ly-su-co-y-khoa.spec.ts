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

describe('QuanLySuCoYKhoa e2e test', () => {
  const quanLySuCoYKhoaPageUrl = '/quan-ly-su-co-y-khoa';
  const quanLySuCoYKhoaPageUrlPattern = new RegExp('/quan-ly-su-co-y-khoa(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const quanLySuCoYKhoaSample = { maSoSuCo: 'grow Electronics' };

  let quanLySuCoYKhoa: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/quan-ly-su-co-y-khoas+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/quan-ly-su-co-y-khoas').as('postEntityRequest');
    cy.intercept('DELETE', '/api/quan-ly-su-co-y-khoas/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (quanLySuCoYKhoa) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/quan-ly-su-co-y-khoas/${quanLySuCoYKhoa.id}`,
      }).then(() => {
        quanLySuCoYKhoa = undefined;
      });
    }
  });

  it('QuanLySuCoYKhoas menu should load QuanLySuCoYKhoas page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('quan-ly-su-co-y-khoa');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('QuanLySuCoYKhoa').should('exist');
    cy.url().should('match', quanLySuCoYKhoaPageUrlPattern);
  });

  describe('QuanLySuCoYKhoa page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(quanLySuCoYKhoaPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create QuanLySuCoYKhoa page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/quan-ly-su-co-y-khoa/new$'));
        cy.getEntityCreateUpdateHeading('QuanLySuCoYKhoa');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', quanLySuCoYKhoaPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/quan-ly-su-co-y-khoas',
          body: quanLySuCoYKhoaSample,
        }).then(({ body }) => {
          quanLySuCoYKhoa = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/quan-ly-su-co-y-khoas+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/quan-ly-su-co-y-khoas?page=0&size=20>; rel="last",<http://localhost/api/quan-ly-su-co-y-khoas?page=0&size=20>; rel="first"',
              },
              body: [quanLySuCoYKhoa],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(quanLySuCoYKhoaPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details QuanLySuCoYKhoa page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('quanLySuCoYKhoa');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', quanLySuCoYKhoaPageUrlPattern);
      });

      it('edit button click should load edit QuanLySuCoYKhoa page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('QuanLySuCoYKhoa');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', quanLySuCoYKhoaPageUrlPattern);
      });

      it('last delete button click should delete instance of QuanLySuCoYKhoa', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('quanLySuCoYKhoa').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', quanLySuCoYKhoaPageUrlPattern);

        quanLySuCoYKhoa = undefined;
      });
    });
  });

  describe('new QuanLySuCoYKhoa page', () => {
    beforeEach(() => {
      cy.visit(`${quanLySuCoYKhoaPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('QuanLySuCoYKhoa');
    });

    it('should create an instance of QuanLySuCoYKhoa', () => {
      cy.get(`[data-cy="maSoSuCo"]`).type('throughput').should('have.value', 'throughput');

      cy.get(`[data-cy="ngayBaoCao"]`).type('2022-07-03T15:44').should('have.value', '2022-07-03T15:44');

      cy.get(`[data-cy="donViBaoCao"]`).type('Liaison SMTP').should('have.value', 'Liaison SMTP');

      cy.get(`[data-cy="soThuTu"]`).type('Steel').should('have.value', 'Steel');

      cy.get(`[data-cy="tenSuCo"]`).type('Regional Keyboard').should('have.value', 'Regional Keyboard');

      cy.get(`[data-cy="nhomSuCo"]`).type('Analyst Fantastic Associate').should('have.value', 'Analyst Fantastic Associate');

      cy.get(`[data-cy="mucDoSuCo"]`).type('circuit').should('have.value', 'circuit');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        quanLySuCoYKhoa = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', quanLySuCoYKhoaPageUrlPattern);
    });
  });
});
