package apolov2.insightsApolo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository")
@EntityScan(basePackages = "apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity")
public class InsightsApoloApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsightsApoloApplication.class, args);
	}
}
