package com.biblioteca.bibliotecabd.config; // <-- cambia al paquete real de tu proyecto

import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Envuelve automáticamente el DataSource que crea Spring Boot,
 * sin tener que modificar application.properties.
 */
@Component
public class AuditoriaDataSourcePostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean instanceof DataSource ds && !(bean instanceof AuditoriaDataSource)) {
            return new AuditoriaDataSource(ds);
        }
        return bean;
    }
}