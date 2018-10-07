package br.com.artemis.service;

import br.com.artemis.domain.Purchase;
import java.util.List;

/**
 * Service Interface for managing Purchase.
 */
public interface PurchaseService {

	public static String INITIAL_STATUS = "Aguardando pagamento";
    /**
     * Save a purchase.
     *
     * @param purchase the entity to save
     * @return the persisted entity
     */
    Purchase save(Purchase purchase);

    /**
     *  Get all the purchases.
     *
     *  @return the list of entities
     */
    List<Purchase> findAll();

    /**
     *  Get the "id" purchase.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Purchase findOne(Long id);

    /**
     *  Delete the "id" purchase.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
