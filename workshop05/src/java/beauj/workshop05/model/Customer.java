package beauj.workshop05.model;

import java.util.Objects;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery( name = "Customer.findAll", query = "select c from Customer c")
public class Customer {

	@Id
	@Column(name="customer_id")
	private Integer customerId;

	private String name;
	private String addressline1;
	private String addressline2;

	private String city;
	private String state;
	private String zip;

	private String phone;
	private String fax;
	private String email;

	@Column(name="discount_code")
	private String discountCode;

	@Column(name="credit_limit")
	private Integer creditLimt;

	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getAddressline1() {
		return addressline1;
	}
	public void setAddressline1(String addressline1) {
		this.addressline1 = addressline1;
	}

	public String getAddressline2() {
		return addressline2;
	}
	public void setAddressline2(String addressline2) {
		this.addressline2 = addressline2;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getDiscountCode() {
		return discountCode;
	}
	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public Integer getCreditLimt() {
		return creditLimt;
	}
	public void setCreditLimt(Integer creditLimt) {
		this.creditLimt = creditLimt;
	}

	public JsonObject toJSON() {
		return (Json.createObjectBuilder()
				.add("custId", customerId)
				.add("name", name)
				.add("addr1", addressline1)
				.add("addr2", Objects.toString(addressline2, ""))
				.add("city", city)
				.add("state", state)
				.add("zip", zip)
				.add("phone", Objects.toString(phone, ""))
				.add("fax", Objects.toString(fax, ""))
				.add("email", Objects.toString(email, ""))
				.add("discount", discountCode)
				.add("credit", creditLimt)
				.build());
	}
}
