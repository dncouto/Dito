package br.com.microserviceDito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MicroserviceDitoApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MicroserviceDitoApplication.class, args);
    }

}