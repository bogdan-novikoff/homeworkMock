package com.example.Controller;

import com.example.Model.RequestDTO;
import com.example.Model.ResponceDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Random;


// Пользовательский класс ошибки со статус кодом
// Классы с ошибками можно вынести в отдельный файл
@ResponseStatus(value = HttpStatus.UNAUTHORIZED) // тут указывется код ответа
class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }

    public HttpStatus getStatusCode() {
        // функцию можно использовать для обработки ошибки, чтобы не хардкодить код ответа
        return ValidationException.class.getAnnotation(ResponseStatus.class).value();
    }
}


@RestController
public class MainController {
    private final Logger log = LoggerFactory.getLogger(MainController.class);

    ObjectMapper mapper = new ObjectMapper();

    @PostMapping(
            value = "/info/postBalances",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public Object postBalances(@RequestBody RequestDTO requestDTO) {
        try {
            // Логика
            String clientId = requestDTO.getClientId();

            // Валидация clientId для примера.
            // На занятии был вопрос как вызвать пользовательскую ошибку, с опеределенным статус кодом
            // Класс с ошибкой объявлен выше
            if (clientId == null || clientId.length() != 19) { throw new ValidationException("clientId is not valid!");}

            char firstChar = clientId.charAt(0);

            BigDecimal maxLimit;
            String currency;
            if (firstChar == '8') {
                maxLimit = new BigDecimal(2000);
                currency = "US";
            } else if (firstChar == '9') {
                maxLimit = new BigDecimal(1000);
                currency = "EU";
            } else {
                maxLimit = new BigDecimal(10000);
                currency = "RUB";
            }

            BigDecimal balance = new BigDecimal(new Random().nextInt(maxLimit.intValue() + 1));

            // Запоминаем данные в ResponceDTO
            ResponceDTO responceDTO = new ResponceDTO();
            responceDTO.setRqUID(requestDTO.getRqUID());
            responceDTO.setClientId(clientId);
            responceDTO.setAccount(requestDTO.getAccount());
            responceDTO.setCurrency(currency);
            responceDTO.setBalance(balance);
            responceDTO.setMaxLimit(maxLimit);

            // Логируем
            log.info("\n*********** RequestDTO ***********\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(requestDTO));
            log.info("\n*********** ResponceDTO ***********\n" + mapper.writerWithDefaultPrettyPrinter().writeValueAsString(responceDTO));

            // Возвращаем объект responceDTO
            return responceDTO;

        } catch (ValidationException e) {
            // Обработка пользовательской ошибки
            log.error(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());

        } catch (Exception e) {
            // Логируем
            log.error(e.getMessage());
            // Возвращаем ошибку, объект класса ResponceEntity
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
