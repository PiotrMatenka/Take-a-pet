package pl.piotron.animals.services;

import org.springframework.stereotype.Service;
import pl.piotron.animals.exceptions.DuplicateEmailException;
import pl.piotron.animals.exceptions.UserNotFoundException;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.dto.ImageAdvertisementDto;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.model.mapper.ImageAdvertisementMapper;
import pl.piotron.animals.model.mapper.UserMapper;
import pl.piotron.animals.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageAdvertisementMapper imageAdvertisementMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper, ImageAdvertisementMapper imageAdvertisementMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageAdvertisementMapper = imageAdvertisementMapper;
    }
    public Optional<UserDto> findById (Long id)
    {
        return userRepository.findById(id).map(userMapper::userDto);
    }

    public List<UserDto> findAll()
    {
        return userRepository.findAll().stream().map(userMapper::userDto).collect(Collectors.toList());
    }
    public UserDto saveUser (UserDto user)
    {
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail !=null)
        {
            if (user.getEmail().equals(userByEmail.getEmail()))
                throw new DuplicateEmailException();
        }
        return mapAndSave(user);
    }

    public UserDto updateUser (UserDto user)
    {
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail !=null && !userByEmail.getId().equals(user.getId()))
            throw new DuplicateEmailException();
        return mapAndSave(user);
    }

    public List<ImageAdvertisementDto> getUserAdvertisements(Long id)
    {
        return userRepository.findById(id)
                .map(User::getAdvertisements)
                .orElseThrow(UserNotFoundException::new)
                .stream()
                .map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    private UserDto mapAndSave(UserDto user)
    {
        User userEntity = userMapper.toEntity(user);
        User savedUser = userRepository.save(userEntity);
        return userMapper.userDto(savedUser);
    }
}
