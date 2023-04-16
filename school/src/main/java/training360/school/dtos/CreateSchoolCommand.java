package training360.school.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateSchoolCommand {


    @NotBlank(message = "Schoolname cannot be blank")
    private String schoolName;
    private String postalCode;
    private String city;
    private String street;
    private int houseNumber;

}
