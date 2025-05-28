package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.module.mes.controller.admin.order.vo.InventoryStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/27 15:31
 */
@Mapper
public interface InventoryStatisticsQueryVOMapper extends BaseMapperX<InventoryStatisticsVO> {

   @Select("<script>" +
           "select wl.line_type, wl.model, wl.spec, wl.color, wl.reel_number, wl.total_height as total_height, wl.pieces as labelPieces, ol.total_height as total_weight, ol.pieces as outboundPieces, wl.date " +
           "from wh_label as wl INNER JOIN order_outbound_item_label as ol ON wl.id = ol.wh_label_id " +
           "WHERE ol.create_time <![CDATA[ >= ]]> #{startDate} AND ol.create_time <![CDATA[ <= ]]> #{endDate} " +
           "<if test=\"lineType != null and lineType != ''\">AND wl.line_type = #{lineType} </if>" +
           "<if test=\"model != null and model != ''\">AND wl.model like CONCAT('%', #{model}, '%') </if>" +
           "<if test=\"spec != null and spec != ''\">AND wl.spec like CONCAT('%', #{spec}, '%') </if>" +
           "<if test=\"color != null and color != ''\">AND wl.color like CONCAT('%', #{color}, '%') </if>" +
           "<if test=\"reelNumber != null and reelNumber != ''\">AND wl.reel_number like CONCAT('%', #{reel_number}, '%') </if>" +
           "LIMIT ${(pageNo - 1) * pageSize}, #{pageSize}" +
           "</script>")
   List<InventoryStatisticsVO> inventoryStatistics(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("lineType") String lineType, @Param("model") String model, @Param("spec") String spec, @Param("color") String color, @Param("reelNumber") String reelNumber, @Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

}
