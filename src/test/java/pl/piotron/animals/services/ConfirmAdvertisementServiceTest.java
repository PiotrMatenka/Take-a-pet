package pl.piotron.animals.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import pl.piotron.animals.exceptions.AppException;
import pl.piotron.animals.exceptions.ImageNotFoundException;
import pl.piotron.animals.model.Advertisement;
import pl.piotron.animals.model.mapper.UserAdvertisementMapper;
import pl.piotron.animals.repositories.AdvertisementRepository;
import pl.piotron.animals.repositories.ImageRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfirmAdvertisementServiceTest {
    @Mock
    private AdvertisementRepository advertisementRepository;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private UserAdvertisementMapper userAdvertisementMapper;
    @Mock
    private EmailSenderService emailSenderService;
    @Captor
    private ArgumentCaptor<SimpleMailMessage> simpleMailMessageArgumentCaptor;

    private Advertisement advertisement;
    @InjectMocks ConfirmAdvertisementService confirmAdvertisementService;

    @BeforeEach
    void init()
    {
        MockitoAnnotations.openMocks(this);
        advertisement = createAdvertisement();
        when(advertisementRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(advertisement));
    }

    @Test
    void shouldThrowAppExceptionWhenAcceptByUser()
    {
        advertisement.setEnd(LocalDateTime.now());
        assertThatThrownBy(() -> confirmAdvertisementService.acceptAdverByUser(1L))
                .isInstanceOf(AppException.class)
                .hasMessage("Ogłoszenie jest już zakończone");
        assertThat(advertisement.isAcceptedByUser()).isEqualTo(false);

    }
    @Test
    void shouldSetAcceptByUserOnTrue()
    {
        confirmAdvertisementService.acceptAdverByUser(1L);
        assertThat(advertisement.isAcceptedByUser()).isEqualTo(true);

    }
    @Test
    void shouldThrowAppExceptionWhenAcceptAdmin()
    {
        advertisement.setEnd(LocalDateTime.now());
        assertThatThrownBy(() -> confirmAdvertisementService.acceptAdverByAdmin(1L))
                .isInstanceOf(AppException.class)
                .hasMessage("Ogłoszenie jest już zakończone");
        assertThat(advertisement.isAcceptedByAdmin()).isEqualTo(false);
    }

    @Test
    void shouldFinishAdverAndRemoveImages()
    {
        confirmAdvertisementService.finishAdvertisement(1L);
        verify(imageRepository).deleteAll(ArgumentMatchers.anyCollection());
        assertNotNull(advertisement.getEnd());
    }

    private Advertisement createAdvertisement()
    {
        Advertisement advertisement = new Advertisement();
        advertisement.setEnd(null);
        advertisement.setId(1L);
        advertisement.setAcceptedByUser(false);
        advertisement.setAcceptedByAdmin(false);
        return advertisement;
    }

}