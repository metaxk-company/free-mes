package io.metaxk.module.mes.service.impl.dept;

import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.dal.dataobject.dept.SysDept;
import io.metaxk.module.mes.dal.mysql.dept.SysDeptMapper;
import io.metaxk.module.mes.service.dept.SysDeptService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 万界星空
 * @time 2023/7/7 15:02
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {


    @Resource
    private SysDeptMapper  sysDeptMapper;

    @Override
    public List<SysDept> findDeptList() {
        return sysDeptMapper.selectList();
    }

    @Override
    public SysDept findDeptById(String department) {
        LambdaQueryWrapperX<SysDept> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(SysDept::getId,department);
        return sysDeptMapper.selectOne(queryWrapperX);
    }

    @Override
    public SysDept findDeptByName(String department ) {
        LambdaQueryWrapperX<SysDept> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(SysDept::getName,department);
        queryWrapperX.last("LIMIT 1");
        return sysDeptMapper.selectOne(queryWrapperX);
    }


}
