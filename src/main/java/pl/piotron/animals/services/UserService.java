package pl.piotron.animals.services;

import org.springframework.stereotype.Service;
import pl.piotron.animals.exceptions.AppException;
import pl.piotron.animals.exceptions.DuplicateEmailException;
import pl.piotron.animals.exceptions.UserNotFoundException;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.UserDetails;
import pl.piotron.animals.model.UserRole;
import pl.piotron.animals.model.dto.EndedAdvertisementDto;
import pl.piotron.animals.model.dto.ImageAdvertisementDto;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.model.mapper.EndedAdvertisementMapper;
import pl.piotron.animals.model.mapper.ImageAdvertisementMapper;
import pl.piotron.animals.model.mapper.UserMapper;
import pl.piotron.animals.repositories.UserDetailsRepository;
import pl.piotron.animals.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageAdvertisementMapper imageAdvertisementMapper;
    private final UserDetailsRepository userDetailsRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, ImageAdvertisementMapper imageAdvertisementMapper,
                       UserDetailsRepository userDetailsRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.imageAdvertisementMapper = imageAdvertisementMapper;
        this.userDetailsRepository  = userDetailsRepository;
    }
    public Optional<UserDto> findById (Long id)
    {
        return userRepository.findById(id).map(userMapper::userDto);
    }

    public UserDto findByEmail (String email)
    {
        if (email==null)
            throw new UserNotFoundException();

        return userMapper.userDto(userRepository.findByEmail(email));
    }

    public List<UserDto> findAll()
    {
        return userRepository.findAll().stream().map(userMapper::userDto).collect(Collectors.toList());
    }

    public List<UserDto> findAllByLastName(String text)
    {
        return userRepository.findAllByLastName(text)
                .stream().map(userMapper::userDto)
                .collect(Collectors.toList());
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
            throw new AppException("Użytkownik nie istnieje");
        return updateAndSave(user);
    }

    public List<ImageAdvertisementDto> getUserAdvertisements(Long id)
    {
        return userRepository.findById(id)
                .map(User::getAdvertisements)
                .orElseThrow(UserNotFoundException::new)
                .stream()
                .filter(a -> a.getEnd() == null)
                .map(imageAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<EndedAdvertisementDto> getEndedAdvertisements (Long id)
    {
        return userRepository.findById(id)
                .map(User::getAdvertisements)
                .orElseThrow(UserNotFoundException::new)
                .stream()
                .filter(a -> a.getEnd() != null)
                .map(EndedAdvertisementMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<UserRole> getAdminRole(String email)
    {
        Set<UserRole> userRoles = new HashSet<>(userRepository.findByEmail(email)
                .getRoles());
        UserRole userRole = new UserRole();
        for (UserRole r:userRoles)
            if (r.getRole().equals("ADMIN"))
                userRole = r;
        return Optional.of(userRole);
    }

    private UserDto mapAndSave(UserDto user)
    {
        User userEntity = userMapper.toEntity(user);
        UserDetails userDetails = userEntity.getUserDetails();
        UserDetails savedDetails = userDetailsRepository.save(userDetails);
        User savedUser = userRepository.save(userEntity);
        return userMapper.userDto(savedUser);
    }

    private UserDto updateAndSave (UserDto user)
    {
        UserDetails userEntity = userMapper.updateUser(user);
        UserDetails savedUser = userDetailsRepository.save(userEntity);
        return userMapper.updatedToDto(savedUser);
    }

}
