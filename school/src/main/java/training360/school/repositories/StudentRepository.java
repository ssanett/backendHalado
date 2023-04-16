package training360.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.school.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
