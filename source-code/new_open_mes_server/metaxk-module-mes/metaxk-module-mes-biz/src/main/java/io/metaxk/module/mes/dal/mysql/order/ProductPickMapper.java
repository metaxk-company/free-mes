package io.metaxk.module.mes.dal.mysql.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickAllExportVo;
import io.metaxk.module.mes.controller.admin.order.vo.ProductPickQueryVo;
import io.metaxk.module.mes.dal.dataobject.order.ProductPick;
import io.metaxk.module.mes.utils.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/21 14:11
 */
@Mapper
public interface ProductPickMapper extends BaseMapperX<ProductPick> {

    default PageResult<ProductPick> findPage(ProductPickQueryVo queryVo){
        LambdaQueryWrapperX<ProductPick> queryWrapperX = new LambdaQueryWrapperX<>();
        if(StringUtils.isNotBlank(queryVo.getNumber())){
            queryWrapperX.eq(ProductPick::getNumber,queryVo.getNumber());
        }

        if(StringUtils.isNotBlank(queryVo.getStartTime())){
            LocalDate requestDate = LocalDate.parse(queryVo.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.ge(ProductPick::getPickDate, requestDate);
        }

        if(StringUtils.isNotBlank(queryVo.getEndTime())){
            LocalDate requestDate = LocalDate.parse(queryVo.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            queryWrapperX.le(ProductPick::getPickDate, requestDate);
        }


        if(StringUtils.isBlank(queryVo.getNumber()) && StringUtils.isBlank(queryVo.getStartTime())
                && StringUtils.isBlank(queryVo.getEndTime())){
            queryWrapperX.isNotNull(ProductPick::getId);
        }
        return  selectPage(queryVo,queryWrapperX);
    }

       default ProductPick findProductPickByNum(String number){
            return  selectOne(new LambdaQueryWrapperX<ProductPick>().eq(ProductPick::getNumber,number));
       }

    @Select("<script>" +
            "SELECT opp.number,opp.pick_date,opp.product_type,opp.create_time,oppi.item_code,oppi.item_name,oppi.model,oppi.spec,oppi.kind,oppi.unit_of_measure,oppi.quantity\n" +
            "FROM order_product_pick AS opp\n" +
            "LEFT JOIN order_product_pick_item AS oppi ON opp.number = oppi.pick_number\n" +
            "<if test='ids.size() != 0'>" +
            "WHERE opp.id IN\n" +
            "<foreach collection='ids' index='index' item='id' open='(' separator=',' close=')'>#{id}</foreach>\n" +
            "</if>" +
            "</script>")
    List<ProductPickAllExportVo> exportAllByIds(@Param("ids") List<Integer> ids);
}
