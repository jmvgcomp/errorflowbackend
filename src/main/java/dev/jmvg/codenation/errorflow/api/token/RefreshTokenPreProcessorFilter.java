package dev.jmvg.codenation.errorflow.api.token;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenPreProcessorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if("/oauth/token".equalsIgnoreCase(request.getRequestURI()) &&
                "refresh_token".equals(request.getParameter("grant_type")) && request.getCookies() != null){
            for (Cookie cookie : request.getCookies()){
                if(cookie.getName().equals("refreshToken")){
                    String refreshToken = cookie.getValue();
                    request = new ServletRequestWrapper(request, refreshToken);
                }
            }
        }

        filterChain.doFilter(request, servletResponse);

    }
}
