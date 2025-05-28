package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.DetailedInventoryResponseVO;
import io.metaxk.module.mes.controller.admin.order.vo.LabelQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.Label;
import io.metaxk.module.mes.dal.dataobject.order.OutboundRecord;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/27 15:31
 */
@Mapper
public interface LabelMapper extends BaseMapperX<Label> {


    default PageResult<Label> findPage(LabelQueryVo queryVo){
        LambdaQueryWrapperX<Label> queryWrapperX = new LambdaQueryWrapperX<>();

        if(queryVo != null) {


            if (StringUtils.isNotBlank(queryVo.getModel())) {
                queryWrapperX.eq(Label::getModel, queryVo.getModel());
            }
            if (StringUtils.isNotBlank(queryVo.getPalletNumber())) {
                queryWrapperX.eq(Label::getPalletNumber, queryVo.getPalletNumber());
            }
            if (StringUtils.isNotBlank(queryVo.getSpec())) {
                queryWrapperX.eq(Label::getSpec, queryVo.getSpec());
            }
            if (StringUtils.isNotBlank(queryVo.getClientCode())) {
                queryWrapperX.eq(Label::getClientCode, queryVo.getClientCode());
            }
            if (StringUtils.isNotBlank(queryVo.getStatus())) {
                queryWrapperX.eq(Label::getStatus, queryVo.getStatus());
            }
            if (StringUtils.isNotBlank(queryVo.getBarCode())) {
                queryWrapperX.eq(Label::getBarCode, queryVo.getBarCode());
            }
            if (queryVo.getIds() != null) {
                queryWrapperX.notIn(Label::getId, queryVo.getIds());
            }
            if (StringUtils.isNotBlank(queryVo.getLineType())) {
                queryWrapperX.eq(Label::getLineType, queryVo.getLineType());
            }
            if (StringUtils.isNotBlank(queryVo.getReelNumber())) {
                queryWrapperX.eq(Label::getReelNumber, queryVo.getReelNumber());
            }
            if (StringUtils.isNotBlank(queryVo.getColor())) {
                queryWrapperX.eq(Label::getColor, queryVo.getColor());
            }
            if (StringUtils.isNotBlank(queryVo.getBoxNumber())) {
                queryWrapperX.eq(Label::getBoxNumber, queryVo.getBoxNumber());
            }
            if (StringUtils.isNotBlank(queryVo.getStartDate())) {
                queryWrapperX.gt(Label::getDate, queryVo.getStartDate());
            }
            if (StringUtils.isNotBlank(queryVo.getEndDate())) {
                queryWrapperX.lt(Label::getDate, queryVo.getEndDate());
            }
            if (queryVo.getIds() == null
                    && StringUtils.isBlank(queryVo.getSpec())
                    && StringUtils.isBlank(queryVo.getModel())
                    && StringUtils.isBlank(queryVo.getClientCode())
                    && StringUtils.isBlank(queryVo.getSpec())
                    && StringUtils.isBlank(queryVo.getBarCode())
                    && StringUtils.isBlank(queryVo.getLineType())
                    && StringUtils.isBlank(queryVo.getReelNumber())
                    && StringUtils.isBlank(queryVo.getColor())
                    && StringUtils.isBlank(queryVo.getBoxNumber())
                    && StringUtils.isBlank(queryVo.getStartDate())
                    && StringUtils.isBlank(queryVo.getPalletNumber())
                    && StringUtils.isBlank(queryVo.getEndDate())) {
                queryWrapperX.isNotNull(Label::getId);
            }
        }
        return  selectPage(queryVo,queryWrapperX);
    }

