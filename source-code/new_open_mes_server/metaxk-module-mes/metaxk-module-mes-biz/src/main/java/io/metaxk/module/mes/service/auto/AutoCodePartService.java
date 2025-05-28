package io.metaxk.module.mes.service.auto;


import io.metaxk.module.mes.dal.dataobject.auto.AutoCodePart;
import java.util.List;


/**
 * 编码生成规则组成 Service 接口
 *
 * @author 万界星空
 */
public interface AutoCodePartService {

     /**
      *  编码生成规则组成列表
      * @param sysAutoCodePart
      * @return List<AutoCodePart>
      */
     public List<AutoCodePart> listPart(AutoCodePart sysAutoCodePart);

     /**
      *  根据id查询编码生成规则组成
      * @param partId
      * @return AutoCodePart
      */
     public AutoCodePart findById(Long partId);

     /**
      * 校验编码生成规则组成
      * @param sysAutoCodePart
      * @return String
      */
     public String checkPartUnique(AutoCodePart sysAutoCodePart);


     /**
      * 新增编码生成规则组成
      * @param sysAutoCodePart
      * @return Integer
      */
     public Integer insertPart(AutoCodePart sysAutoCodePart);



}
