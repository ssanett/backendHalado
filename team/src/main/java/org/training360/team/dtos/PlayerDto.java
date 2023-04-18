package org.training360.team.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.training360.team.model.PositionType;
import org.training360.team.model.Team;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class PlayerDto {

    private long id;
    private String name;
    private LocalDate dateOfBirth;
    private PositionType position;



}
