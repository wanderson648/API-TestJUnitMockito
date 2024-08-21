package br.wo.api.resources;

import br.wo.api.domain.User;
import br.wo.api.domain.dto.UserDto;
import br.wo.api.services.impl.UserServiceImpl;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
class UserResourceTest {

    public static final Integer ID = 1;
    public static final String NAME = "Wanderson";
    public static final String EMAIL = "wanderson@gmail.com";

    public static final String PASSWORD = "12345";
    private User user = new User(ID, NAME, EMAIL, PASSWORD);
    private UserDto userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);


    @InjectMocks
    private UserResource resource;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        when(service.findById(anyInt())).thenReturn(user);

        when(mapper.map(any(), any())).thenReturn(userDto);

        when(service.findAll()).thenReturn(List.of(user));

        when(service.create(any())).thenReturn(user);

    }

    @Test
    @DisplayName("whenFindByIdThenReturnSuccess")
    void whenFindByIdThenReturnSuccess() {
        ResponseEntity<UserDto> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDto.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    @DisplayName("whenGetAllThenReturnSuccess")
    void whenGetAllThenReturnSuccess() {
        ResponseEntity<List<UserDto>> users = resource.getAll();

        assertNotNull(users);
        assertNotNull(users.getBody());
        assertEquals(ResponseEntity.class, users.getClass());
        assertEquals(UserDto.class, users.getBody().get(0).getClass());

        assertEquals(ID, users.getBody().get(0).getId());
        assertEquals(NAME, users.getBody().get(0).getName());
        assertEquals(EMAIL, users.getBody().get(0).getEmail());
        assertEquals(PASSWORD, users.getBody().get(0).getPassword());
    }

    @Test
    @DisplayName("whenCreateThenReturnCreated")
    void whenCreateThenReturnCreated() {
        ResponseEntity<UserDto> user = resource.create(userDto);

        assertNotNull(user);
        assertNotNull(user.getBody());
        assertEquals(ResponseEntity.class, user.getClass());
        assertEquals(HttpStatus.CREATED, user.getStatusCode());

        assertEquals(ID, user.getBody().getId());
        assertEquals(NAME, user.getBody().getName());
        assertEquals(EMAIL, user.getBody().getEmail());
        assertEquals(PASSWORD, user.getBody().getPassword());

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}