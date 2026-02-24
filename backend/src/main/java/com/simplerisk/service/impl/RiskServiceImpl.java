package com.simplerisk.service.impl;

import com.simplerisk.entity.Risk;
import com.simplerisk.repository.RiskRepository;
import com.simplerisk.service.RiskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Business Layer: Implementierung – koordiniert DAOs und Transaktionen.
 */
@Service
public class RiskServiceImpl implements RiskService {

    private final RiskRepository riskRepository;

    public RiskServiceImpl(RiskRepository riskRepository) {
        this.riskRepository = riskRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Risk> findAll() {
        return riskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Risk> findById(Long id) {
        return riskRepository.findById(id);
    }

    @Override
    @Transactional
    public Risk create(Risk risk) {
        return riskRepository.save(risk);
    }

    @Override
    @Transactional
    public Risk update(Long id, Risk risk) {
        Risk existing = riskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Risk nicht gefunden: " + id));
        existing.setTitle(risk.getTitle());
        existing.setDescription(risk.getDescription());
        existing.setStatus(risk.getStatus());
        return riskRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        riskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Risk> findByStatus(Risk.RiskStatus status) {
        return riskRepository.findByStatusOrderByCreatedAtDesc(status);
    }
}
