package br.wo.api.services;

import br.wo.api.domain.User;


public interface UserService {
    User findById(Integer id);
}
