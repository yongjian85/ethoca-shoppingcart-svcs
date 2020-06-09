package com.yxj.ethoca.filters;

import com.mongodb.client.model.Filters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@WebFilter("/filter-response-header/*")
@Order(1)
public class CorsHeaders implements Filter {

    @Value("#{'${cors.white.list}'.split(',')}")
    private List<String> corsWhiteList;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {



        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String method = httpServletRequest.getMethod();

        String origin = httpServletRequest.getHeader("Origin");





        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (null != corsWhiteList) {



            if (corsWhiteList.contains(origin)) {


                httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);



            } else {
                httpServletResponse.setHeader("Access-Control-Allow-Origin", corsWhiteList.get(0));
            }
        } /*else we are not expecting any websites to connect to us so we are forcing them to fail CORS preflight check by not setting "Access-Control-Allow-Origin" */


        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        httpServletResponse.setHeader("Access-Control-Request-Headers", "authorization, Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Access-Control-Allow-Credentials, Access-Control-Allow-Origin, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization, Authorization");

        if (method.equalsIgnoreCase("OPTIONS")) {



            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }


        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // ...
    }

    @Override
    public void destroy() {
        // ...
    }

}
