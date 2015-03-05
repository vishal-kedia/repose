package org.openrepose.filters.flush;

import org.openrepose.core.filter.logic.impl.FilterLogicHandlerDelegate;

import javax.inject.Named;
import javax.servlet.*;
import java.io.IOException;

@Named
public class FlushOutputFilter implements Filter {

    private FlushOutputHandlerFactory handlerFactory;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        new FilterLogicHandlerDelegate(request, response, chain).doFilter(handlerFactory.newHandler());
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {        
        handlerFactory = new FlushOutputHandlerFactory();
    }
}
