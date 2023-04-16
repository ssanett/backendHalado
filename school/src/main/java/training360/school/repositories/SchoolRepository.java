package training360.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import training360.school.model.School;

import java.util.List;
import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School, Long> {

    @Query("select s from School s left join fetch s.students where s.address.city=:city")
    List<School> findAllByCity(Optional<String> city);
}
