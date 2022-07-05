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

describe('ThongTinNguoiBenh e2e test', () => {
  const thongTinNguoiBenhPageUrl = '/thong-tin-nguoi-benh';
  const thongTinNguoiBenhPageUrlPattern = new RegExp('/thong-tin-nguoi-benh(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const thongTinNguoiBenhSample = {};

  let thongTinNguoiBenh: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/thong-tin-nguoi-benhs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/thong-tin-nguoi-benhs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/thong-tin-nguoi-benhs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (thongTinNguoiBenh) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/thong-tin-nguoi-benhs/${thongTinNguoiBenh.id}`,
      }).then(() => {
        thongTinNguoiBenh = undefined;
      });
    }
  });

  it('ThongTinNguoiBenhs menu should load ThongTinNguoiBenhs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('thong-tin-nguoi-benh');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ThongTinNguoiBenh').should('exist');
    cy.url().should('match', thongTinNguoiBenhPageUrlPattern);
  });

  describe('ThongTinNguoiBenh page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(thongTinNguoiBenhPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ThongTinNguoiBenh page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/thong-tin-nguoi-benh/new$'));
        cy.getEntityCreateUpdateHeading('ThongTinNguoiBenh');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thongTinNguoiBenhPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/thong-tin-nguoi-benhs',
          body: thongTinNguoiBenhSample,
        }).then(({ body }) => {
          thongTinNguoiBenh = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/thong-tin-nguoi-benhs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/thong-tin-nguoi-benhs?page=0&size=20>; rel="last",<http://localhost/api/thong-tin-nguoi-benhs?page=0&size=20>; rel="first"',
              },
              body: [thongTinNguoiBenh],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(thongTinNguoiBenhPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ThongTinNguoiBenh page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('thongTinNguoiBenh');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thongTinNguoiBenhPageUrlPattern);
      });

      it('edit button click should load edit ThongTinNguoiBenh page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ThongTinNguoiBenh');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thongTinNguoiBenhPageUrlPattern);
      });

      it('last delete button click should delete instance of ThongTinNguoiBenh', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('thongTinNguoiBenh').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thongTinNguoiBenhPageUrlPattern);

        thongTinNguoiBenh = undefined;
      });
    });
  });

  describe('new ThongTinNguoiBenh page', () => {
    beforeEach(() => {
      cy.visit(`${thongTinNguoiBenhPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ThongTinNguoiBenh');
    });

    it('should create an instance of ThongTinNguoiBenh', () => {
      cy.get(`[data-cy="hoVaTen"]`).type('Buckinghamshire payment').should('have.value', 'Buckinghamshire payment');

      cy.get(`[data-cy="soBenhAn"]`).type('grey').should('have.value', 'grey');

      cy.get(`[data-cy="gioiTinh"]`).should('not.be.checked');
      cy.get(`[data-cy="gioiTinh"]`).click().should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        thongTinNguoiBenh = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', thongTinNguoiBenhPageUrlPattern);
    });
  });
});
