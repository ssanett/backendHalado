package training360.school.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.school.model.SchoolAgeStatus;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {



    private Long id;

    private String name;
    private LocalDate dateOfBirth;

    private SchoolAgeStatus schoolAgeStatus;
}
