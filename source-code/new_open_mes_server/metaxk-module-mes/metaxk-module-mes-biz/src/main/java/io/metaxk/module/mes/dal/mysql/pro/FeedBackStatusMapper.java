package io.metaxk.module.mes.dal.mysql.pro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.EquationTimeVo;
import io.metaxk.module.mes.dal.dataobject.pro.FeedbackStatus;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;



/**
 * 生产报工状态 Mapper
 * @author 万界星空
 */
@Mapper
public interface FeedBackStatusMapper extends BaseMapperX<FeedbackStatus> {

    /**
     * 根据任务编号查询报工状态
     * @param taskCode
     * @return List<FeedbackStatus>
     */
    default  List<FeedbackStatus> findFeedBackStatusByTaskCode(String taskCode){
        LambdaQueryWrapperX<FeedbackStatus> queryWrapper = new LambdaQueryWrapperX<>();
        queryWrapper.eq(FeedbackStatus::getTaskCode,taskCode);
        return selectList(queryWrapper);
    }


    /**
     * 根据任务编号查询报工状态
     * @param taskCode
     * @return List<FeedbackStatus>
     */
    default   List<FeedbackStatus> selectProFeedbackStatusByTaskCode(String taskCode){
        LambdaQueryWrapperX<FeedbackStatus> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(FeedbackStatus::getTaskCode,taskCode);
        return  selectList(queryWrapperX);
    }


    /**
     * 根据任务编号计算时间
     * @param taskCode
     * @return EquationTimeVo
     */
    EquationTimeVo matrixingTime(String taskCode);

    /**
     * 根据任务编号和状态查询时间
     * @param taskCode
     * @param start
     * @return String
     */
    default   String findTimeByPausedStatusStart(String taskCode, String start){
        QueryWrapper<FeedbackStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("time")
                .eq("task_code", taskCode)
                .eq("status", start)
                .orderByDesc("time")
                .last("LIMIT 1");
        FeedbackStatus result = selectOne(queryWrapper);
        return result == null ? null : result.getTime().toString();
    }




    /**
     * 根据任务编号和状态查询时间
     * @param taskCode
     * @param paused
     * @return String
     */
    default   String findTimeByPausedStatus(String taskCode, String paused){
        QueryWrapper<FeedbackStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("time")
                .eq("task_code", taskCode)
                .eq("status", paused)
                .orderByDesc("time")
                .last("LIMIT 1");
        FeedbackStatus result = selectOne(queryWrapper);
        return result == null ? null : result.getTime().toString();
    }


    /**
     * 根据任务编号和状态查询时间
     * @param taskCode
     * @param resumed
     * @return String
     */
    default   String findTimeByPausedStatusResumed(String taskCode, String resumed){
        QueryWrapper<FeedbackStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("time")
                .eq("task_code", taskCode)
                .eq("status", resumed)
                .orderByDesc("time")
                .last("LIMIT 1");
        FeedbackStatus result = selectOne(queryWrapper);
        return result == null ? null : result.getTime().toString();
    }

    /**
     * 根据任务编号和状态查询时间
     * @param taskCode
     * @param finished
     * @return String
     */
    default   String findTimeByPausedStatusFinished(String taskCode, String finished){
        QueryWrapper<FeedbackStatus> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("time")
                .eq("task_code", taskCode)
                .eq("status", finished)
                .orderByDesc("time")
                .last("LIMIT 1");
        FeedbackStatus result = selectOne(queryWrapper);
        return result == null ? null : result.getTime().toString();
    }


    /**
     * 根据任务编号查询报工状态
     * @param taskCode
     * @return FeedbackStatus
     */
  default   FeedbackStatus findFeedBackStatusByTaskCodes(String taskCode){
      LambdaQueryWrapperX<FeedbackStatus> queryWrapperX = new LambdaQueryWrapperX<>();
      queryWrapperX.eq(FeedbackStatus::getTaskCode,taskCode);
      return  selectOne(queryWrapperX);
  }
}
