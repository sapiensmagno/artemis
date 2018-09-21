package br.com.artemis.web.rest;

import com.codahale.metrics.annotation.Timed;
import br.com.artemis.domain.SpecialOffer;
import br.com.artemis.service.SpecialOfferService;
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
 * REST controller for managing SpecialOffer.
 */
@RestController
@RequestMapping("/api")
public class SpecialOfferResource {

    private final Logger log = LoggerFactory.getLogger(SpecialOfferResource.class);

    private static final String ENTITY_NAME = "specialOffer";

    private final SpecialOfferService specialOfferService;

    public SpecialOfferResource(SpecialOfferService specialOfferService) {
        this.specialOfferService = specialOfferService;
    }

    /**
     * POST  /special-offers : Create a new specialOffer.
     *
     * @param specialOffer the specialOffer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new specialOffer, or with status 400 (Bad Request) if the specialOffer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/special-offers")
    @Timed
    public ResponseEntity<SpecialOffer> createSpecialOffer(@Valid @RequestBody SpecialOffer specialOffer) throws URISyntaxException {
        log.debug("REST request to save SpecialOffer : {}", specialOffer);
        if (specialOffer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new specialOffer cannot already have an ID")).body(null);
        }
        SpecialOffer result = specialOfferService.save(specialOffer);
        return ResponseEntity.created(new URI("/api/special-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /special-offers : Updates an existing specialOffer.
     *
     * @param specialOffer the specialOffer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated specialOffer,
     * or with status 400 (Bad Request) if the specialOffer is not valid,
     * or with status 500 (Internal Server Error) if the specialOffer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/special-offers")
    @Timed
    public ResponseEntity<SpecialOffer> updateSpecialOffer(@Valid @RequestBody SpecialOffer specialOffer) throws URISyntaxException {
        log.debug("REST request to update SpecialOffer : {}", specialOffer);
        if (specialOffer.getId() == null) {
            return createSpecialOffer(specialOffer);
        }
        SpecialOffer result = specialOfferService.save(specialOffer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, specialOffer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /special-offers : get all the specialOffers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of specialOffers in body
     */
    @GetMapping("/special-offers")
    @Timed
    public List<SpecialOffer> getAllSpecialOffers() {
        log.debug("REST request to get all SpecialOffers");
        return specialOfferService.findAll();
        }

    /**
     * GET  /special-offers/:id : get the "id" specialOffer.
     *
     * @param id the id of the specialOffer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the specialOffer, or with status 404 (Not Found)
     */
    @GetMapping("/special-offers/{id}")
    @Timed
    public ResponseEntity<SpecialOffer> getSpecialOffer(@PathVariable Long id) {
        log.debug("REST request to get SpecialOffer : {}", id);
        SpecialOffer specialOffer = specialOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(specialOffer));
    }

    /**
     * DELETE  /special-offers/:id : delete the "id" specialOffer.
     *
     * @param id the id of the specialOffer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/special-offers/{id}")
    @Timed
    public ResponseEntity<Void> deleteSpecialOffer(@PathVariable Long id) {
        log.debug("REST request to delete SpecialOffer : {}", id);
        specialOfferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
