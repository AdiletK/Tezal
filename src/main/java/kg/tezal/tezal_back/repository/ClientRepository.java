package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.Client;
import kg.tezal.tezal_back.model.ClientLongModel;
import kg.tezal.tezal_back.model.ClientShortModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query(value = "select new kg.tezal.tezal_back.model.ClientLongModel(client.id, client.image, client.personalCode,client.firstName,client.lastName, client.patronymic,client.clientSex,client.createdDate,client.nationality,client.locale) FROM Client client where client.id = :id")
    ClientLongModel findClientById(Long id);

    @Query("select new kg.tezal.tezal_back.model.ClientShortModel(client.id, client.image, client.firstName,client.lastName, client.patronymic,client.clientSex) FROM Client client")
    Page<ClientShortModel> getClientsByPagination(Pageable pageable);

    @Query("select new kg.tezal.tezal_back.model.ClientShortModel(client.id, client.image, client.firstName,client.lastName, client.patronymic,client.clientSex) FROM Client client")
    List<ClientShortModel> getAllClients();

    @Query("select new kg.tezal.tezal_back.model.ClientShortModel(client.id, client.image, client.firstName,client.lastName, client.patronymic,client.clientSex) from Client client where lower(firstName) like %?1% or lower(lastName) like %?1% or lower(firstName || ' ' || lastName)  like %?1% or lower(lastName || ' ' || firstName)  like %?1% ORDER BY firstName ASC")
    Page<ClientShortModel> getClientsByName(String search, Pageable pageable);

    @Query(value = "select new kg.tezal.tezal_back.model.ClientLongModel(client.id, client.image, client.personalCode,client.firstName,client.lastName, client.patronymic,client.clientSex,client.createdDate,client.nationality,client.locale) FROM Client client where client.personalCode = :code")
    ClientLongModel findClientByPersonalCode(String code);
}
