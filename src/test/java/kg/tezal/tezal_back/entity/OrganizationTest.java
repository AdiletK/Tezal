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

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class OrganizationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Organization organization;

    private Client firstClients;

    private Client secondClients;

    @Before
    public void setUp() {
        OrgCategory orgCategory = new OrgCategory();
        orgCategory.setName("Test");
        organization = new Organization();
        organization.clients = new HashSet<>();
        organization.setName("Alma");
        organization.setOrgCategory(this.entityManager.persistAndFlush(orgCategory));
        firstClients = new Client();
        firstClients.setFirstName("Adilet");
        firstClients = this.entityManager.persistAndFlush(firstClients);
        secondClients = new Client();
        secondClients.setFirstName("Esen");
        secondClients = this.entityManager.persistAndFlush(secondClients);
    }

    @Test
    public void saveOrganizationOrg() {
        Organization savedOrgData = this.entityManager.persistAndFlush(organization);
        assertThat(savedOrgData.getName()).isEqualTo("Alma");
    }

    @Test
    public void saveOrgClients() {
        organization.clients.add(firstClients);
        organization.clients.add(secondClients);
        assertThat(organization.getName()).isEqualTo("Alma");
        assertThat(organization.clients.size()).isEqualTo(2);
    }

    @Test
    public void removeOrgClients() {
        organization.clients.add(firstClients);
        organization.clients.add(secondClients);
        organization.clients.remove(secondClients);
        assertThat(organization.clients.size()).isEqualTo(1);
        assertThat(organization.clients.iterator().next().getFirstName()).isEqualTo("Adilet");
    }

}