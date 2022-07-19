package com.example.demo.security;

import com.example.demo.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

import static java.lang.String.format;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private final AuthService authService;

  private final JwtTokenFilter jwtTokenFilter;

  public SecurityConfiguration(AuthService authService, JwtTokenFilter jwtTokenFilter) {
    this.authService = authService;
    this.jwtTokenFilter = jwtTokenFilter;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(
        username -> {
          return authService
              .findByUsername(username)
              .orElseThrow(
                  () -> new UsernameNotFoundException(format("User: %s, not found", username)));
        });
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // Enable CORS and disable CSRF
    http = http.cors().and().csrf().disable();

    //        Set session management to stateless
    http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();

    //  Set unauthorized requests exception handler
    http =
        http.exceptionHandling()
            .authenticationEntryPoint(
                (request, response, ex) -> {
                  response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                })
            .and();

    // Set permissions on endpoints
    http.authorizeRequests()
        // Our public endpoints
        .antMatchers("/api/login")
        .permitAll()
        // Our private endpoints
        .anyRequest()
        .permitAll();
    // .authenticated();

    // Add JWT token filter
    http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  //
  //	// Used by spring security if CORS is enabled.
  //	@Bean
  //	public CorsFilter corsFilter() {
  //		UrlBasedCorsConfigurationSource source =
  //				new UrlBasedCorsConfigurationSource();
  //		CorsConfiguration config = new CorsConfiguration();
  //		config.setAllowCredentials(true);
  //		config.addAllowedOrigin("*");
  //		config.addAllowedHeader("*");
  //		config.addAllowedMethod("*");
  //		source.registerCorsConfiguration("/**", config);
  //		return new CorsFilter(source);
  //	}
}
