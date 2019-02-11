package com.yilong.bbs.web.config;

import com.yilong.bbs.core.common.ProjectUtil;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@SpringBootConfiguration
public class AuditorBean implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        String account = ProjectUtil.getLoginUser()==null?null: ProjectUtil.getLoginUser().getAccount();
        return Optional.ofNullable(account);
    }
}
