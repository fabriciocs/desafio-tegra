package br.com.tegra.repository;

import br.com.tegra.domain.AirplaneTripImport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AirplaneTripImport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirplaneTripImportRepository extends JpaRepository<AirplaneTripImport, Long> {

}
