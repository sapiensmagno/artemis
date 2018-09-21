package br.com.artemis.repository;

import br.com.artemis.domain.PriceRule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PriceRule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PriceRuleRepository extends JpaRepository<PriceRule, Long> {

}
