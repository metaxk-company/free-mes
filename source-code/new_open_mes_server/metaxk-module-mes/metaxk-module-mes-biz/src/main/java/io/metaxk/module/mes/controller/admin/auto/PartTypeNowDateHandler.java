package io.metaxk.module.mes.controller.admin.auto;
import io.metaxk.module.mes.dal.dataobject.auto.AutoCodePart;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Order(1)
public class PartTypeNowDateHandler implements PartTypeTemplate {

    @Override
    public String partHandle(AutoCodePart sysAutoCodePart) {
        String formatDate = sysAutoCodePart.getDateFormat();
        return DateTimeFormatter.ofPattern(formatDate).format(LocalDateTime.now());
    }
}
