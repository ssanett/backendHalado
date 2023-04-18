package org.training360.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training360.team.model.Team;

public interface TeamRepository extends JpaRepository<Team,Long> {
}
