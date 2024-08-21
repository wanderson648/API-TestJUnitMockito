package br.wo.api.resources;

import br.wo.api.domain.User;
import br.wo.api.domain.dto.UserDto;
import br.wo.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResource {

    private static final String ID  = "/{id}";
    
    private final UserService service;
    private final ModelMapper mapper;

    @GetMapping(ID)
    public ResponseEntity<UserDto> findById(@PathVariable("idUser") Integer id) {
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDto.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok().body(service.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class)).toList());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(obj);
    }

    @PutMapping(ID)
    public ResponseEntity<UserDto> update(
            @PathVariable Integer id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(userDto), UserDto.class));
    }

    @DeleteMapping(ID)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
