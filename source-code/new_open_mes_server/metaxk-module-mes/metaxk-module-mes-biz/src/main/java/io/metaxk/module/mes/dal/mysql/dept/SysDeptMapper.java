package io.metaxk.module.mes.dal.mysql.dept;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.dept.SysDept;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 万界星空
 * @time 2023/7/7 15:01
 */
@Mapper
public interface SysDeptMapper extends BaseMapperX<SysDept> {

   default SysDept findDeptByName(String department){
       LambdaQueryWrapperX<SysDept> queryWrapperX = new LambdaQueryWrapperX<>();
       queryWrapperX.eq(SysDept::getName,department);
       return  selectOne(queryWrapperX);
   }


}
