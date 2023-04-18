package org.training360.team.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.training360.team.model.PositionType;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class CreatePlayerCommand {

    @NotBlank
    private String name;
    private LocalDate dateOfBirth;
    private PositionType position;

    public CreatePlayerCommand(String name) {
        this.name = name;
    }
}
