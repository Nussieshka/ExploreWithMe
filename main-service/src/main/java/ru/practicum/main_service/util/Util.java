package ru.practicum.main_service.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import ru.practicum.main_service.model.dto.CategoryDTO;
import ru.practicum.main_service.model.dto.CreatedCompilationDTO;
import ru.practicum.main_service.model.dto.UpdateEventDTO;
import ru.practicum.main_service.model.dto.UserDTO;
import ru.practicum.main_service.model.entity.CategoryEntity;

import java.time.LocalDateTime;

@UtilityClass
public class Util {
    public static void updateCategoryFromDTO(CategoryDTO categoryDTO, CategoryEntity category) {
        String newName = categoryDTO.getName();
        if (newName != null && !newName.isBlank()) {
            category.setName(newName);
        }
    }

    public static boolean isInvalidEventDate(LocalDateTime eventDate) {
        return isInvalidEventDate(eventDate, 2);
    }

    public static boolean isInvalidEventDate(LocalDateTime eventDate, int minusHours) {
        if (eventDate == null) return false;
        return eventDate.minusHours(minusHours).isBefore(LocalDateTime.now());
    }

    public static void validateUpdateEventDTO(UpdateEventDTO eventDTO) {
        if (eventDTO == null) {
            throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                    "Body cannot be null.");
        }

        String annotation = eventDTO.getAnnotation();
        if (annotation != null) {
            if (annotation.isBlank()) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: annotation. Error: must not be blank. Value: " + annotation);
            }
            if (annotation.length() < 20) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: annotation. Error: must be at least 20 characters long. Value: " + annotation);
            }
            if (annotation.length() > 2000) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: annotation. Error: must not exceed 2000 characters. Value: " + annotation);
            }
        }

        String description = eventDTO.getDescription();
        if (description != null) {
            if (description.isBlank()) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: description. Error: must not be blank. Value: " + description);
            }
            if (description.length() < 20) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: description. Error: must be at least 20 characters long. Value: " + description);
            }
            if (description.length() > 7000) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: description. Error: must not exceed 7000 characters. Value: " + description);
            }
        }

        String title = eventDTO.getTitle();
        if (title != null) {
            if (title.isBlank()) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: title. Error: must not be blank. Value: " + title);
            }
            if (title.length() < 3) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: title. Error: must be at least 3 characters long. Value: " + title);
            }
            if (title.length() > 120) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                        "Field: title. Error: must not exceed 120 characters. Value: " + title);
            }
        }

        LocalDateTime eventDate = eventDTO.getEventDate();
        if (eventDate != null && eventDate.isBefore(LocalDateTime.now())) {
            throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrectly made request.",
                    "Field: eventDate. Error: must be a future date. Value: " + eventDate);
        }
    }

    public static void validateUserDTOEmail(UserDTO userDTO) {

        String[] parts = userDTO.getEmail().split("@");

        if (parts.length != 2) {
            return;
        }

        String localPart = parts[0];
        String domainPart = parts[1];
        String[] splitDomain = domainPart.split("\\.");

        if (localPart.length() > 64) {
            throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Incorrectly made request.",
                    "Local part of email exceeds maximum length.");
        }

        for (String domain : splitDomain) {
            if (domain.length() > 63) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Incorrectly made request.",
                        "Domain part of email exceeds maximum length.");
            }
        }
    }

    public static void validateCompilationDTO(CreatedCompilationDTO compilationDTO) {
        String title = compilationDTO.getTitle();
        if (title != null) {
            if (title.isBlank()) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Incorrectly made request.",
                        "Field: title. Error: must not be blank. Value: " + title);
            } else if (title.length() > 50) {
                throw new MessageResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Incorrectly made request.",
                        "Field: title. Error: must not exceed 50 characters. Value: " + title);
            }
        }
    }

}
