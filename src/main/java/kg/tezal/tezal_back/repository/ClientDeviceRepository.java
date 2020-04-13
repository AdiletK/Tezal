package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.ClientDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientDeviceRepository extends JpaRepository<ClientDevice, Long> {
    ClientDevice findByPhoneNumber(String phone);
}
