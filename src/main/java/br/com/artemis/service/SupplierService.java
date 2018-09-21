package br.com.artemis.service;

import br.com.artemis.domain.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Supplier.
 */
public interface SupplierService {

    /**
     * Save a supplier.
     *
     * @param supplier the entity to save
     * @return the persisted entity
     */
    Supplier save(Supplier supplier);

    /**
     *  Get all the suppliers.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Supplier> findAll(Pageable pageable);

    /**
     *  Get the "id" supplier.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Supplier findOne(Long id);

    /**
     *  Delete the "id" supplier.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
