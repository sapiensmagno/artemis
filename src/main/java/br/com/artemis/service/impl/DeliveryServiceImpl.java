package br.com.artemis.service.impl;

import br.com.artemis.service.DeliveryService;
import br.com.artemis.domain.Delivery;
import br.com.artemis.repository.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Delivery.
 */
@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService{

    private final Logger log = LoggerFactory.getLogger(DeliveryServiceImpl.class);

    private final DeliveryRepository deliveryRepository;
    public DeliveryServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    /**
     * Save a delivery.
     *
     * @param delivery the entity to save
     * @return the persisted entity
     */
    @Override
    public Delivery save(Delivery delivery) {
        log.debug("Request to save Delivery : {}", delivery);
        return deliveryRepository.save(delivery);
    }

    /**
     *  Get all the deliveries.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Delivery> findAll() {
        log.debug("Request to get all Deliveries");
        return deliveryRepository.findAll();
    }

    /**
     *  Get one delivery by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Delivery findOne(Long id) {
        log.debug("Request to get Delivery : {}", id);
        return deliveryRepository.findOne(id);
    }

    /**
     *  Delete the  delivery by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Delivery : {}", id);
        deliveryRepository.delete(id);
    }
}
