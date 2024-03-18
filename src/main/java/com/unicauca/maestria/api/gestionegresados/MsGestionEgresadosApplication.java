package com.unicauca.maestria.api.gestionegresados;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsGestionEgresadosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsGestionEgresadosApplication.class, args);
	}

}
