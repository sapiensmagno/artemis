package br.com.artemis.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.artemis.domain.Purchase;
import br.com.artemis.service.PurchaseService;
import br.com.artemis.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Purchase.
 */
@RestController
@RequestMapping("/api")
public class PurchaseResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseResource.class);

    private static final String ENTITY_NAME = "purchase";

    private final PurchaseService purchaseService;

    public PurchaseResource(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    /**
     * POST  /purchases : Create a new purchase.
     *
     * @param purchase the purchase to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchase, or with status 400 (Bad Request) if the purchase has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchases")
    @Timed
    public ResponseEntity<Purchase> createPurchase(@Valid @RequestBody Purchase purchase) throws URISyntaxException {
        log.debug("REST request to save Purchase : {}", purchase);
        if (purchase.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new purchase cannot already have an ID")).body(null);
        }
        Purchase result = purchaseService.save(purchase);
        return ResponseEntity.created(new URI("/api/purchases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchases : Updates an existing purchase.
     *
     * @param purchase the purchase to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchase,
     * or with status 400 (Bad Request) if the purchase is not valid,
     * or with status 500 (Internal Server Error) if the purchase couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchases")
    @Timed
    public ResponseEntity<Purchase> updatePurchase(@Valid @RequestBody Purchase purchase) throws URISyntaxException {
        log.debug("REST request to update Purchase : {}", purchase);
        if (purchase.getId() == null) {
            return createPurchase(purchase);
        }
        Purchase result = purchaseService.save(purchase);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchase.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchases : get all the purchases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of purchases in body
     */
    @GetMapping("/purchases")
    @Timed
    public List<Purchase> getAllPurchases() {
        log.debug("REST request to get all Purchases");
        return purchaseService.findAll();
        }

    /**
     * GET  /purchases/:id : get the "id" purchase.
     *
     * @param id the id of the purchase to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchase, or with status 404 (Not Found)
     */
    @GetMapping("/purchases/{id}")
    @Timed
    public ResponseEntity<Purchase> getPurchase(@PathVariable Long id) {
        log.debug("REST request to get Purchase : {}", id);
        Purchase purchase = purchaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(purchase));
    }

    /**
     * DELETE  /purchases/:id : delete the "id" purchase.
     *
     * @param id the id of the purchase to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchases/{id}")
    @Timed
    public ResponseEntity<Void> deletePurchase(@PathVariable Long id) {
        log.debug("REST request to delete Purchase : {}", id);
        purchaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
