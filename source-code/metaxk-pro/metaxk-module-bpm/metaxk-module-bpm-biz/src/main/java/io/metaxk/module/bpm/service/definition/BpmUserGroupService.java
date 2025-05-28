package io.metaxk.module.bpm.service.definition;

import java.util.*;
import javax.validation.*;

import io.metaxk.module.bpm.controller.admin.definition.vo.group.BpmUserGroupCreateReqVO;
import io.metaxk.module.bpm.controller.admin.definition.vo.group.BpmUserGroupPageReqVO;
import io.metaxk.module.bpm.controller.admin.definition.vo.group.BpmUserGroupUpdateReqVO;
import io.metaxk.module.bpm.dal.dataobject.definition.BpmUserGroupDO;
import io.metaxk.framework.common.pojo.PageResult;

/**
 * 用户组 Service 接口
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface BpmUserGroupService {

    /**
     * 创建用户组
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUserGroup(@Valid BpmUserGroupCreateReqVO createReqVO);

    /**
     * 更新用户组
     *
     * @param updateReqVO 更新信息
     */
    void updateUserGroup(@Valid BpmUserGroupUpdateReqVO updateReqVO);

    /**
     * 删除用户组
     *
     * @param id 编号
     */
    void deleteUserGroup(Long id);

    /**
     * 获得用户组
     *
     * @param id 编号
     * @return 用户组
     */
    BpmUserGroupDO getUserGroup(Long id);

    /**
     * 获得用户组列表
     *
     * @param ids 编号
     * @return 用户组列表
     */
    List<BpmUserGroupDO> getUserGroupList(Collection<Long> ids);

    /**
     * 获得指定状态的用户组列表
     *
     * @param status 状态
     * @return 用户组列表
     */
    List<BpmUserGroupDO> getUserGroupListByStatus(Integer status);

    /**
     * 获得用户组分页
     *
     * @param pageReqVO 分页查询
     * @return 用户组分页
     */
    PageResult<BpmUserGroupDO> getUserGroupPage(BpmUserGroupPageReqVO pageReqVO);

    /**
     * 校验用户组们是否有效。如下情况，视为无效：
     * 1. 用户组编号不存在
     * 2. 用户组被禁用
     *
     * @param ids 用户组编号数组
     */
    void validUserGroups(Set<Long> ids);

}
