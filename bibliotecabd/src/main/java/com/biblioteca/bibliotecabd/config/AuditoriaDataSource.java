package com.biblioteca.bibliotecabd.config;// <-- cambia al paquete real de tu proyecto

import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

/**
 * Cada vez que Spring toma una conexión del pool, se le indica a Oracle
 * quién es el usuario de la aplicación (CLIENT_IDENTIFIER). Los triggers
 * de auditoría lo leen y lo guardan en BITACORA.USUARIO_BD.
 */
public class AuditoriaDataSource extends DelegatingDataSource {

    private static final String USUARIO_POR_DEFECTO = "sistema";

    public AuditoriaDataSource(DataSource destino) {
        super(destino);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return marcarUsuario(super.getConnection());
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return marcarUsuario(super.getConnection(username, password));
    }

    private Connection marcarUsuario(Connection conexion) throws SQLException {
        String usuario = USUARIO_POR_DEFECTO;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            usuario = auth.getName();
        }
        try {
            conexion.setClientInfo("OCSID.CLIENTID", usuario);
        } catch (SQLClientInfoException e) {
            // Si falla, los triggers usarán el usuario de la conexión como respaldo
        }
        return conexion;
    }
}