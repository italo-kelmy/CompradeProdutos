package com.CompraDeProdutos.Configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("prod")
public class DadosUser {

    @Bean
    public DataSource usuariosDataSource(){
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/usuarios?useSSL=false&serverTimezone=UTC")
                .username("italo")
                .password("2610!!")
                .build();
    }

}
