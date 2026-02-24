package com.simplerisk.repository;

import com.simplerisk.entity.Risk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Layer (DAO): Kapselt den Zugriff auf die Tabelle risk.
 * Spring Data JPA liefert die Implementierung.
 */
@Repository
public interface RiskRepository extends JpaRepository<Risk, Long> {

    List<Risk> findByStatusOrderByCreatedAtDesc(Risk.RiskStatus status);
}
