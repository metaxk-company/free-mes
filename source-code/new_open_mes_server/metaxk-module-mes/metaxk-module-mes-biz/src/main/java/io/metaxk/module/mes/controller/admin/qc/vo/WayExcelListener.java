package io.metaxk.module.mes.controller.admin.qc.vo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.metaxk.module.mes.dal.dataobject.qc.Way;
import io.metaxk.module.mes.dal.mysql.qc.WayMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;



/**
 * @author 万界星空
 * @time 2023/7/7 10:10
 */
@Slf4j
@NoArgsConstructor
public class WayExcelListener extends AnalysisEventListener<WayExcelVo> {



    private static final int BATCH_COUNT = 30000;

    List<WayExcelVo> list = new ArrayList<>();

    private WayMapper inspectMethodMapper;

    public WayExcelListener(WayMapper inspectMethodMapper) {
        this.inspectMethodMapper = inspectMethodMapper;
    }


    @Override
    public void invoke(WayExcelVo data, AnalysisContext context) {
        log.info("解析到一条记录: {}", data);
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }



    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        for(WayExcelVo inspectMethodExcelVO:list){
            Way inspectMethod = new Way();
            BeanUtils.copyProperties(inspectMethodExcelVO,inspectMethod);
            if(inspectMethodMapper.findMethodByCodeAndName(inspectMethod.getInspectCode(),inspectMethod.getInspectName()) == null) {
                inspectMethodMapper.insert(inspectMethod);
            }
        }
        log.info("存储数据库成功！");
    }

}
