package com.yxj.ethoca.filters;

import com.mongodb.client.model.Filters;
import org.apache.logging.log4j.ThreadContext;
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

        //Adding a thread id to each request for logging purposes
        ThreadContext.put("id", UUID.randomUUID().toString());


        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        String method = httpServletRequest.getMethod();

        String origin = httpServletRequest.getHeader("Origin");

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (null != corsWhiteList) {

            //Allowing all CORS operations if the incoming request originated from a trusted UI server in the corsWhiteList property
            if (corsWhiteList.contains(origin)) {


                httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);



            } else {
                //In this example, the if statement does not make much of a different since both conditions serve the
                //"Access-Control-Allow-Origin": localhost:3000 -> reactUI address, but in real production, the ORIGIN field will
                //be from the production UI server, and when the browser sees localhost:3000, it will reject the request thinking that
                //it is a CORS attack from a malicious source.
                httpServletResponse.setHeader("Access-Control-Allow-Origin", corsWhiteList.get(0));
            }
        } /*else we are not expecting any websites to connect to us so we are forcing them to fail CORS preflight check by not setting "Access-Control-Allow-Origin" */


        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Request-Headers", "authorization, Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Access-Control-Allow-Credentials, Access-Control-Allow-Origin, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization, Authorization");

        //For API requests coming from browsers that perform preflight checks, allow OPTIONS check
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
