package io.metaxk.module.mes.service.auto;

import io.metaxk.module.mes.dal.dataobject.auto.AutoCodeResult;
import java.util.List;

/**
 * 编码生成记录 Service 接口
 * @author 万界星空
 */
public interface AutoCodeResultService {

    /**
     * 根据条件查询当前的记录表中是否有满足指定条件的记录
     * gen_date条件要以like 'xxx%' 方式查询
     * @param sysAutoCodeResult
     * @return
     */
    public List<AutoCodeResult> list(AutoCodeResult sysAutoCodeResult);

    /**
     * 新增编码生成记录
     * @param sysAutoCodeResult
     */
    public void saveAutoCodeResult(AutoCodeResult sysAutoCodeResult);

    /**
     * 修改编码生成记录
     * @param sysAutoCodeResult
     */
    public void updateAutoCodeResult(AutoCodeResult sysAutoCodeResult);
}
