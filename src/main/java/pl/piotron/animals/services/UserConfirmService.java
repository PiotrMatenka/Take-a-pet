package pl.piotron.animals.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import pl.piotron.animals.exceptions.AppException;
import pl.piotron.animals.model.ConfirmationToken;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.model.mapper.UserMapper;
import pl.piotron.animals.repositories.ConfirmationTokenRepository;
import pl.piotron.animals.repositories.UserRepository;

@Service
public class UserConfirmService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;
    private final UserMapper userMapper;

    public UserConfirmService(ConfirmationTokenRepository confirmationTokenRepository, UserRepository userRepository,
                              EmailSenderService emailSenderService, UserMapper userMapper) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
        this.userMapper = userMapper;
    }

    public void sendConfirmationEmail(UserDto user)
    {
        User userByEmail = userRepository.findByEmail(user.getEmail());
        ConfirmationToken token  = new ConfirmationToken(userByEmail);
        confirmationTokenRepository.save(token);

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
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(confirmToken);
        if (confirmationToken == null)
            throw new AppException("Token nie może być pusty");

        User user = userRepository.findByEmail(confirmationToken.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        userMapper.userDto(user);
    }

}
