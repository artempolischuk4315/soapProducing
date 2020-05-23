package com.soap.endpoint;

import com.soap.repository.TeamRepository;
import com.soap.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import tutorial.soapservice.*;

import java.util.Optional;

@Endpoint
public class TeamEndpoint {
    private static final String NAMESPACE_URI = "http://soapService.tutorial";

    private TeamRepository teamRepository;

    @Autowired
    public TeamEndpoint(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTeamRequest")
    @ResponsePayload
    public GetTeamResponse getTeam(@RequestPayload GetTeamRequest request){

        ObjectFactory objectFactory = new ObjectFactory();
        GetTeamResponse response = objectFactory.createGetTeamResponse();

        Optional<Team> teamByName = teamRepository.findByName(request.getName());

        if(teamByName.isPresent()){
            Team teamFromRepo = teamByName.get();
            tutorial.soapservice.Team team = objectFactory.createTeam();
            team.setId(teamFromRepo.getId());
            team.setName(teamFromRepo.getName());
            response.setTeam(team);
            return response;
        }
        return null;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveTeamRequest")
    @ResponsePayload
    public SaveTeamResponse saveTeam(@RequestPayload SaveTeamRequest request){

        ObjectFactory objectFactory = new ObjectFactory();
        SaveTeamResponse response = objectFactory.createSaveTeamResponse();

        Team newTeam = new Team();
        newTeam.setName(request.getName());

        response.setId(teamRepository.save(newTeam).getId());
        return response;
    }


}
