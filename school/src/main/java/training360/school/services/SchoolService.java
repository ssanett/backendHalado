package training360.school.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.school.converter.Converter;
import training360.school.dtos.CreateSchoolCommand;
import training360.school.dtos.CreateStudentCommand;
import training360.school.dtos.SchoolDto;
import training360.school.exceptions.SchoolNotFoundExceptions;
import training360.school.exceptions.StudentNotFoundException;
import training360.school.model.Address;
import training360.school.model.School;
import training360.school.model.SchoolAgeStatus;
import training360.school.model.Student;
import training360.school.repositories.SchoolRepository;
import training360.school.repositories.StudentRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SchoolService {

    private SchoolRepository schoolRepository;
    private StudentRepository studentRepository;
    private Converter converter;


    public SchoolDto createSchool(CreateSchoolCommand command) {
        School school = School.builder()
                .schoolName(command.getSchoolName()).build();
        school.setAddress(Address.builder()
                .postalCode(command.getPostalCode())
                .city(command.getCity())
                .street(command.getStreet())
                .houseNumber(command.getHouseNumber()).build());
        return converter.schoolToDto(schoolRepository.save(school));
    }

    public SchoolDto createStudent(long id, CreateStudentCommand command) {
        School school = schoolRepository.findById(id).orElseThrow(() -> new SchoolNotFoundExceptions(id));
        Student student = Student.builder()
                .name(command.getName())
                .dateOfBirth(command.getDateOfBirth()).build();
        setAgeStatus(student);
        school.getStudents().add(student);
        student.setSchool(school);
        studentRepository.save(student);
        return converter.schoolToDto(schoolRepository.save(school));

    }

    public List<SchoolDto> findSchools(Optional<String> city) {
        if (city.isPresent()) {
            return converter.schoolToDto(schoolRepository.findAllByCity(city));
        }
        return converter.schoolToDto(schoolRepository.findAll());
    }


    private void setAgeStatus(Student student) {
        if (ChronoUnit.YEARS.between(LocalDate.now(),
                student.getDateOfBirth()) > 16) {
            student.setSchoolAgeStatus(SchoolAgeStatus.NOT_SCHOOL_AGED);
        }
        student.setSchoolAgeStatus(SchoolAgeStatus.SCHOOL_AGED);
    }


    public SchoolDto fireStudentFromSchool(long id, long stdId) {
        School school = schoolRepository.findById(id).orElseThrow(()->new SchoolNotFoundExceptions(id));
        Student student = studentRepository.findById(stdId).orElseThrow(()->new StudentNotFoundException(stdId));
        if(student.getSchool().getId() != school.getId()){
            throw new StudentNotFoundException(stdId);
        }
        student.setSchool(null);
        school.getStudents().remove(student);
        studentRepository.save(student);
        return converter.schoolToDto(schoolRepository.save(school));
    }
}
