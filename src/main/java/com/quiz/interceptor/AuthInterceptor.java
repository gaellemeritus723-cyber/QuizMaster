package com.quiz.interceptor;

import com.quiz.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String path = request.getRequestURI();

        // Routes publiques — toujours autorisées
        if (isPublic(path)) return true;

        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("currentUser") : null;

        // Non connecté → login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // Route /admin/** → réservée aux admins
        if (path.startsWith("/admin") && !user.isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/?forbidden");
            return false;
        }

        return true;
    }

    private boolean isPublic(String path) {
        return path.startsWith("/login")
            || path.startsWith("/register")
            || path.startsWith("/logout")
            || path.startsWith("/css/")
            || path.startsWith("/js/")
            || path.startsWith("/images/")
           
            || path.startsWith("/favicon");
    }
}
