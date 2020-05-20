package com.soap.repository;

import com.soap.entity.Team;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component(value = "teamRepository")
public class TeamRepository {
    private static List<Team> teams =
            Arrays.asList(new Team(1, "Dynamo"), new Team(2, "Leeds United"),
                    new Team(3, "Ukraine national team"), new Team(4, "Team"));

    public Optional<Team> findTeamByName(String name){
        return teams.stream()
                .filter(team -> team.getName().equals(name))
                .findFirst();
    }

    public Integer saveTeam(String name){
        int size = teams.size();

        System.out.println("Adding: "+name);

        return size+1;
    }

}
