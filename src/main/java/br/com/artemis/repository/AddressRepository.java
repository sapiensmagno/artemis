package br.com.artemis.repository;

import br.com.artemis.domain.Address;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("select address from Address address where address.user.login = ?#{principal.username}")
    List<Address> findByUserIsCurrentUser();

}
