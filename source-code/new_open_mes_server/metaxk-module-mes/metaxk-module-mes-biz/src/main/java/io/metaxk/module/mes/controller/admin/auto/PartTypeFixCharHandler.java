package io.metaxk.module.mes.controller.admin.auto;

import io.metaxk.module.mes.dal.dataobject.auto.AutoCodePart;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class PartTypeFixCharHandler implements PartTypeTemplate {
    @Override
    public String partHandle(AutoCodePart sysAutoCodePart) {
        return sysAutoCodePart.getFixCharacter();
    }
}
