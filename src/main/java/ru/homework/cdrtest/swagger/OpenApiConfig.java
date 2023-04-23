package ru.homework.cdrtest.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "№3 Домашнее задание",
                description = "Создание микросервисов с использование Java Spring Boot", version = "1.0.0",
                contact = @Contact(
                        name = "Antonov Vladimir",
                        url = "https://t.me/bloodyt3ars"
                )
        )
)
public class OpenApiConfig {
}
