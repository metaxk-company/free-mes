package io.metaxk.module.mes.service.dept;

import io.metaxk.module.mes.dal.dataobject.dept.SysDept;

import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/7 15:01
 */
public interface SysDeptService {

    List<SysDept> findDeptList();

    SysDept findDeptById(String department);

    SysDept findDeptByName(String department );
}
