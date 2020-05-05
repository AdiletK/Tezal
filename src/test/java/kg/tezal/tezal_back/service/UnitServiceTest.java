package kg.tezal.tezal_back.service;

import kg.tezal.tezal_back.entity.Unit;
import kg.tezal.tezal_back.repository.UnitRepository;
import kg.tezal.tezal_back.service.impl.UnitServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {

    @Mock
    private UnitRepository unitRepo;

    @InjectMocks
    private UnitServiceImpl unitService;
    private Unit unit;
    private Unit unit1;
    @Before
    public void setUp() {
        unit = new Unit();
        unit.setId(1L);
        unit.setName("KG");
        unit1 = new Unit();
        unit1.setName("GR");
    }
    @Test
    public void saveUnitSuccess() {
        given(unitRepo.save(unit)).willAnswer(invocation -> invocation.getArgument(0));

        Unit saved = unitService.create(unit);

        assertThat(saved).isNotNull();

        verify(unitRepo).save(any(Unit.class));
    }

    @Test
    public void update() {
        given(unitRepo.save(unit)).willReturn(unit);
        given(unitRepo.save(unit1)).willReturn(unit1);
        Unit saved = unitService.create(unit);
        unit1.setId(saved.getId());
        Unit expected = unitService.create(unit1);

        assertThat(expected).isNotNull();
    }
    @Test
    public void shouldReturnFindAll() {
        List<Unit> datas = new ArrayList<>();
        datas.add(unit);
        datas.add(unit1);

        given(unitService.findAll()).willReturn(datas);

        List<Unit> expected = unitService.findAll();

        assertEquals(expected, datas);
    }
    @Test
    public void findById(){
        final Long id = 1L;

        given(unitRepo.findById(id)).willReturn(Optional.of(unit));

        final Unit expected  = unitService.findById(id);

        assertThat(expected).isNotNull();

    }

    @Test
    public void shouldBeDelete() {
        final Long userId=1L;

        unitService.deleteById(userId);
        unitService.deleteById(userId);

        verify(unitRepo, times(2)).deleteById(userId);
    }
}
