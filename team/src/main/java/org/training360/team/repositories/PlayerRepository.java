package org.training360.team.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.training360.team.model.Player;

public interface PlayerRepository extends JpaRepository<Player,Long> {


}
