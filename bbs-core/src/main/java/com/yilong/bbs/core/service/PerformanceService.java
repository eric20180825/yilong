package com.yilong.bbs.core.service;

import com.yilong.bbs.core.entity.Performance;
import com.yilong.bbs.core.repository.PerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class PerformanceService {

    @Autowired
    private PerformanceRepository performanceRepository;

    public Performance save(Performance performance) {
        return performanceRepository.save(performance);
    }
}
