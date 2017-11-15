package beauj.workshop05.business;

import beauj.workshop05.model.Customer;
import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class CustomerBean {

	@PersistenceContext private EntityManager em;

	public Optional<Customer> findById(Integer cid) {
		return (Optional.ofNullable(em.find(Customer.class, cid)));
	}

	public List<Customer> findAll() {
		TypedQuery<Customer> query = em.createNamedQuery("Customer.findAll", Customer.class);
		return (query.getResultList());
	}

	public void add(Customer customer) {
		em.persist(customer);
	}
	
}
