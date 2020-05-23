package endpoint;

import com.soap.endpoint.TeamEndpoint;
import com.soap.entity.Team;
import com.soap.repository.TeamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tutorial.soapservice.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamEndpointTest {

    private static final String NAME = "Dynamo";
    private static final int ID = 1;
    @InjectMocks
    TeamEndpoint systemUnderTest;

    @Mock
    TeamRepository teamRepository;

    @Mock
    GetTeamRequest request;

    @Mock
    SaveTeamRequest saveTeamRequest;

    @Mock
    GetTeamResponse response;

    @Mock
    Team teamEntity;

    @Mock
    tutorial.soapservice.Team teamGenerated;

    @Mock
    ObjectFactory objectFactory;

    @Test
    void getTeamShouldReturnResponseIfSuchTeamExists(){

        tutorial.soapservice.Team team = createTeam();
        GetTeamResponse response = new GetTeamResponse();

        when(request.getName()).thenReturn(NAME);
        when(teamRepository.findByName(request.getName())).thenReturn(Optional.of(teamEntity));
        when(teamEntity.getId()).thenReturn(team.getId());
        when(teamEntity.getName()).thenReturn(team.getName());

        response.setTeam(team);

        GetTeamResponse actual = systemUnderTest.getTeam(request);

        assertEquals(response.getTeam().getId(), actual.getTeam().getId());
        assertEquals(response.getTeam().getName(), actual.getTeam().getName());
    }

    @Test
    void getTeamShouldReturnNullIfNoSuchTeam(){
        when(teamRepository.findByName(request.getName())).thenReturn(Optional.empty());

        GetTeamResponse actual = systemUnderTest.getTeam(request);

        assertNull(actual);
    }

    @Test
    void saveTeamShouldReturnResponseWithId(){
        when(saveTeamRequest.getName()).thenReturn(NAME);
        when(teamRepository.save(any(Team.class))).thenReturn(teamEntity);
        when(teamEntity.getId()).thenReturn(1);

        SaveTeamResponse actual = systemUnderTest.saveTeam(saveTeamRequest);

        assertEquals(1,  actual.getId());
    }

    private tutorial.soapservice.Team createTeam() {
        tutorial.soapservice.Team team = new tutorial.soapservice.Team();
        team.setId(ID);
        team.setName(NAME);
        return team;
    }
}
