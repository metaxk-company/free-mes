package io.metaxk.module.mes.dal.mysql.pro;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.pro.vo.FeedbackQueryVo;
import io.metaxk.module.mes.dal.dataobject.pro.Feedback;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.Arrays;


/**
 * 生产报工 Mapper
 * @author 万界星空
 */
@Mapper
public interface FeedbackMapper extends BaseMapperX<Feedback> {


    /**
     * 生产报工条件分页查询
     * @param reqVO
     * @return PageResult<Feedback>
     */
     default PageResult<Feedback> selectPage(FeedbackQueryVo reqVO) {
        LambdaQueryWrapperX<Feedback> queryWrapper  = new LambdaQueryWrapperX<>();
         if(StringUtils.isNotBlank(reqVO.getWorkorderCode())){
             queryWrapper.eq(Feedback::getWorkorderCode,reqVO.getWorkorderCode());
         }
         if(StringUtils.isNotBlank(reqVO.getTaskCode())){
             queryWrapper.eq(Feedback::getTaskCode,reqVO.getTaskCode());
         }
         if(StringUtils.isNotBlank(reqVO.getItemName())){
             queryWrapper.like(Feedback::getItemName,reqVO.getItemName());
         }
         if(StringUtils.isNotBlank(reqVO.getUserName())){
             queryWrapper.eq(Feedback::getUserName,reqVO.getUserName());
         }
         if(StringUtils.isNotBlank(reqVO.getStatus())){
             queryWrapper.eq(Feedback::getStatus,reqVO.getStatus());
         }
         if( StringUtils.isBlank(reqVO.getWorkorderCode()) &&
         StringUtils.isBlank(reqVO.getTaskCode()) && StringUtils.isBlank(reqVO.getItemName()) && StringUtils.isBlank(reqVO.getUserName())
         && StringUtils.isBlank(reqVO.getStatus())){
             queryWrapper.isNotNull(Feedback::getId);
         }

         String assignUsername = reqVO.getAssignUsername();
         if (StringUtils.isNotBlank(assignUsername)) {
             if ("admin".equals(assignUsername)) {
                 queryWrapper.isNotNull(Feedback::getAssignUsername);
             } else {
                 String[] usernames = assignUsername.split("#");
                 queryWrapper.and(wrapper ->
                         Arrays.stream(usernames).forEach(username ->
                                 wrapper.or().like(Feedback::getAssignUsername, "%" + username + "%")
                         )
                 );
             }
         }
         return  selectPage(reqVO,queryWrapper);
    }


    /**
     * 根据任务编号查询报工信息
     * @param taskCode
     * @return Feedback
     */
    default Feedback findFeedBackByTaskCode(String taskCode){
        LambdaQueryWrapperX<Feedback> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Feedback::getTaskCode,taskCode);
        return  selectOne(queryWrapperX);

    }

    /**
     * 根据报工编号查询报工信息
     * @param feedbackCode
     * @return Feedback
     */
   default Feedback selectFeedBackByFeedBackId(Long feedbackCode){
       LambdaQueryWrapperX<Feedback> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(Feedback::getId,feedbackCode);
       return  selectOne(queryWrapperX);
   }


}
