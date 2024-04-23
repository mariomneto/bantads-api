package dac.bantads;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class BantadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BantadsApplication.class, args);
	}

	@GetMapping("/")
	public String index(){
		return "bantads-api";
	}
}
