package io.metaxk.module.mes.service.impl.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.pro.vo.TemporaryWorkHoursExcelVo;
import io.metaxk.module.mes.controller.admin.pro.vo.TemporaryWorkHoursVo;
import io.metaxk.module.mes.dal.dataobject.pro.TemporaryWorkHours;
import io.metaxk.module.mes.dal.mysql.pro.TemporaryWorkHoursMapper;
import io.metaxk.module.mes.service.pro.TemporaryWorkHoursService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



@Service
public class TemporaryWorkHoursServiceImpl implements TemporaryWorkHoursService {

    @Resource
    private TemporaryWorkHoursMapper temporaryWorkHoursMapper;

    @Override
    public PageResult<TemporaryWorkHours> list(TemporaryWorkHoursVo temporaryWorkHoursVO) {
        //将传入的结束时间加1，这样做的目的是为了查询包含结束时间的数据
        if(StringUtils.isNotBlank(temporaryWorkHoursVO.getEndTime())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime endTime = LocalDateTime.parse(temporaryWorkHoursVO.getEndTime(), formatter);
            LocalDateTime newEndTime = endTime.plusMinutes(1); // 在原来的时间上加1分钟
            String formattedNewEndTime = newEndTime.format(formatter); // 转化为字符串
            temporaryWorkHoursVO.setEndTime(formattedNewEndTime);
        }
        return temporaryWorkHoursMapper.list(temporaryWorkHoursVO);
    }



    @Override
    public Integer save(TemporaryWorkHours temporaryWorkHours) {
        temporaryWorkHours.setCreateTime(new Date());
        return temporaryWorkHoursMapper.insert(temporaryWorkHours);
    }

    @Override
    public Integer removeByIds(List<Long> ids) {
        return temporaryWorkHoursMapper.deleteBatchIds(ids);
    }


    @Override
    public TemporaryWorkHours findTemporaryWorkHoursById(Long id) {
        return temporaryWorkHoursMapper.selectById(id);
    }


    @Override
    public Integer update(TemporaryWorkHours temporaryWorkHours) {
        TemporaryWorkHours WorkHours = temporaryWorkHoursMapper.selectById(temporaryWorkHours.getId());
        temporaryWorkHours.setCreateTime(WorkHours.getCreateTime());
        temporaryWorkHours.setUpdateTime(new Date());
        return temporaryWorkHoursMapper.updateById(temporaryWorkHours);
    }


    //临时工时导出
    @Override
    public List<TemporaryWorkHoursExcelVo> listData(List<Long> ids) {
        List<TemporaryWorkHours> dictList;
        if (ids != null && !ids.isEmpty()) {
            dictList = temporaryWorkHoursMapper.selectBatchIds(ids);
        } else {
            dictList = temporaryWorkHoursMapper.selectList(null);
        }
        ArrayList<TemporaryWorkHoursExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            TemporaryWorkHoursExcelVo excelDictDTO = new TemporaryWorkHoursExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }


}
