package training360.school.converter;

import org.mapstruct.Mapper;
import training360.school.dtos.SchoolDto;
import training360.school.dtos.StudentDto;
import training360.school.model.School;
import training360.school.model.Student;

import java.util.List;

@Mapper(componentModel = "spring")
public interface Converter {

    SchoolDto schoolToDto(School school);
    List<SchoolDto> schoolToDto(List<School> schools);

    StudentDto studentToDto(Student student);
}
