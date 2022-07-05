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

describe('ThongTinSuCo e2e test', () => {
  const thongTinSuCoPageUrl = '/thong-tin-su-co';
  const thongTinSuCoPageUrlPattern = new RegExp('/thong-tin-su-co(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const thongTinSuCoSample = {};

  let thongTinSuCo: any;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/thong-tin-su-cos+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/thong-tin-su-cos').as('postEntityRequest');
    cy.intercept('DELETE', '/api/thong-tin-su-cos/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (thongTinSuCo) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/thong-tin-su-cos/${thongTinSuCo.id}`,
      }).then(() => {
        thongTinSuCo = undefined;
      });
    }
  });

  it('ThongTinSuCos menu should load ThongTinSuCos page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('thong-tin-su-co');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response!.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ThongTinSuCo').should('exist');
    cy.url().should('match', thongTinSuCoPageUrlPattern);
  });

  describe('ThongTinSuCo page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(thongTinSuCoPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ThongTinSuCo page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/thong-tin-su-co/new$'));
        cy.getEntityCreateUpdateHeading('ThongTinSuCo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thongTinSuCoPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/thong-tin-su-cos',
          body: thongTinSuCoSample,
        }).then(({ body }) => {
          thongTinSuCo = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/thong-tin-su-cos+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/thong-tin-su-cos?page=0&size=20>; rel="last",<http://localhost/api/thong-tin-su-cos?page=0&size=20>; rel="first"',
              },
              body: [thongTinSuCo],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(thongTinSuCoPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ThongTinSuCo page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('thongTinSuCo');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thongTinSuCoPageUrlPattern);
      });

      it('edit button click should load edit ThongTinSuCo page', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ThongTinSuCo');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thongTinSuCoPageUrlPattern);
      });

      it('last delete button click should delete instance of ThongTinSuCo', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('thongTinSuCo').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response!.statusCode).to.equal(200);
        });
        cy.url().should('match', thongTinSuCoPageUrlPattern);

        thongTinSuCo = undefined;
      });
    });
  });

  describe('new ThongTinSuCo page', () => {
    beforeEach(() => {
      cy.visit(`${thongTinSuCoPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ThongTinSuCo');
    });

    it('should create an instance of ThongTinSuCo', () => {
      cy.get(`[data-cy="viTriXayRaSuCo"]`).type('Incredible Accountability').should('have.value', 'Incredible Accountability');

      cy.get(`[data-cy="ngayXayRaSuCo"]`).type('2022-07-03T08:16').should('have.value', '2022-07-03T08:16');

      cy.get(`[data-cy="thoiGian"]`).type('2022-07-03T20:14').should('have.value', '2022-07-03T20:14');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(201);
        thongTinSuCo = response!.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response!.statusCode).to.equal(200);
      });
      cy.url().should('match', thongTinSuCoPageUrlPattern);
    });
  });
});
