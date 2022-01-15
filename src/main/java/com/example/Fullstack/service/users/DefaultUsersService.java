package com.example.Fullstack.service.users;

import com.example.Fullstack.dto.UsersDTO;
import com.example.Fullstack.entity.Users;
import com.example.Fullstack.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@AllArgsConstructor
public class DefaultUsersService implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersConverter usersConverter;


    @Override
    public UsersDTO saveUser(UsersDTO usersDTO) throws ValidationException {
        validateUserDTO(usersDTO);
        Users savedUser = usersRepository.save(usersConverter.fromUserDTOToUser(usersDTO));
        return usersConverter.fromUserToUserDTO(savedUser);
    }

    private void validateUserDTO(UsersDTO usersDTO) throws ValidationException {
        if (isNull(usersDTO)) {
            throw new ValidationException("Object user is null");
        }
        if (isNull(usersDTO.getLogin()) || usersDTO.getLogin().isEmpty()) {
            throw new ValidationException("Login is empty");
        }
    }

    @Override
    public void deleteUser(String login) {
        usersRepository.delete(usersRepository.findByLogin(login));
    }

    @Override
    public UsersDTO findByLogin(String login) {
        Users users = usersRepository.findByLogin(login);
        if (users != null) {
            return usersConverter.fromUserToUserDTO(users);
        }
        return null;
    }

    @Override
    public UsersDTO findByToken(String token) {
        Users users = usersRepository.findByToken(token);
        if (users != null) {
            return usersConverter.fromUserToUserDTO(users);
        }
        return null;
    }

    @Override
    public List<UsersDTO> findAll() {
        return usersRepository.findAll()
                .stream()
                .map(usersConverter::fromUserToUserDTO)
                .collect(Collectors.toList());
    }
}