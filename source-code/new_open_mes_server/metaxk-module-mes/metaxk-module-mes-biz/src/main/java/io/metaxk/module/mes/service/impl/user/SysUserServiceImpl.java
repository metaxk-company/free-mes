package io.metaxk.module.mes.service.impl.user;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.user.vo.UserPageReqVo;
import io.metaxk.module.mes.dal.dataobject.user.SysUser;
import io.metaxk.module.mes.dal.mysql.user.SysUserMapper;
import io.metaxk.module.mes.service.user.SysUserService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 用户实现类
 * @author 万界星空
 * @time 2023/6/27 17:54
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;



    @Override
    public PageResult<SysUser> selectPage(UserPageReqVo userPageReqVO) {
        return sysUserMapper.getSysUserPage(userPageReqVO);
    }


    @Override
    public SysUser findUserByIdAndName(String personCode, String userName) {
        LambdaQueryWrapperX<SysUser> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(SysUser::getId,personCode);
        queryWrapperX.eq(SysUser::getUserName,userName);
        return sysUserMapper.selectOne(queryWrapperX);
    }

    @Override
    public Integer updateSysUser(SysUser sysUser) {
        return sysUserMapper.updateById(sysUser);
    }

    @Override
    public SysUser findUserById(Long id) {
        return sysUserMapper.selectById(id);
    }
}
