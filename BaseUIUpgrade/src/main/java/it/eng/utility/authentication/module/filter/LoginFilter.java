package it.eng.utility.authentication.module.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LoginFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain pFilterChain) throws IOException, ServletException {
		pFilterChain.doFilter(request, response);	
	}

	@Override
	public void destroy() {
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		

	}

}
