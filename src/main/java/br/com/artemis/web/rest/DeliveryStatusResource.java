package br.com.artemis.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import br.com.artemis.domain.DeliveryStatus;
import br.com.artemis.service.DeliveryStatusService;
import br.com.artemis.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing DeliveryStatus.
 */
@RestController
@RequestMapping("/api")
public class DeliveryStatusResource {

    private final Logger log = LoggerFactory.getLogger(DeliveryStatusResource.class);

    private static final String ENTITY_NAME = "deliveryStatus";

    private final DeliveryStatusService deliveryStatusService;

    public DeliveryStatusResource(DeliveryStatusService deliveryStatusService) {
        this.deliveryStatusService = deliveryStatusService;
    }

    /**
     * POST  /delivery-statuses : Create a new deliveryStatus.
     *
     * @param deliveryStatus the deliveryStatus to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deliveryStatus, or with status 400 (Bad Request) if the deliveryStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/delivery-statuses")
    @Timed
    public ResponseEntity<DeliveryStatus> createDeliveryStatus(@RequestBody DeliveryStatus deliveryStatus) throws URISyntaxException {
        log.debug("REST request to save DeliveryStatus : {}", deliveryStatus);
        if (deliveryStatus.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new deliveryStatus cannot already have an ID")).body(null);
        }
        DeliveryStatus result = deliveryStatusService.save(deliveryStatus);
        return ResponseEntity.created(new URI("/api/delivery-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /delivery-statuses : Updates an existing deliveryStatus.
     *
     * @param deliveryStatus the deliveryStatus to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deliveryStatus,
     * or with status 400 (Bad Request) if the deliveryStatus is not valid,
     * or with status 500 (Internal Server Error) if the deliveryStatus couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/delivery-statuses")
    @Timed
    public ResponseEntity<DeliveryStatus> updateDeliveryStatus(@RequestBody DeliveryStatus deliveryStatus) throws URISyntaxException {
        log.debug("REST request to update DeliveryStatus : {}", deliveryStatus);
        if (deliveryStatus.getId() == null) {
            return createDeliveryStatus(deliveryStatus);
        }
        DeliveryStatus result = deliveryStatusService.save(deliveryStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deliveryStatus.getId().toString()))
            .body(result);
    }

    /**
     * GET  /delivery-statuses : get all the deliveryStatuses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of deliveryStatuses in body
     */
    @GetMapping("/delivery-statuses")
    @Timed
    public List<DeliveryStatus> getAllDeliveryStatuses() {
        log.debug("REST request to get all DeliveryStatuses");
        return deliveryStatusService.findAll();
        }

    /**
     * GET  /delivery-statuses/:id : get the "id" deliveryStatus.
     *
     * @param id the id of the deliveryStatus to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deliveryStatus, or with status 404 (Not Found)
     */
    @GetMapping("/delivery-statuses/{id}")
    @Timed
    public ResponseEntity<DeliveryStatus> getDeliveryStatus(@PathVariable Long id) {
        log.debug("REST request to get DeliveryStatus : {}", id);
        DeliveryStatus deliveryStatus = deliveryStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(deliveryStatus));
    }

    /**
     * DELETE  /delivery-statuses/:id : delete the "id" deliveryStatus.
     *
     * @param id the id of the deliveryStatus to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/delivery-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteDeliveryStatus(@PathVariable Long id) {
        log.debug("REST request to delete DeliveryStatus : {}", id);
        deliveryStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
