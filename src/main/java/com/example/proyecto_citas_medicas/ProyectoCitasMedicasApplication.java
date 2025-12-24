package com.example.proyecto_citas_medicas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ProyectoCitasMedicasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoCitasMedicasApplication.class, args);
    }
}