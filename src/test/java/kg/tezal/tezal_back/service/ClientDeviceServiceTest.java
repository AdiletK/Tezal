package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Client;
import kg.tezal.tezal_back.entity.ClientDevice;
import kg.tezal.tezal_back.repository.ClientDeviceRepository;
import kg.tezal.tezal_back.service.impl.ClientDeviceServiceImpl;
import kg.tezal.tezal_back.utils.RecordNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class ClientDeviceServiceTest {

    @Mock
    private ClientDeviceRepository clientDeviceRepository;

    @InjectMocks
    private ClientDeviceServiceImpl clientDeviceService;
    private ClientDevice clientDevice;
    private ClientDevice clientDevice1;
    @Before
    public void setUp() {
        Client client = new Client();
        client.setId(1L);
        client.setFirstName("Test");
        clientDevice = new ClientDevice();
        clientDevice.setId(1L);
        clientDevice.setClient(client);
        clientDevice.setPhoneNumber("0555555555");
        clientDevice.setPassword("123456");
        clientDevice1 = new ClientDevice();
        clientDevice1.setClient(client);
        clientDevice1.setPhoneNumber("0555555556");
        clientDevice1.setPassword("123456");
    }
    @Test
    public void saveUnitSuccess() {
        given(clientDeviceRepository.save(clientDevice)).willAnswer(invocation -> invocation.getArgument(0));

        ClientDevice saved = clientDeviceService.create(clientDevice);

        assertThat(saved).isNotNull();

        verify(clientDeviceRepository).save(any(ClientDevice.class));
    }
    @Test
    public void shouldThrowErrorWhenSaveClientDeviceWithExistingNumber() {

        given(clientDeviceRepository.findByPhoneNumber(clientDevice.getPhoneNumber())).willReturn(clientDevice);

        RecordNotFoundException exception =  assertThrows(RecordNotFoundException.class,
                () -> clientDeviceService.create(clientDevice));
        assertTrue(exception.getMessage().contains("Record already exist with number:"));
    }

    @Test
    public void update() {
        given(clientDeviceRepository.save(clientDevice)).willReturn(clientDevice);
        given(clientDeviceRepository.save(clientDevice1)).willReturn(clientDevice1);
        ClientDevice saved = clientDeviceService.create(clientDevice);
        clientDevice1.setId(saved.getId());
        ClientDevice expected = clientDeviceService.create(clientDevice1);

        assertThat(expected).isNotNull();
    }
    @Test
    public void shouldReturnFindAll() {
        List<ClientDevice> datas = new ArrayList<>();
        datas.add(clientDevice);
        datas.add(clientDevice1);

        given(clientDeviceService.findAll()).willReturn(datas);

        List<ClientDevice> expected = clientDeviceService.findAll();

        assertEquals(expected, datas);
    }
    @Test
    public void findById(){
        final Long id = 1L;

        given(clientDeviceRepository.findById(id)).willReturn(Optional.of(clientDevice));

        final ClientDevice expected  = clientDeviceService.findById(id);

        assertThat(expected).isNotNull();

    }

    @Test
    public void shouldBeDelete() {
        final Long userId=1L;

        clientDeviceService.deleteById(userId);
        clientDeviceService.deleteById(userId);

        verify(clientDeviceRepository, times(2)).deleteById(userId);
    }
}
