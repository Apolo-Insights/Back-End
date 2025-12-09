package apolov2.insightsApolo.v2.infrastructure.sercive;

import apolov2.insightsApolo.v2.infrastructure.DTO.LogDTO;
import apolov2.insightsApolo.v2.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;



@Service
public class MessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarLog(LogDTO log) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE_NAME,
                    RabbitMQConfig.ROUTING_KEY,
                    log
            );
            logger.info("📤 Log enviado para RabbitMQ - Ação: {} | Usuário: {} | Descrição: {}",
                    log.getAcao(), log.getUsuario(), log.getDescricao());
        } catch (Exception e) {
            logger.error("❌ Erro ao enviar log para RabbitMQ: {}", e.getMessage(), e);
            throw e;
        }
    }
}