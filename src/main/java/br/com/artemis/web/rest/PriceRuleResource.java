package br.com.artemis.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.artemis.domain.PriceRule;
import br.com.artemis.service.PriceRuleService;
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
 * REST controller for managing PriceRule.
 */
@RestController
@RequestMapping("/api")
public class PriceRuleResource {

    private final Logger log = LoggerFactory.getLogger(PriceRuleResource.class);

    private static final String ENTITY_NAME = "priceRule";

    private final PriceRuleService priceRuleService;

    public PriceRuleResource(PriceRuleService priceRuleService) {
        this.priceRuleService = priceRuleService;
    }

    /**
     * POST  /price-rules : Create a new priceRule.
     *
     * @param priceRule the priceRule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priceRule, or with status 400 (Bad Request) if the priceRule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/price-rules")
    @Timed
    public ResponseEntity<PriceRule> createPriceRule(@Valid @RequestBody PriceRule priceRule) throws URISyntaxException {
        log.debug("REST request to save PriceRule : {}", priceRule);
        if (priceRule.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new priceRule cannot already have an ID")).body(null);
        }
        PriceRule result = priceRuleService.save(priceRule);
        return ResponseEntity.created(new URI("/api/price-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-rules : Updates an existing priceRule.
     *
     * @param priceRule the priceRule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priceRule,
     * or with status 400 (Bad Request) if the priceRule is not valid,
     * or with status 500 (Internal Server Error) if the priceRule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/price-rules")
    @Timed
    public ResponseEntity<PriceRule> updatePriceRule(@Valid @RequestBody PriceRule priceRule) throws URISyntaxException {
        log.debug("REST request to update PriceRule : {}", priceRule);
        if (priceRule.getId() == null) {
            return createPriceRule(priceRule);
        }
        PriceRule result = priceRuleService.save(priceRule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, priceRule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-rules : get all the priceRules.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of priceRules in body
     */
    @GetMapping("/price-rules")
    @Timed
    public List<PriceRule> getAllPriceRules() {
        log.debug("REST request to get all PriceRules");
        return priceRuleService.findAll();
        }

    /**
     * GET  /price-rules/:id : get the "id" priceRule.
     *
     * @param id the id of the priceRule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priceRule, or with status 404 (Not Found)
     */
    @GetMapping("/price-rules/{id}")
    @Timed
    public ResponseEntity<PriceRule> getPriceRule(@PathVariable Long id) {
        log.debug("REST request to get PriceRule : {}", id);
        PriceRule priceRule = priceRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(priceRule));
    }

    /**
     * DELETE  /price-rules/:id : delete the "id" priceRule.
     *
     * @param id the id of the priceRule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/price-rules/{id}")
    @Timed
    public ResponseEntity<Void> deletePriceRule(@PathVariable Long id) {
        log.debug("REST request to delete PriceRule : {}", id);
        priceRuleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
