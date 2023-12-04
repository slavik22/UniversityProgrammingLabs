package com.example.lab9.servlets;

import com.example.lab9.dao.DataAccessObject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GroupsDeleteServlet", value = "/deleteGroup")
public class GroupsDeleteServlet extends HttpServlet {

    private DataAccessObject dao;

    public void init() {
        dao = new DataAccessObject();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.deleteGroup(id);
        response.sendRedirect(request.getContextPath() + "/groups");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
