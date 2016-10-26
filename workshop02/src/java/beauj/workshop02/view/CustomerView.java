package beauj.workshop02.view;

import beauj.workshop02.model.Customer;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@ViewScoped
@Named
public class CustomerView implements Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext private EntityManager em;

	private Integer queryId; 

	private Customer customer;

	public Integer getQueryId() {
		return queryId;
	}
	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void query() {

		customer = em.find(Customer.class, queryId);

		if (null == customer) {
			FacesMessage message = new FacesMessage(String.format("Customer %d not found", queryId));
			FacesContext.getCurrentInstance().addMessage(
					"queryForm:queryId", message);
			return;
		}
	}

	public void clearQuery() {
		customer = null;
		queryId = null;
	}
	
}
