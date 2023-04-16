package training360.school.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Column(name = "postal_code")
    private String postalCode;
    private String city;
    private String street;
    @Column(name = "house_number")
    private int houseNumber;

}
