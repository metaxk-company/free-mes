package io.metaxk.module.system.api.tenant;

import io.metaxk.module.system.service.tenant.TenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多租户的 API 实现类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Service
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

    @Override
    public List<Long> getTenantIdList() {
        return tenantService.getTenantIdList();
    }

    @Override
    public void validateTenant(Long id) {
        tenantService.validTenant(id);
    }

}
