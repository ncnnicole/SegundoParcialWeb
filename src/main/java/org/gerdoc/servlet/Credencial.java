package com.escuela.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/ESCUELA?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "tu_password"; // Cambia por tu contraseña de MySQL

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");

        // Consulta SQL para verificar credenciales, estado activo y rol
        String sql = "SELECT u.nombre, r.nombre_rol FROM USUARIO u " +
                "JOIN ROL r ON u.id_rol = r.id " +
                "WHERE u.usuario = ? AND u.password = ? AND u.activo = 1";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, usuario);
                pstmt.setString(2, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String nombreCompleto = rs.getString("nombre");
                        String rol = rs.getString("nombre_rol");

                        // Crear sesión HTTP
                        HttpSession session = request.getSession();
                        session.setAttribute("nombre", nombreCompleto);
                        session.setAttribute("rol", rol);

                        // Redireccionar según el rol
                        if ("PROFESOR".equalsIgnoreCase(rol)) {
                            response.sendRedirect("profesor/bienvenido.jsp");
                        } else if ("ALUMNO".equalsIgnoreCase(rol)) {
                            response.sendRedirect("alumno/bienvenido.jsp");
                        } else {
                            response.sendRedirect("login.jsp?error=rol_no_valido");
                        }
                    } else {
                        // Credenciales incorrectas o usuario inactivo
                        response.sendRedirect("login.jsp?error=credenciales_incorrectas");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=excepcion");
        }
    }
}