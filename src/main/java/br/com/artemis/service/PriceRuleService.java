package br.com.artemis.service;

import br.com.artemis.domain.PriceRule;
import java.util.List;

/**
 * Service Interface for managing PriceRule.
 */
public interface PriceRuleService {

    /**
     * Save a priceRule.
     *
     * @param priceRule the entity to save
     * @return the persisted entity
     */
    PriceRule save(PriceRule priceRule);

    /**
     *  Get all the priceRules.
     *
     *  @return the list of entities
     */
    List<PriceRule> findAll();

    /**
     *  Get the "id" priceRule.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    PriceRule findOne(Long id);

    /**
     *  Delete the "id" priceRule.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
