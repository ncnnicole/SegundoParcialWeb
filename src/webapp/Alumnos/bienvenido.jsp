<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    // Validar que exista sesión y el rol sea ALUMNO
    String nombre = (String) session.getAttribute("nombre");
    String rol = (String) session.getAttribute("rol");

    if (nombre == null || !"ALUMNO".equalsIgnoreCase(rol)) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema Escolar - Alumno</title>
</head>
<body>
<pre>
----------------------------------------
          SISTEMA ESCOLAR
----------------------------------------
Bienvenido, <%= nombre %>
Rol: <%= rol %>
</pre>
</body>
</html>