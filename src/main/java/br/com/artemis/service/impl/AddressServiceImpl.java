package br.com.artemis.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.artemis.domain.Address;
import br.com.artemis.domain.User;
import br.com.artemis.repository.AddressRepository;
import br.com.artemis.service.AddressService;
import br.com.artemis.service.UserService;

/**
 * Service Implementation for managing Address.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService{

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;
    
    private final UserService userService;
    
    public AddressServiceImpl(AddressRepository addressRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
    }
    
    private void setAddressToLoggedUser (Address address) {
    	User loggedUser = this.userService.getUserWithAuthorities();
    	address.setUser(loggedUser);    	
    }

    /**
     * Save a address.
     *
     * @param address the entity to save
     * @return the persisted entity
     */
    @Override
    public Address save(Address address) {
        log.debug("Request to save Address : {}", address);
        this.setAddressToLoggedUser(address);
        return addressRepository.save(address);
    }
    
    /**
     *  Get all the addresses.
     *
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Address> findAll() {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll();
    }

    /**
     *  Get one address by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Address findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findOne(id);
    }

    /**
     *  Delete the  address by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.delete(id);
    }

	@Override
	public Address findByUser(User user) {
		return addressRepository.findByUserIsCurrentUser().get(0);
	}
}