    default List<Label> findLabelList(Long id){
        LambdaQueryWrapperX<Label> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Label::getId,id);
        return selectList(queryWrapperX);
    }

    @Select("select * from wh_label where status = #{status}")
    List<Label> findLabelByStatus(String status);

   default List<Label> findLabelByCode(String code){
       return selectList(new LambdaQueryWrapperX<Label>().eq(Label::getBarCode,code).eq(Label::getStatus,"1"));
   }

  default   List<Label> findLabelByPanHao(String panhao){
      return selectList(new LambdaQueryWrapperX<Label>().eq(Label::getPalletNumber,panhao).eq(Label::getStatus,"1"));
  }

   default PageResult<Label> findRepackagedListPage(LabelQueryVo queryVo){
       LambdaQueryWrapperX<Label> queryWrapperX = new LambdaQueryWrapperX<>();
       if(queryVo != null) {
           if (StringUtils.isNotBlank(queryVo.getModel())) {
               queryWrapperX.eq(Label::getModel, queryVo.getModel());
           }
           if (StringUtils.isNotBlank(queryVo.getSpec())) {
               queryWrapperX.eq(Label::getSpec, queryVo.getSpec());
           }
           if (StringUtils.isNotBlank(queryVo.getClientCode())) {
               queryWrapperX.eq(Label::getClientCode, queryVo.getClientCode());
           }
           if (StringUtils.isNotBlank(queryVo.getStatus())) {
               queryWrapperX.eq(Label::getStatus, queryVo.getStatus());
           }
           if (StringUtils.isNotBlank(queryVo.getBarCode())) {
               queryWrapperX.eq(Label::getBarCode, queryVo.getBarCode());
           }
           if (queryVo.getIds() != null) {
               queryWrapperX.notIn(Label::getId, queryVo.getIds());
           }
           if (StringUtils.isNotBlank(queryVo.getLineType())) {
               queryWrapperX.eq(Label::getLineType, queryVo.getLineType());
           }
           if (StringUtils.isNotBlank(queryVo.getReelNumber())) {
               queryWrapperX.eq(Label::getReelNumber, queryVo.getReelNumber());
           }
           if (StringUtils.isNotBlank(queryVo.getColor())) {
               queryWrapperX.eq(Label::getColor, queryVo.getColor());
           }
           if (StringUtils.isNotBlank(queryVo.getBoxNumber())) {
               queryWrapperX.eq(Label::getBoxNumber, queryVo.getBoxNumber());
           }
           if (StringUtils.isNotBlank(queryVo.getStartDate())) {
               queryWrapperX.gt(Label::getDate, queryVo.getStartDate());
           }
           if (StringUtils.isNotBlank(queryVo.getEndDate())) {
               queryWrapperX.lt(Label::getDate, queryVo.getEndDate());
           }
           if (queryVo.getIds() == null && StringUtils.isBlank(queryVo.getSpec()) && StringUtils.isBlank(queryVo.getModel()) && StringUtils.isBlank(queryVo.getClientCode())
              && StringUtils.isBlank(queryVo.getSpec()) && StringUtils.isBlank(queryVo.getBarCode()) && StringUtils.isBlank(queryVo.getLineType())
              && StringUtils.isBlank(queryVo.getReelNumber()) && StringUtils.isBlank(queryVo.getColor())
              && StringUtils.isBlank(queryVo.getBoxNumber()) && StringUtils.isBlank(queryVo.getStartDate()) && StringUtils.isBlank(queryVo.getEndDate())) {
               queryWrapperX.isNotNull(Label::getId);
           }
           queryWrapperX.eq(Label::getStatus,"3");
       }
       return  selectPage(queryVo,queryWrapperX);
   }


    OutboundRecord countRepackage();


   @Select("SELECT spec, reel_number, color, sum(total_height) as total_height " +
           "FROM wh_label " +
           "WHERE line_type = #{lineType} and model = #{model} and spec= #{spec} and status = '1' " +
           "GROUP BY reel_number, color;")
    List<DetailedInventoryResponseVO> detailedInventory(@Param("lineType") String lineType, @Param("model") String model, @Param("spec") String spec);












/*
   @Select("<script> select wl.line_type, wl.model, wl.spec, wl.color, wl.reel_number, wl.total_height as total_height, wl.pieces as labelPieces, ol.total_height as total_weight, ol.pieces as outboundPieces, wl.date " +
           "from wh_label as wl INNER JOIN order_outbound_item_label as ol ON wl.id = ol.wh_label_id " +
           "WHERE wl.date <![CDATA[ >= ]]> #{startDate} AND wl.date <![CDATA[ <= ]]> #{endDate} " +
           "<if test='lineType != null'>AND wl.line_type = #{lineType} </if>" +
           "<if test='model != null'>AND wl.model = #{model} </if>" +
           "<if test='spec != null'>AND wl.spec = #{spec} </if>" +
           "<if test='color != null'>AND wl.color = #{color} </if>" +
           "<if test='reelNumber != null'>AND wl.reel_number = #{reelNumber} </if>" +
           "LIMIT #{pageNO}*#{pageSize}-1, #{pageSize}" +
           "</script>")
   List<InventoryStatisticsVO> inventoryStatistics(String startDate, String endDate, String lineType, String model, String spec, String color, String reelNumber, int pageNO, int pageSize);
*/

}
