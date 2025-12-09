package apolov2.insightsApolo.v2.infrastructure.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConfig.class);

    public static final String QUEUE_NAME = "apolo.queue";
    public static final String EXCHANGE_NAME = "apolo.exchange";
    public static final String ROUTING_KEY = "apolo.key";

    @Bean
    public Queue queue() {
        Queue queue = new Queue(QUEUE_NAME, true);
        logger.info("✅ Queue '{}' criada", QUEUE_NAME);
        return queue;
    }

    @Bean
    public TopicExchange exchange() {
        TopicExchange exchange = new TopicExchange(EXCHANGE_NAME);
        logger.info("✅ Exchange '{}' criado", EXCHANGE_NAME);
        return exchange;
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
        logger.info("✅ Binding criado: {} -> {} com routing key '{}'", QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        return binding;
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        template.setMandatory(true);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
}