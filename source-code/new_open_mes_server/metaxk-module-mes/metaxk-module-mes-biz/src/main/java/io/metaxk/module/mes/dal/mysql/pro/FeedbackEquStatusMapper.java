package io.metaxk.module.mes.dal.mysql.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.pro.EquipmentTimeVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackEquStatus;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 报工设备状态 Mapper
 * @author 万界星空
 */
@Mapper
public interface FeedbackEquStatusMapper extends BaseMapperX<FeedbackEquStatus> {

    /**
     * 计算报工设备时间
     * @param id
     * @return EquipmentTimeVo
     */
    EquipmentTimeVo findTimeByFeedEqipId(Long id);


    /**
     * 根据设备状态id查询报工设备状态
     * @param equipmentStatusId
     * @return FeedbackEquStatus
     */
    FeedbackEquStatus findEquipmentStatusByfeedStatusId(Long equipmentStatusId);

    /**
     * 根据id查询报工设备状态
     * @param id
     * @return List<FeedbackEquStatus>
     */
   default  List<FeedbackEquStatus> selectEquipmentStatusByfeedStatusId(Long id){
       LambdaQueryWrapperX<FeedbackEquStatus> queryWrapper = new LambdaQueryWrapperX<>();
       queryWrapper.eq(FeedbackEquStatus::getId,id);
       return  selectList(queryWrapper);
   }

    /**
     * 根据id以及状态查询报工设备状态的时间
     * @param id
     * @param start
     * @return String
     */
   default String findTimeByPausedStatusStart(Long id, String start){
       QueryWrapper<FeedbackEquStatus> queryWrapper = new QueryWrapper<>();
       queryWrapper.select("time")
               .eq("id", id)
               .eq("status", start)
               .orderByDesc("time")
               .last("LIMIT 1");
       FeedbackEquStatus result = selectOne(queryWrapper);
       return result == null ? null : result.getTime().toString();
   }

    /**
     * 根据id以及状态查询报工设备状态的时间
     * @param id
     * @param paused
     * @return String
     */
   default String findTimeByPausedStatus(Long id, String paused){
       QueryWrapper<FeedbackEquStatus> queryWrapper = new QueryWrapper<>();
       queryWrapper.select("time")
               .eq("id", id)
               .eq("status", paused)
               .orderByDesc("time")
               .last("LIMIT 1");
       FeedbackEquStatus result = selectOne(queryWrapper);
       return result == null ? null : result.getTime().toString();
   }

    /**
     * 根据id以及状态查询报工设备状态的时间
     * @param id
     * @param resumed
     * @return String
     */
   default String findTimeByPausedStatusResumed(Long id, String resumed){
       QueryWrapper<FeedbackEquStatus> queryWrapper = new QueryWrapper<>();
       queryWrapper.select("time")
               .eq("id", id)
               .eq("status", resumed)
               .orderByDesc("time")
               .last("LIMIT 1");
       FeedbackEquStatus result = selectOne(queryWrapper);
       return result == null ? null : result.getTime().toString();
   }

    /**
     * 根据id以及状态查询报工设备状态的时间
     * @param id
     * @param finished
     * @return String
     */
   default String findTimeByPausedStatusFinished(Long id, String finished){
       QueryWrapper<FeedbackEquStatus> queryWrapper = new QueryWrapper<>();
       queryWrapper.select("time")
               .eq("id", id)
               .eq("status", finished)
               .orderByDesc("time")
               .last("LIMIT 1");
       FeedbackEquStatus result = selectOne(queryWrapper);
       return result == null ? null : result.getTime().toString();
   }

    /**
     * 据id以及状态查询报工设备状态的时间
     * @param id
     * @param status
     * @return FeedbackEquStatus
     */
   default FeedbackEquStatus findByIdAndStatus(Long id, String status){
       LambdaQueryWrapperX<FeedbackEquStatus> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(FeedbackEquStatus::getId,id);
       queryWrapperX.eq(FeedbackEquStatus::getStatus,status);
       return  selectOne(queryWrapperX);
   }
}
