package com.yilong.bbs.web.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditBean implements AuditorAware<String> {
    @Override
    public Optional getCurrentAuditor() {
        String account="张三";
        return Optional.ofNullable(account);
    }
}
