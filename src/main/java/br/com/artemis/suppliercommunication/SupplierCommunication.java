package br.com.artemis.suppliercommunication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.artemis.domain.Address;
import br.com.artemis.domain.Product;
import br.com.artemis.domain.Supplier;


public class SupplierCommunication {
	
	private final Logger log = LoggerFactory.getLogger(SupplierCommunication.class);
	
	public Long createProductRequest (Product product, Address address) {
		Long requestId = 123L;
		
		log.debug("Request product using suppliers API");
	
		return requestId;
	}
	
}