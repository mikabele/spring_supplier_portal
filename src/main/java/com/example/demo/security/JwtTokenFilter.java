package com.example.demo.security;

import com.example.demo.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {


	private final JwtTokenUtil jwtTokenUtil;

	private final AuthService authService;

	public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, AuthService authService) {
		this.jwtTokenUtil = jwtTokenUtil;
		this.authService = authService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response,
									FilterChain chain)
			throws ServletException, IOException {
		// Get authorization header and validate
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}
		// Get jwt token and validate
		final String token = header.split(" ")[1].trim();
		var res = jwtTokenUtil.decodeToken(token);
		if (res.getToken() == null) {
			chain.doFilter(request, response);
			return;
		}
		// Get user identity and set it on the spring security context
		UserDetails userDetails = authService
				.findByUsername(res.getPayload())
				.orElse(null);
		UsernamePasswordAuthenticationToken
				authentication = new UsernamePasswordAuthenticationToken(
				userDetails, null,
				userDetails == null ?
						List.of() : userDetails.getAuthorities()
		);
		authentication.setDetails(
				new WebAuthenticationDetailsSource().buildDetails(request)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

}