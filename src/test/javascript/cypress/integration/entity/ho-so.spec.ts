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

describe('HoSo e2e test', () => {
  const hoSoPageUrl = '/ho-so';
  const hoSoPageUrlPattern = new RegExp('/ho-so(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const hoSoSample = {};

  let hoSo: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/ho-sos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/ho-sos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/ho-sos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (hoSo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/ho-sos/${hoSo.id}`,
      }).then(() => {
        hoSo = undefined;
      });
    }
  });

  it('HoSos menu should load HoSos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('ho-so');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('HoSo').should('exist');
    cy.url().should('match', hoSoPageUrlPattern);
  });

  describe('HoSo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(hoSoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create HoSo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/ho-so/new$'));
        cy.getEntityCreateUpdateHeading('HoSo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', hoSoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/ho-sos',
          body: hoSoSample,
        }).then(({ body }) => {
          hoSo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/ho-sos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/ho-sos?page=0&size=20>; rel="last",<http://localhost/api/ho-sos?page=0&size=20>; rel="first"',
              },
              body: [hoSo],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(hoSoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details HoSo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('hoSo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', hoSoPageUrlPattern);
      });

      it('edit button click should load edit HoSo page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('HoSo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', hoSoPageUrlPattern);
      });

      it('last delete button click should delete instance of HoSo', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('hoSo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', hoSoPageUrlPattern);

        hoSo = undefined;
      });
    });
  });

  describe('new HoSo page', () => {
    beforeEach(() => {
      cy.visit(`${hoSoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('HoSo');
    });

    it('should create an instance of HoSo', () => {
      cy.get(`[data-cy="soThuTu"]`).type('Agent Concrete').should('have.value', 'Agent Concrete');

      cy.get(`[data-cy="tenHoSo"]`).type('Roads').should('have.value', 'Roads');

      cy.get(`[data-cy="noiLuu"]`).type('override magenta Gloves').should('have.value', 'override magenta Gloves');

      cy.get(`[data-cy="thoiGianLuu"]`).type('2022-07-03T11:46').should('have.value', '2022-07-03T11:46');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        hoSo = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', hoSoPageUrlPattern);
    });
  });
});
