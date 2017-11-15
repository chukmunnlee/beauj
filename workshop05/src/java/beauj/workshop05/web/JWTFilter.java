package beauj.workshop05.web;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/api/*", asyncSupported = true, 
		dispatcherTypes = {DispatcherType.ASYNC, DispatcherType.REQUEST})
public class JWTFilter implements Filter {

	private byte[] SECRET = null;

	@Override
	public void init(FilterConfig filterConfig) 
			throws ServletException {
		SECRET = (byte[])filterConfig.getServletContext().getAttribute("secret");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException { 
		HttpServletRequest httpReq = (HttpServletRequest)request;
		String authHeader = httpReq.getHeader("Authorization");

		if (Objects.isNull(authHeader)) {
			HttpServletResponse httpResp = (HttpServletResponse)response;
			httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}

		//Watch the space in 'Bearer '
		final String token = authHeader.substring("Bearer ".length());

		try {
			Claims claims = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody();
		} catch (ExpiredJwtException | IllegalArgumentException | 
				MalformedJwtException | SignatureException | UnsupportedJwtException e) {
			e.printStackTrace();
			HttpServletResponse httpResp = (HttpServletResponse)response;
			httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		} 

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() { }
	
}
