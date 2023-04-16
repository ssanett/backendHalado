package training360.school.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training360.school.dtos.CreateSchoolCommand;
import training360.school.dtos.CreateStudentCommand;
import training360.school.dtos.SchoolDto;
import training360.school.services.SchoolService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schools")
@AllArgsConstructor
public class SchoolController {

    SchoolService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private SchoolDto createSchool(@Valid @RequestBody CreateSchoolCommand command){
        return service.createSchool(command);
    }

    @PostMapping("/{id}/students")
    @ResponseStatus(HttpStatus.CREATED)
    private SchoolDto createStudent(@PathVariable("id") long id, @Valid @RequestBody CreateStudentCommand command){
        return service.createStudent(id,command);
    }

    @GetMapping()
    private List<SchoolDto> findSchools(@RequestParam Optional<String> city){
        return service.findSchools(city);
    }

    @PutMapping("/{id}/students/{stdId}")
    private SchoolDto fireStudentFromSchool(@PathVariable("id") long id,@PathVariable("stdId") long stdId){
        return service.fireStudentFromSchool(id,stdId);
    }




}
