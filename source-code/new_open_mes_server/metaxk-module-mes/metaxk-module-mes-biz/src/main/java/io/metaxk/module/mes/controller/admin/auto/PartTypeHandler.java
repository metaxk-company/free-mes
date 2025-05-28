package io.metaxk.module.mes.controller.admin.auto;
import io.metaxk.module.mes.dal.dataobject.auto.AutoCodePart;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.List;

@Component
public class PartTypeHandler {

    @Resource
    List<PartTypeTemplate> partTypeTemplates;


    public String choiceExecute(AutoCodePart sysAutoCodePart){
        String partType = sysAutoCodePart.getPartType();

        return partTypeTemplates.get(PartTypeEnum.getByCode(partType).getBeanIndex()).partHandle(sysAutoCodePart);
    }

}
