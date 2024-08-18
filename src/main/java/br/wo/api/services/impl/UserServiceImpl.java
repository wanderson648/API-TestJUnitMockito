package br.wo.api.services.impl;

import br.wo.api.domain.User;
import br.wo.api.domain.dto.UserDto;
import br.wo.api.repositories.UserRepository;
import br.wo.api.services.UserService;
import br.wo.api.services.exceptions.DataIntegrityViolationException;
import br.wo.api.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;
    @Override
    public User findById(Integer id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User create(UserDto obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, User.class));
    }

    @Override
    public User update(UserDto userDto) {
        return create(userDto);
    }

    @Override
    public void delete(Integer id) {
        User user = findById(id);
        repository.delete(user);
    }

    private void findByEmail(UserDto obj) {
        Optional<User> user = repository.findByEmail(obj.getEmail());
        if(user.isPresent() && !user.get().getId().equals(obj.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sitema.");
        }
    }
}
