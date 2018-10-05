package br.com.artemis.service;

import br.com.artemis.domain.DeliveryStatus;
import java.util.List;

/**
 * Service Interface for managing DeliveryStatus.
 */
public interface DeliveryStatusService {

    /**
     * Save a deliveryStatus.
     *
     * @param deliveryStatus the entity to save
     * @return the persisted entity
     */
    DeliveryStatus save(DeliveryStatus deliveryStatus);

    /**
     *  Get all the deliveryStatuses.
     *
     *  @return the list of entities
     */
    List<DeliveryStatus> findAll();

    /**
     *  Get the "id" deliveryStatus.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    DeliveryStatus findOne(Long id);

    /**
     *  Delete the "id" deliveryStatus.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
