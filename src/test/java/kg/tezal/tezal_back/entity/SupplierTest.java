package kg.tezal.tezal_back.entity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class SupplierTest {

    @Autowired
    private TestEntityManager entityManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Supplier suppler;

    @Before
    public void setUp() {
        suppler = new Supplier();
        suppler.setName("Adilet");
    }


    @Test
    public void saveSupplier() {
        Supplier savedSupplier = this.entityManager.persistFlushFind(suppler);
        assertThat(savedSupplier.getId()).isNotNull();
        assertThat(savedSupplier.getName()).isEqualTo("Adilet");
    }

    @Test
    public void updateSupplier() {
        Supplier savedSupplier = this.entityManager.persistFlushFind(suppler);
        savedSupplier.setName("Test1");
        Supplier savedSupplier1 = this.entityManager.persistFlushFind(savedSupplier);
        assertThat(savedSupplier1.getId()).isNotNull();
        assertThat(savedSupplier1.getName()).isEqualTo("Test1");
    }

    @Test
    public void deleteSupplier() {
        Supplier savedSupplier = this.entityManager.persistFlushFind(suppler);
        this.entityManager.remove(savedSupplier);

        assertThat(this.entityManager.getId(savedSupplier) == null);
    }
}