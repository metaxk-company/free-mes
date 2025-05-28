package io.metaxk.module.mes.controller.admin.auto;

import cn.hutool.core.collection.CollectionUtil;
import io.metaxk.module.mes.dal.dataobject.auto.AutoCodePart;
import io.metaxk.module.mes.dal.dataobject.auto.AutoCodeResult;
import io.metaxk.module.mes.service.auto.AutoCodeResultService;
import io.metaxk.module.mes.utils.AutoCodeUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Component
@Order(3)
public class PartTypeSerialNoHandler implements PartTypeTemplate {

    @Resource
    private AutoCodeResultService iAutoCodeResultService;

    @Override
    public String partHandle(AutoCodePart sysAutoCodePart) {

        if("Y".equals(sysAutoCodePart.getCycleFlag())){
            return cycleY(sysAutoCodePart);
        }else{
            return cycleN(sysAutoCodePart);
        }
    }


    private  String cycleY(AutoCodePart sysAutoCodePart){
        String method = sysAutoCodePart.getCycleMethod();
        String param ="";
        if(CycleMethodMnum.CYCLE_METHOD_OTHER.getCode().equals(method)){
            param = sysAutoCodePart.getInputCharacter();
        }else{
            switch (CycleMethodMnum.getByCode(method)){
                case CYCLE_METHOD_YEAR:
                    param = cn.hutool.core.date.DateUtil.format(new Date(),"yyyy");
                    break;
                case CYCLE_METHOD_MONTH:
                    param = cn.hutool.core.date.DateUtil.format(new Date(),"yyyyMM");
                    break;
                case CYCLE_METHOD_DAY:
                    param = cn.hutool.core.date.DateUtil.format(new Date(),"yyyyMMdd");
                    break;
                case CYCLE_METHOD_HOUR:
                    param = cn.hutool.core.date.DateUtil.format(new Date(),"yyyyMMddHH");
                    break;
                case CYCLE_METHOD_MINUTE:
                    param = cn.hutool.core.date.DateUtil.format(new Date(),"yyyyMMddHHmm");
                    break;
                default:
                    break;
            }
        }

        List<AutoCodeResult> rs = getAutoCodeResult(sysAutoCodePart.getRuleId(),param,method);
        if(CollectionUtil.isNotEmpty(rs)){
            //如果在编码记录表中有记录，则在最后一个流水号上加上步长，返回新的流水号
            AutoCodeUtil.threadLocal.set(false);
            Integer lastSerialNo = rs.get(0).getLastSerialNo();
            return String.format("%0"+sysAutoCodePart.getPartLength()+"d",lastSerialNo+sysAutoCodePart.getSeriaStep());
        }else {
            //如果在编码记录表中不存在，则直接返回起始流水号
            AutoCodeUtil.threadLocal.set(true);
            return String.format("%0"+sysAutoCodePart.getPartLength()+"d",sysAutoCodePart.getSeriaStartNo());
        }
    }

    //不循环方式
    private String cycleN(AutoCodePart sysAutoCodePart){
        List<AutoCodeResult> rs = getAutoCodeResult(sysAutoCodePart.getRuleId(),"","");
        if(CollectionUtil.isNotEmpty(rs)){
            //存在记录则在当前记录加上步长
            Integer lastSerialNo = rs.get(0).getLastSerialNo();
            return String.format("%0"+sysAutoCodePart.getPartLength()+"d",lastSerialNo+sysAutoCodePart.getSeriaStep());
        }else{
            //不存在记录则从头开始
            AutoCodeUtil.threadLocal.set(true);
            return String.format("%0"+sysAutoCodePart.getPartLength()+"d",sysAutoCodePart.getSeriaStartNo());
        }

    }

    //从编码结果记录表中查找当前指定循环规则的流水号记录
    private List<AutoCodeResult> getAutoCodeResult(Long ruleId, String param, String cycleMethod){
        AutoCodeResult queryParam = new AutoCodeResult();
        queryParam.setRuleId(ruleId);//ruleId要一致

        if(CycleMethodMnum.CYCLE_METHOD_OTHER.getCode().equals(cycleMethod)){
            //如果循环方式是手工输入指定的字符
            queryParam.setLastInputChar(param);
        }else{
            //如果循环方式是按格式化的日期
            queryParam.setGenDate(param);//这里的param将按照 gen_date like #{param}+'%' 的方式进行模糊查询，数据库中记录的永远都是yyyMMddHHmmss格式的
        }
        return iAutoCodeResultService.list(queryParam);
    }
}
