package br.com.artemis.service;

import br.com.artemis.domain.SpecialOffer;
import java.util.List;

/**
 * Service Interface for managing SpecialOffer.
 */
public interface SpecialOfferService {

    /**
     * Save a specialOffer.
     *
     * @param specialOffer the entity to save
     * @return the persisted entity
     */
    SpecialOffer save(SpecialOffer specialOffer);

    /**
     *  Get all the specialOffers.
     *
     *  @return the list of entities
     */
    List<SpecialOffer> findAll();

    /**
     *  Get the "id" specialOffer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SpecialOffer findOne(Long id);

    /**
     *  Delete the "id" specialOffer.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
