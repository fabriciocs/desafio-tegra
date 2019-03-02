package br.com.tegra.repository;

import br.com.tegra.domain.AirplaneTripImport;
import br.com.tegra.domain.enumeration.ImportStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * Spring Data  repository for the AirplaneTripImport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AirplaneTripImportRepository extends JpaRepository<AirplaneTripImport, Long> {

    Collection<AirplaneTripImport> findAllByStatusIs(ImportStatus status);
}
