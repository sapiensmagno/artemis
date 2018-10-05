package br.com.artemis.service.impl;

import br.com.artemis.service.DeliveryStatusService;
import br.com.artemis.domain.DeliveryStatus;
import br.com.artemis.repository.DeliveryStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing DeliveryStatus.
 */
@Service
@Transactional
public class DeliveryStatusServiceImpl implements DeliveryStatusService{

    private final Logger log = LoggerFactory.getLogger(DeliveryStatusServiceImpl.class);

    private final DeliveryStatusRepository deliveryStatusRepository;
    public DeliveryStatusServiceImpl(DeliveryStatusRepository deliveryStatusRepository) {
        this.deliveryStatusRepository = deliveryStatusRepository;
    }

    /**
     * Save a deliveryStatus.
     *
     * @param deliveryStatus the entity to save
     * @return the persisted entity
     */
    @Override
    public DeliveryStatus save(DeliveryStatus deliveryStatus) {
        log.debug("Request to save DeliveryStatus : {}", deliveryStatus);
        return deliveryStatusRepository.save(deliveryStatus);
    }

    /**
     *  Get all the deliveryStatuses.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryStatus> findAll() {
        log.debug("Request to get all DeliveryStatuses");
        return deliveryStatusRepository.findAll();
    }

    /**
     *  Get one deliveryStatus by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DeliveryStatus findOne(Long id) {
        log.debug("Request to get DeliveryStatus : {}", id);
        return deliveryStatusRepository.findOne(id);
    }

    /**
     *  Delete the  deliveryStatus by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryStatus : {}", id);
        deliveryStatusRepository.delete(id);
    }
}
