package br.com.artemis.repository;

import br.com.artemis.domain.DeliveryStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DeliveryStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryStatusRepository extends JpaRepository<DeliveryStatus, Long> {

}
