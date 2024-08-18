package br.wo.api.services;

import br.wo.api.domain.User;
import br.wo.api.domain.dto.UserDto;

import java.util.List;


public interface UserService {
    User findById(Integer id);
    List<User> findAll();
    User create(UserDto obj);
    User update(UserDto userDto);
    void delete(Integer id);
}
