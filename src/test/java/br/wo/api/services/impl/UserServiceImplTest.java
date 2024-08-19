package br.wo.api.services.impl;

import br.wo.api.domain.User;
import br.wo.api.domain.dto.UserDto;
import br.wo.api.repositories.UserRepository;
import br.wo.api.services.exceptions.DataIntegrityViolationException;
import br.wo.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME = "Wanderson";
    public static final String EMAIL = "wanderson@gmail.com";
    public static final String PASSWORD = "12345";
    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema.";


    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;


    private User user = new User(ID, NAME, EMAIL, PASSWORD);
    private UserDto userDto = new UserDto(ID, NAME, EMAIL, PASSWORD);
    private Optional<User> optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));

    @BeforeEach
    void setUp() {
        when(repository.findById(anyInt())).thenReturn(Optional.of(user));

        when(repository.findAll()).thenReturn(List.of(user));

        when(repository.save(any())).thenReturn(user);

        when(repository.findByEmail(anyString())).thenReturn(optionalUser);

       doNothing().when(repository).delete(any());

    }

    @Test
    @DisplayName("whenFindByIdThenReturnAnUserInstance")
    void whenFindByIdThenReturnAnUserInstance() {
        User response = service.findById(ID);

        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    @DisplayName("whenFindByIdThenReturnAnObjectNotFoundException")
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            service.findById(ID);
        } catch(Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }


    @Test
    @DisplayName("whenFindAllTheReturnAnListOfUsers")
    void whenFindAllTheReturnAnListOfUsers() {
       List<User> users = service.findAll();

       assertNotNull(users);
       assertEquals(1, users.size());
       assertEquals(User.class, users.get(0).getClass());
       assertEquals(ID, users.get(0).getId());
    }

    @Test
    @DisplayName("whenCreateTheReturnSuccess")
    void whenCreateTheReturnSuccess() {
        User newUser = service.create(userDto);

        assertNotNull(newUser);
        assertEquals(User.class, newUser.getClass());
        assertEquals(ID, newUser.getId());
        assertEquals(NAME, newUser.getName());
        assertEquals(EMAIL, newUser.getEmail());
        assertEquals(PASSWORD, newUser.getPassword());
    }

    @Test
    @DisplayName("whenCreateTheReturnAnDataIntegrityViolationException")
    void whenCreateTheReturnAnDataIntegrityViolationException() {

        try {
            optionalUser.get().setId(2);
            service.create(userDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test
    @DisplayName("whenUpdateThenReturnSuccess")
    void whenUpdateThenReturnSuccess() {
        User newUser = service.update(userDto);

        assertNotNull(newUser);
        assertEquals(User.class, newUser.getClass());
        assertEquals(ID, newUser.getId());
        assertEquals(NAME, newUser.getName());
        assertEquals(EMAIL, newUser.getEmail());
        assertEquals(PASSWORD, newUser.getPassword());
    }

    @Test
    @DisplayName("whenUpdateTheReturnAnDataIntegrityViolationException")
    void whenUpdateTheReturnAnDataIntegrityViolationException() {

        try {
            optionalUser.get().setId(2);
            service.create(userDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test
    @DisplayName("deleteWithSuccess")
    void deleteWithSuccess() {
        service.delete(ID);
        verify(repository, times(1)).delete(any());
    }

    @Test
    @DisplayName("deleteWithObjectNotFoundException")
    void deleteWithObjectNotFoundException() {
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            service.delete(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

}