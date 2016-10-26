package beauj.workshop01.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Item> items = new LinkedList<>();

	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	public void addItem(Item item) {
		items.add(item);
	}
	
}
