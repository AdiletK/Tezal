package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Supplier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SupplierRepoTest {

    @Autowired
    private SupplierRepository supplierRepo;

    private Supplier supplier;

    @Before
    public void setUp() {
        supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("Test");
    }

    @Test
    public void saveSupplierAndFindById() {
        supplier = supplierRepo.save(supplier);
        assertThat(supplierRepo.findById(supplier.getId()).get()).isEqualTo((supplier));
    }

    @Test
    public void updateSupplierAndFindById() {
        supplier.setName("Test1");
        supplier = supplierRepo.save(supplier);
        assertThat(supplierRepo.findById(supplier.getId()).get().getName()).isEqualTo("Test1");
    }

    @Test
    public void deleteSupplierAndFindById() {
        supplier.setName("Test1");
        supplierRepo.delete(supplier);
        assertThat(supplierRepo.findById(supplier.getId()).isPresent()).isEqualTo(false);
    }

}