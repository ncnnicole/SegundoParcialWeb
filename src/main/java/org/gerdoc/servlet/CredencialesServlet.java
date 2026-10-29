package org.gerdoc.servlet;

import org.gerdoc.model.Alumno;
import org.gerdoc.service.AlumnoService;
import org.gerdoc.service.impl.AlumnoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serial;

@WebServlet("/AlumnoServlet")
public class CredencialesServlet extends HttpServlet
{
    @Serial
    private static final long serialVersionUID = 1L;
    private final AlumnoService alumnoService = new AlumnoServiceImpl( );

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String accion = req.getParameter("accion");
        if ("delete".equals(accion))
        {
            Long id = Long.parseLong(req.getParameter("id"));
            alumnoService.deleteById(id);
            resp.sendRedirect("Alumnos.jsp");
            return;
        }
        String nombre = req.getParameter("nombre" );
        Integer edad = Integer.parseInt( req.getParameter("edad" ) );
        Double promedio = Double.parseDouble( req.getParameter("promedio" ) );
        Long id = Long.parseLong( req.getParameter("id" ) );

        Alumno alumno = Alumno.builder( )
                .nombre( nombre )
                .edad( edad )
                .promedio( promedio )
                .build();
        if( "update".equals(accion) )
        {
            alumno.setId( id );
            alumnoService.update( alumno );
        }
        else
        {
            alumnoService.save( alumno );
        }
        resp.sendRedirect("Alumnos.jsp");
    }
}
