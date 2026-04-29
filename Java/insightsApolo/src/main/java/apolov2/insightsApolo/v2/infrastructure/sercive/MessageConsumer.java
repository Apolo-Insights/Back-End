package apolov2.insightsApolo.v2.infrastructure.sercive;


import apolov2.insightsApolo.v2.infrastructure.DTO.LogDTO;
import apolov2.insightsApolo.v2.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
public class MessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receberLog(LogDTO log) {
        logger.info("📥 Log recebido do RabbitMQ - Ação: {} | Usuário: {} | Descrição: {} | Data/Hora: {}", 
                log.getAcao(), 
                log.getUsuario(), 
                log.getDescricao(),
                log.getDataHora());
    }
}