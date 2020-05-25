package com.demo.filter;

import com.demo.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();
        String[] routes = {"/login","/register"};

        for(String path:routes)
        {
            if(uri.contains(path))
            {
                request.getRequestDispatcher("login").forward(request,response);
                return;
            }
        }

        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("USER");
        if(u!=null)
        {
            chain.doFilter(req,resp);
            return;
        }


        request.getRequestDispatcher("login").forward(request,response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
