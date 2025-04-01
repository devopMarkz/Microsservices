package br.com.erudio.erudio_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ErudioConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErudioConfigServerApplication.class, args);
	}

}
