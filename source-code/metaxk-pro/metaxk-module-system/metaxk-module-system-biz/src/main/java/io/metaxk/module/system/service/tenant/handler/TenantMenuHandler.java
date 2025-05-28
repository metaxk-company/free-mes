package io.metaxk.module.system.service.tenant.handler;

import java.util.Set;

/**
 * 租户菜单处理
 * 目的：尽量减少租户逻辑耦合到系统中
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface TenantMenuHandler {

    /**
     * 基于传入的租户菜单【全】列表，进行相关逻辑的执行
     * 例如说，返回可分配菜单的时候，可以移除多余的
     *
     * @param menuIds 菜单列表
     */
    void handle(Set<Long> menuIds);

}
