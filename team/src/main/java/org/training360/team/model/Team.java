package org.training360.team.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.PERSIST)
    private List<Player> players = new ArrayList<>();

    public Team(String name, List<Player> players) {
        this.name = name;
        this.players = players;
    }

    public void addPlayer(Player player){
        players.add(player);
        player.setTeam(this);
    }
}
