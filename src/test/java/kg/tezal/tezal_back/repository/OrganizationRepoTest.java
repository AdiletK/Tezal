package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.OrgCategory;
import kg.tezal.tezal_back.entity.Organization;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLIntegrityConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrganizationRepoTest {

    @Autowired
    private OrganizationRepository repository;
    @Autowired
    private TestEntityManager entityManager;
    private Organization organization;
    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Before
    public void setUp() {
        OrgCategory orgCategory = new OrgCategory();
        orgCategory.setName("Test");
        organization = new Organization();
        organization.setId(1L);
        organization.setName("Alma");
        organization.setOrgCategory(this.entityManager.persistAndFlush(orgCategory));
    }

    @Test
    public void saveAndFindById() {
        organization = repository.save(organization);
        assertThat(repository.findById(organization.getId()).get()).isEqualTo((organization));
    }

    @Test
    public void updateAndFindById() {
        organization.setName("Test1");
        organization = repository.save(organization);
        assertThat(repository.findById(organization.getId()).get().getName()).isEqualTo("Test1");
    }

    @Test
    public void deleteAndFindById() {
        organization.setName("Test1");
        repository.delete(organization);
        assertThat(repository.findById(organization.getId()).isPresent()).isEqualTo(false);
    }

    @Test
    public void getListAndFindById() {
        organization.setName("Test1");
        repository.save(organization);
        assertThat(repository.findAllOrganizationList().size()).isEqualTo(1);
    }
    @Test
    public void constraintException() {
        this.thrown.expect(SQLIntegrityConstraintViolationException.class);
        repository.save(new Organization());

    }
}