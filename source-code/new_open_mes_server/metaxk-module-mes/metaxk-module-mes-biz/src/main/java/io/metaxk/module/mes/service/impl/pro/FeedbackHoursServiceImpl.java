package io.metaxk.module.mes.service.impl.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackHoursExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackHoursVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackHours;
import io.metaxk.module.mes.dal.mysql.pro.FeedbackHoursMapper;
import io.metaxk.module.mes.service.pro.FeedbackHoursService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


@Service
public class FeedbackHoursServiceImpl  implements FeedbackHoursService {
    @Resource
    private FeedbackHoursMapper feedbackHoursMapper;



    @Override
    public void removeFeedBackHoursById(Long id) {
        QueryWrapper<FeedbackHours> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("feedback_id",id);
        feedbackHoursMapper.delete(queryWrapper);
    }

    @Override
    public PageResult<FeedbackHours> list(FeedbackHoursVo feedbackHoursVO) throws ParseException {
        return feedbackHoursMapper.list(feedbackHoursVO);
    }




    @Override
    public List<FeedbackHoursExcelVo> listData(List<Long> ids) {
        List<FeedbackHours> dictList;
        if (ids != null && !ids.isEmpty()) {
            dictList = feedbackHoursMapper.selectBatchIds(ids);
        } else {
            dictList = feedbackHoursMapper.selectList(null);
        }
        ArrayList<FeedbackHoursExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            FeedbackHoursExcelVo excelDictDTO = new FeedbackHoursExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

}
