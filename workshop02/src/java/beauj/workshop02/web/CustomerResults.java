package beauj.workshop02.web;

import beauj.workshop02.model.Customer;
import java.io.Serializable;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@CacheDefaults(cacheName = "customerResult")
@ApplicationScoped
public class CustomerResults implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext private EntityManager em;

	@CacheResult
	public Customer findByCustomerId(@CacheKey final Integer custId) {
		return (em.find(Customer.class, custId));
	}

	@CacheRemove
	public void clearEntry(final Integer custId) { }
	
}
