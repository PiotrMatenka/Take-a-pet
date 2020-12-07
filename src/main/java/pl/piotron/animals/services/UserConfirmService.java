package pl.piotron.animals.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.piotron.animals.exceptions.AppException;
import pl.piotron.animals.exceptions.UserNotFoundException;
import pl.piotron.animals.model.ConfirmationToken;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.model.mapper.UserMapper;
import pl.piotron.animals.repositories.ConfirmationTokenRepository;
import pl.piotron.animals.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserConfirmService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserConfirmService(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository,
                              EmailSenderService emailSenderService, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendConfirmationEmail(UserDto user)
    {

        ConfirmationToken token = generateToken(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Potwierdzenie rejestracji!");
        mailMessage.setFrom("takeapet.pl@gmail.com");
        mailMessage.setText("Aby potwierdzić rejestrację, prosimy kliknąć poniższy link : "
                +"http://localhost:8080/confirmAccount?token="+token.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
    }

    public void confirmAccount (String confirmToken)
    {
        User user = checkToken(confirmToken);
        user.setEnabled(true);
        userRepository.save(user);
        userMapper.userDto(user);
    }

    public void sendPasswordResetEmail(String email)
    {
        ConfirmationToken token = generateToken(email);
        UserDto user = resetPassword(token.getConfirmationToken());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Reset hasła!");
        mailMessage.setFrom("takeapet.pl@gmail.com");
        mailMessage.setText("Cześć "+user.getFirstName() +", poniżej Twoje nowe hasło: \n\n "
                +token.getConfirmationToken()+"\n\n"
                +"Możesz je zmienić na swoim koncie")
        ;

        emailSenderService.sendEmail(mailMessage);
    }

    public UserDto setNewPassword (Long userId, String password)
    {
        Optional<User>user = userRepository.findById(userId);
        User userById = user.orElseThrow(UserNotFoundException::new);
        String passwordEncoded = passwordEncoder.encode(password);
        userById.setPassword(passwordEncoded);
        userRepository.save(userById);
        return userMapper.userDto(userById);
    }

    private UserDto resetPassword (String confirmToken)
    {
        User user = checkToken(confirmToken);
        String password = passwordEncoder.encode(confirmToken);
        user.setPassword(password);
        User savedUser = userRepository.save(user);
        return userMapper.userDto(savedUser);
    }

    private ConfirmationToken generateToken (UserDto user)
    {
        User userByEmail = userRepository.findByEmail(user.getEmail());
        if (userByEmail == null)
        {
            throw new UserNotFoundException();
        }else {
            ConfirmationToken token  = new ConfirmationToken(userByEmail);
            confirmationTokenRepository.save(token);
            return token;
        }
    }

    private ConfirmationToken generateToken (String email)
    {
        User userByEmail = userRepository.findByEmail(email);
        if (userByEmail == null)
        {
            throw new UserNotFoundException();
        }else {
            ConfirmationToken token  = new ConfirmationToken(userByEmail);
            confirmationTokenRepository.save(token);
            return token;
        }
    }

    private User checkToken (String confirmToken)
    {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(confirmToken);
        if (confirmationToken == null)
            throw new AppException("Token nie może być pusty");

        User user = userRepository.findByEmail(confirmationToken.getUser().getEmail());
        return user;
    }

}
