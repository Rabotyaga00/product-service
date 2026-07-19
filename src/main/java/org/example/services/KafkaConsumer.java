package org.example.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.OrderEventDTO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumer {

    private final ProductService productService;

    /**
     * Стандартный потребитель для обработки сообщений в формате JSON строки.
     * <p>
     * Применяется, когда:
     * - Сообщения приходят в виде сырой JSON строки
     * - Требуется ручной контроль процесса десериализации
     * - Необходима дополнительная обработка ошибок парсинга JSON
     * <p>
     * Особенности:
     * - Использует ObjectMapper для десериализации
     * - Логирует ошибки парсинга JSON
     * - Подходит для обработки сообщений с нестандартными форматами
     *
//     * @param message сырое JSON сообщение в виде строки
//     */
//    @KafkaListener(
//            topics = "${app.kafka.topics.order-events}"
//            //,properties = {"value.deserializer=org.apache.kafka.common.serialization.StringDeserializer"}
//            //,properties = {"value.deserializer=org.springframework.kafka.support.serializer.JsonDeserializer"}
//            //при замене меняется способ чтения с listenStandard(OrderEventDto orderEventDto) на listenStandard(String message) то есть приходящее сообщение десериализуется как строка
//    )
//    @KafkaHandler
//    public void listenStandard(String message) {
//        try {
//            OrderEventDTO order = objectMapper.readValue(message, OrderEventDTO.class);
//            orderService.addOrder(orderMapper.toEntity(order));
//            log.info("Standard consumer with message processed and saved order: {}", order.getOrderId());
//        } catch (Exception e) {
//            log.error("Error processing message: {}", e.getMessage());
//            try {
//                throw e;
//            } catch (JsonProcessingException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//    }

    /**
     * Оптимизированный стандартный потребитель для автоматической десериализации DTO.
     * <p>
     * Применяется, когда:
     * - Сообщения приходят в стандартном JSON формате
     * - Spring Kafka может автоматически десериализовать в OrderEventDto
     * - Требуется минимальная конфигурация
     * <p>
     * Особенности:
     * - Автоматическая десериализация Spring Kafka
     * - Более чистый и лаконичный код
     * - Меньший контроль над процессом десериализации
     *
//     * @param productEventDto автоматически десериализованный объект OrderEventDto
     */
    @KafkaListener(topics = "${app.kafka.topics.order-created}")
    public void listenStandard(OrderEventDTO orderEventDto) {
        try {
            productService.applyOrderEvent(orderEventDto);
            log.info("Order event processed for orderId={}", orderEventDto.getOrderId());
        } catch (Exception e) {
            log.error("Error processing message: {}", e.getMessage());
        }
    }
}
