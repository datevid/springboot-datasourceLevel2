package com.example.demo.configdb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "ignored")
@PropertySources({
        @PropertySource(value = "file:${catalina.home}/config/${PATH_CONFIG}/app_backend.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "file:${catalina.home}/config/app_backend.properties", ignoreResourceNotFound = true),
        @PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = true)
})
public class DataSourceConfig {
    @Value("${jndi.consultadata.jndiName:#{null}}")
    private String primaryJndiName;

    @Value("${jndi.consultadata.jndiName:#{null}}")
    private String secondaryJndiName;

    @Value("${nro_ruc:#{null}}")
    private String textFromProperties;

    private final JndiDataSourceLookup lookup = new JndiDataSourceLookup();

    //Configuración de Data source Primario
    @Primary
    @Bean(destroyMethod = "", name = "dsPrimary")
    @ConfigurationProperties(prefix = "jndi.verifica")
    public DataSource dataSourceDbPrimary() {
        System.out.println("textFromProperties");//solo para la demostración
        System.out.println(textFromProperties);//solo para la demostración
        DataSource dataSource;
        if (this.primaryJndiName != null && !this.primaryJndiName.equals("")) {
            dataSource = lookup.getDataSource(primaryJndiName);
        } else {
            dataSource = DataSourceBuilder.create().build();
        }
        return dataSource;
    }

    //Configuración de Data source Secundario
    @Bean(destroyMethod = "", name = "dsSecondary")
    @ConfigurationProperties(prefix = "jndi.verifica")
    public DataSource dataSourceDbSecondary() {
        System.out.println("textFromProperties secondary:");//solo para la demostración
        System.out.println(textFromProperties);//solo para la demostración
        DataSource dataSource;
        if (this.secondaryJndiName != null && !this.secondaryJndiName.equals("")) {
            dataSource = lookup.getDataSource(secondaryJndiName);
        } else {
            dataSource = DataSourceBuilder.create().build();
        }
        return dataSource;
    }

    //Templates para Data Sources Primario
    @Bean(name = "jdbcTemplateDbPrimary")
    @Primary
    @Autowired
    public JdbcTemplate jdbcTemplateDbSim(@Qualifier("dsPrimary") DataSource ds){
        return new JdbcTemplate(ds);
    }

    @Bean(name = "namedParameterJdbcTemplateDbPrimary")
    @Primary
    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplateDbSim(@Qualifier("dsPrimary") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    //Templates para Data Sources Secundario
    @Bean(name = "jdbcTemplateDbSecondary")
    @Autowired
    public JdbcTemplate jdbcTemplateDbSecondary(@Qualifier("dsSecondary") DataSource ds) {
        return new JdbcTemplate(ds);
    }

    @Bean(name = "namedParameterJdbcTemplateDbSecondary")
    @Autowired
    public NamedParameterJdbcTemplate namedParameterJdbcTemplateDbBio(@Qualifier("dsSecondary") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    //Transaction manager
    @Bean(name = "txmPrimary")
    @Autowired
    @Primary
    DataSourceTransactionManager tm1(@Qualifier("dsPrimary") DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }
}
