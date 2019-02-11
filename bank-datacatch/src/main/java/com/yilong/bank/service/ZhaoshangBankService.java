package com.yilong.bank.service;

import com.yilong.bank.entity.ZhaoshangBank;
import com.yilong.bank.repository.ZhaoshangBankRepository;
import jdk.nashorn.internal.ir.CatchNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ZhaoshangBankService {

    @Autowired
    private ZhaoshangBankRepository zhaoshangBankRepository;

    public  List<ZhaoshangBank> saveAll(List<ZhaoshangBank> bankList) {
        try {
            return zhaoshangBankRepository.saveAll(bankList);
        } catch (DataIntegrityViolationException e) {
            System.out.println("惟一主键约束");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    public ZhaoshangBank save(ZhaoshangBank bank) {
            return zhaoshangBankRepository.save(bank);
    }
}
