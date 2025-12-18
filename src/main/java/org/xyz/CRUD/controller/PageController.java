package org.xyz.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for serving Thymeleaf frontend pages.
 * These are server-rendered HTML pages, not REST APIs.
 */
@Controller
public class PageController {

    /**
     * Home page - redirects to login
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    /**
     * Login page
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // Returns templates/login.html
    }

    /**
     * Registration page
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register"; // Returns templates/register.html
    }

    /**
     * Dashboard page - requires authentication
     */
    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard"; // Returns templates/dashboard.html
    }
}
