package br.wo.api.resources;

import br.wo.api.domain.User;
import br.wo.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResource {

    private final UserService service;

    @GetMapping("/{idUser}")
    public ResponseEntity<User> findById(@PathVariable("idUser") Integer idUser) {
        return ResponseEntity.ok().body(service.findById(idUser));
    }
}
