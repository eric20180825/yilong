package com.yilong.bank.service;

import com.yilong.bank.entity.Page;
import com.yilong.bank.repository.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PageService {
    @Autowired
    private PageRepository pageRepository;
    public Page findById(Long id) {
        return pageRepository.getOne(id);
    }

    public Page save(Page page) {
        return pageRepository.save(page);
    }

    public List<Page> findAll() {
        return pageRepository.findAll();
    }

    public Page findByCurrentMonth(String currentMonth) {
       return pageRepository.findByCurrentMonth(currentMonth);
    }

}
