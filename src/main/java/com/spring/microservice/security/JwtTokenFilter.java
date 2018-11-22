package com.spring.microservice.security;

import com.spring.microservice.service.ExploreCaliUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

/**
 * Filter for Java Web Token Authentication and Authorization.
 */
public class JwtTokenFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenFilter.class);
    private static final String BEARER = "Bearer";

    private ExploreCaliUserDetailsService userDetailsService;

    public JwtTokenFilter(ExploreCaliUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Determines if there is a JWT as part of the HTTP Request Header.
     * If it is valid then sets the current context With the Authentication(user and roles) found in the token.
     *
     * @param req         Servlet Request
     * @param res         Servlet Response
     * @param filterChain Filter Chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        LOGGER.info("Process request to check for a JSON Web Token ");
        //Check for Authorization:Bearer JWT
        String headerValue = ((HttpServletRequest) req).getHeader("Authorization");
        getBearerToken(headerValue).ifPresent(token -> {
            //Pull the Username and Roles from the JWT to construct the user details
            userDetailsService.loadUserByJwtToken(token).ifPresent(userDetails -> {
                //Add the user details (Permissions) to the Context for just this API invocation
                SecurityContextHolder.getContext().setAuthentication(
                        new PreAuthenticatedAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
            });
        });

        //move on to the next filter in the chains
        filterChain.doFilter(req, res);
    }

    /**
     * If present, extracts the jwt token from the "Bearer <jwt>" header value.
     *
     * @param jwtHeader header value
     * @return jwt if present, empty otherwise
     */
    private Optional<String> getBearerToken(String jwtHeader) {
        if (jwtHeader != null && jwtHeader.startsWith(BEARER)) {
            return Optional.of(jwtHeader.replace(BEARER, "").trim());
        }
        return Optional.empty();
    }
}