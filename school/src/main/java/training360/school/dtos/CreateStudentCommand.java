package training360.school.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
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
public class CreateStudentCommand {

    @NotBlank(message = "Student name cannot be blank!")
    private String name;
    @Past(message = "Student date of birth must be in past!")
    private LocalDate dateOfBirth;

    private SchoolAgeStatus schoolAgeStatus;

    public CreateStudentCommand(String name, LocalDate dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }
}
