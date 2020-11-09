package com.io.bookstoreapi.config.dev.persistence;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

//        @Value(value = "${DB_URL}")
//        private String url;
//
//        @Value(value = "${DB_PASSWORD}")
//        private String pwd;
//
//        @Value(value = "${DB_USERNAME}")
//        private String usr;


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
//
//
//                dataSource.setJdbcUrl(
//                        this.url
//                );
//                dataSource.setUser(
//                        this.usr
//                );
//                dataSource.setPassword(
//                        this.pwd
//                );

                dataSource.setJdbcUrl(
                        environment.getRequiredProperty("SPRING_DATASOURCE_URL")
                );
                dataSource.setUser(
                        environment.getRequiredProperty("SPRING_DATASOURCE_USERNAME")
                );
                dataSource.setPassword(
                        environment.getRequiredProperty("SPRING_DATASOURCE_PASSWORD")
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
