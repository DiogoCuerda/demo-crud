package com.api.democrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController

public class DemoCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoCrudApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "------";
	}

	@GetMapping("/easteregg")
	public String easterEgg(){
		return "<img src=\"https://img.estadao.com.br/resources/jpg/9/2/1489168579929.jpg\" alt=\"Girl in a jacket\" width=\"500\" height=\"400\">";
	}

}
