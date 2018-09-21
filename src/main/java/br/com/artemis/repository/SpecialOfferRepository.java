package br.com.artemis.repository;

import br.com.artemis.domain.SpecialOffer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SpecialOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {

}
