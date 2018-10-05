package br.com.artemis.service;

import br.com.artemis.domain.Delivery;
import java.util.List;

/**
 * Service Interface for managing Delivery.
 */
public interface DeliveryService {

    /**
     * Save a delivery.
     *
     * @param delivery the entity to save
     * @return the persisted entity
     */
    Delivery save(Delivery delivery);

    /**
     *  Get all the deliveries.
     *
     *  @return the list of entities
     */
    List<Delivery> findAll();

    /**
     *  Get the "id" delivery.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Delivery findOne(Long id);

    /**
     *  Delete the "id" delivery.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
