package io.metaxk.module.bpm.service.task;

import io.metaxk.module.bpm.controller.admin.task.vo.activity.BpmActivityRespVO;
import io.metaxk.module.bpm.convert.task.BpmActivityConvert;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;


/**
 * BPM 活动实例 Service 实现类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Service
@Slf4j
@Validated
public class BpmActivityServiceImpl implements BpmActivityService {

    @Resource
    private HistoryService historyService;

    @Override
    public List<BpmActivityRespVO> getActivityListByProcessInstanceId(String processInstanceId) {
        List<HistoricActivityInstance> activityList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).list();
        return BpmActivityConvert.INSTANCE.convertList(activityList);
    }

    @Override
    public List<HistoricActivityInstance> getHistoricActivityListByExecutionId(String executionId) {
        return historyService.createHistoricActivityInstanceQuery().executionId(executionId).list();
    }

}
