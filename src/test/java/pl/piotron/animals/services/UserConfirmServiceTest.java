package pl.piotron.animals.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import pl.piotron.animals.model.ConfirmationToken;
import pl.piotron.animals.model.User;
import pl.piotron.animals.model.dto.UserDto;
import pl.piotron.animals.repositories.ConfirmationTokenRepository;
import pl.piotron.animals.repositories.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserConfirmServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private ConfirmationTokenRepository confirmationTokenRepository;
    @Mock private EmailSenderService emailSenderService;
    @InjectMocks private UserConfirmService userConfirmService;
    private ConfirmationToken confirmationToken;
    private User user;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        user = createUser();
        confirmationToken = createToken();
        when(confirmationTokenRepository.findByConfirmationToken(ArgumentMatchers.anyString())).thenReturn(confirmationToken);
        when(userRepository.findByEmail(ArgumentMatchers.anyString())).thenReturn(user);
    }
    @Test
    void shouldReturnUser()
    {
       User user1 =  userConfirmService.checkToken(confirmationToken.getConfirmationToken());
       assertThat(user1.getEmail()).isEqualTo(user.getEmail());
    }
    @Test
    void shouldGenerateToken()
    {
        ConfirmationToken stringToken = userConfirmService.generateToken(ArgumentMatchers.anyString());
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        ConfirmationToken userToken = userConfirmService.generateToken(userDto);
        verify(confirmationTokenRepository).save(stringToken);
        verify(confirmationTokenRepository).save(userToken);
    }
    @Test
    void shouldSendEmail()
    {
        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userConfirmService.sendConfirmationEmail(userDto);
        verify(emailSenderService).sendEmail(ArgumentMatchers.any());
    }

    private User createUser()
    {
        User user = new User();
        user.setEmail("piotron@wp.pl");
        user.setEnabled(false);
        return user;
    }
    private ConfirmationToken createToken()
    {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setConfirmationToken("testToken");
        confirmationToken.setUser(user);
        return confirmationToken;
    }

}