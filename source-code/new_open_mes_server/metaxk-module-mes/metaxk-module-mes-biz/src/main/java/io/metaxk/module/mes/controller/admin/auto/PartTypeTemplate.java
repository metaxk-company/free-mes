package io.metaxk.module.mes.controller.admin.auto;

import io.metaxk.module.mes.dal.dataobject.auto.AutoCodePart;

public interface PartTypeTemplate {
    /**
     * 分段的处理规则
     * @param sysAutoCodePart
     * @return
     */
    String partHandle(AutoCodePart sysAutoCodePart);
}

