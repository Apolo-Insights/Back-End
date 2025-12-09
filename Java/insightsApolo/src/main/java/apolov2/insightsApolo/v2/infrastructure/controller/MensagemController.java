package apolov2.insightsApolo.v2.infrastructure.controller;


import apolov2.insightsApolo.v2.infrastructure.DTO.LogDTO;
import apolov2.insightsApolo.v2.infrastructure.sercive.MessageProducer;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/logs")
public class MensagemController {

    private final MessageProducer producer;

    public MensagemController(MessageProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public String enviarLog(@RequestBody LogDTO log) {
        producer.enviarLog(log);
        return "Log enviado com sucesso!";
    }
}
