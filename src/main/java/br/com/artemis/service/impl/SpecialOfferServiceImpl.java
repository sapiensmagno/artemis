package br.com.artemis.service.impl;

import br.com.artemis.service.SpecialOfferService;
import br.com.artemis.domain.SpecialOffer;
import br.com.artemis.repository.SpecialOfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing SpecialOffer.
 */
@Service
@Transactional
public class SpecialOfferServiceImpl implements SpecialOfferService{

    private final Logger log = LoggerFactory.getLogger(SpecialOfferServiceImpl.class);

    private final SpecialOfferRepository specialOfferRepository;
    public SpecialOfferServiceImpl(SpecialOfferRepository specialOfferRepository) {
        this.specialOfferRepository = specialOfferRepository;
    }

    /**
     * Save a specialOffer.
     *
     * @param specialOffer the entity to save
     * @return the persisted entity
     */
    @Override
    public SpecialOffer save(SpecialOffer specialOffer) {
        log.debug("Request to save SpecialOffer : {}", specialOffer);
        return specialOfferRepository.save(specialOffer);
    }

    /**
     *  Get all the specialOffers.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SpecialOffer> findAll() {
        log.debug("Request to get all SpecialOffers");
        return specialOfferRepository.findAll();
    }

    /**
     *  Get one specialOffer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SpecialOffer findOne(Long id) {
        log.debug("Request to get SpecialOffer : {}", id);
        return specialOfferRepository.findOne(id);
    }

    /**
     *  Delete the  specialOffer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SpecialOffer : {}", id);
        specialOfferRepository.delete(id);
    }
}
