package kg.tezal.tezal_back.repository;

import kg.tezal.tezal_back.entity.PreferenceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferenceCategoryRepository extends JpaRepository<PreferenceCategory, Long> {
}
