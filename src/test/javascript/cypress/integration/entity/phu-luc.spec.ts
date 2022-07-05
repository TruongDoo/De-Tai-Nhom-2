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

describe('PhuLuc e2e test', () => {
  const phuLucPageUrl = '/phu-luc';
  const phuLucPageUrlPattern = new RegExp('/phu-luc(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const phuLucSample = { maHieu: 'Corporate' };

  let phuLuc: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/phu-lucs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/phu-lucs').as('postEntityRequest');
    cy.intercept('DELETE', '/api/phu-lucs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (phuLuc) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/phu-lucs/${phuLuc.id}`,
      }).then(() => {
        phuLuc = undefined;
      });
    }
  });

  it('PhuLucs menu should load PhuLucs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('phu-luc');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('PhuLuc').should('exist');
    cy.url().should('match', phuLucPageUrlPattern);
  });

  describe('PhuLuc page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(phuLucPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create PhuLuc page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/phu-luc/new$'));
        cy.getEntityCreateUpdateHeading('PhuLuc');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', phuLucPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/phu-lucs',
          body: phuLucSample,
        }).then(({ body }) => {
          phuLuc = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/phu-lucs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/phu-lucs?page=0&size=20>; rel="last",<http://localhost/api/phu-lucs?page=0&size=20>; rel="first"',
              },
              body: [phuLuc],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(phuLucPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details PhuLuc page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('phuLuc');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', phuLucPageUrlPattern);
      });

      it('edit button click should load edit PhuLuc page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('PhuLuc');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', phuLucPageUrlPattern);
      });

      it('last delete button click should delete instance of PhuLuc', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('phuLuc').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', phuLucPageUrlPattern);

        phuLuc = undefined;
      });
    });
  });

  describe('new PhuLuc page', () => {
    beforeEach(() => {
      cy.visit(`${phuLucPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('PhuLuc');
    });

    it('should create an instance of PhuLuc', () => {
      cy.get(`[data-cy="soThuTu"]`).type('payment').should('have.value', 'payment');

      cy.get(`[data-cy="tenBieuMau"]`).type('grey').should('have.value', 'grey');

      cy.get(`[data-cy="maHieu"]`).type('auxiliary Belarussian').should('have.value', 'auxiliary Belarussian');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        phuLuc = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', phuLucPageUrlPattern);
    });
  });
});
