package com.io.bookstoreapi.config.dev.persistence;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.logging.Logger;

@Configuration
@PropertySource(
        name = "datasource-properties",
        value = {"classpath:application-dev.properties"}
        )
@EnableTransactionManagement
@Profile(value = "dev")
public class DevConfiguration {

        @Autowired
        private Environment environment;

        private final Logger logger = Logger.getLogger(this.getClass().getName());
        // Define Data source
        @Bean
        public DataSource dataSource() {
                // Create connection pool
                ComboPooledDataSource dataSource = new ComboPooledDataSource();

                // Configure data source
                try {
                        dataSource.setDriverClass(
                                environment.getRequiredProperty("spring.datasource.driver-class-name")
                        );
                } catch (IllegalStateException | PropertyVetoException e) {
                        logger.warning("Could not load database driver!");
                }
                // Configure jdbc properties

                dataSource.setJdbcUrl(
                        environment.getRequiredProperty("spring.datasource.url")
                );
                dataSource.setUser(
                        environment.getRequiredProperty("spring.datasource.username")
                );
                dataSource.setPassword(
                        environment.getRequiredProperty("spring.datasource.password")
                );

                // Configure connection pool properties

                dataSource.setMinPoolSize(
                        Integer.parseInt(
                                environment.getRequiredProperty("spring.jpa.properties.hibernate.connection.pool.min-size")
                        )
                );
                dataSource.setMaxPoolSize(
                        Integer.parseInt(
                                environment.getRequiredProperty("spring.jpa.properties.hibernate.connection.pool.max-size")
                        )
                );
                dataSource.setInitialPoolSize(
                        Integer.parseInt(
                                environment.getRequiredProperty("spring.jpa.properties.hibernate.connection.pool.initial-size")
                        )
                );
                dataSource.setMaxIdleTime(
                        Integer.parseInt(
                                environment.getRequiredProperty("spring.jpa.properties.hibernate.connection.pool.initial-size")
                        )
                );

                return dataSource;
        }

}
