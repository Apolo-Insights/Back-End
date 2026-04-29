package apolov2.insightsApolo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
@Disabled("Desativado temporariamente — causa erro por conta das variáveis de ambiente que não são reconhecidas MySQL/RabbitMQ")

@SpringBootTest(properties = "springdoc.api-docs.enabled=false")
class InsightsApoloApplicationTests {

	@Test
	void contextLoads() {
	}

}
