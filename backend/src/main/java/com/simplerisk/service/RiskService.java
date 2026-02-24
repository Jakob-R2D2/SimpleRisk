package com.simplerisk.service;

import com.simplerisk.entity.Risk;

import java.util.List;
import java.util.Optional;

/**
 * Business Layer: Schnittstelle für Risiko-Geschäftslogik.
 */
public interface RiskService {

    List<Risk> findAll();

    Optional<Risk> findById(Long id);

    Risk create(Risk risk);

    Risk update(Long id, Risk risk);

    void deleteById(Long id);

    List<Risk> findByStatus(Risk.RiskStatus status);
}
