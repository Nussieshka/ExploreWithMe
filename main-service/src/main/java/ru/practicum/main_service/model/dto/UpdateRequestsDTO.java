package ru.practicum.main_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.main_service.model.RequestStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateRequestsDTO {

    @NotEmpty
    private List<Long> requestIds;

    @NotNull
    private RequestStatus status;
}
