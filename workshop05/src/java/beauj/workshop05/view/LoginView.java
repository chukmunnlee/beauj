package beauj.workshop05.view;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@ViewScoped
@Named
public class LoginView implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {

		FacesContext ctx = FacesContext.getCurrentInstance();
		ExternalContext ext = ctx.getExternalContext();

		HttpServletRequest req = (HttpServletRequest)ctx.getExternalContext()
				.getRequest();

		try {
			req.login(username, password);
		} catch (ServletException ex) {
			FacesMessage message = new FacesMessage("Incorrect login");
			ctx.addMessage(null, message);
			return (null);
		}

		return ("/secure/checkout");

	}
}
