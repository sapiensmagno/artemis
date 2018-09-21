package br.com.artemis.service.impl;

import br.com.artemis.service.PriceRuleService;
import br.com.artemis.domain.PriceRule;
import br.com.artemis.repository.PriceRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing PriceRule.
 */
@Service
@Transactional
public class PriceRuleServiceImpl implements PriceRuleService{

    private final Logger log = LoggerFactory.getLogger(PriceRuleServiceImpl.class);

    private final PriceRuleRepository priceRuleRepository;
    public PriceRuleServiceImpl(PriceRuleRepository priceRuleRepository) {
        this.priceRuleRepository = priceRuleRepository;
    }

    /**
     * Save a priceRule.
     *
     * @param priceRule the entity to save
     * @return the persisted entity
     */
    @Override
    public PriceRule save(PriceRule priceRule) {
        log.debug("Request to save PriceRule : {}", priceRule);
        return priceRuleRepository.save(priceRule);
    }

    /**
     *  Get all the priceRules.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PriceRule> findAll() {
        log.debug("Request to get all PriceRules");
        return priceRuleRepository.findAll();
    }

    /**
     *  Get one priceRule by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PriceRule findOne(Long id) {
        log.debug("Request to get PriceRule : {}", id);
        return priceRuleRepository.findOne(id);
    }

    /**
     *  Delete the  priceRule by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PriceRule : {}", id);
        priceRuleRepository.delete(id);
    }
}
