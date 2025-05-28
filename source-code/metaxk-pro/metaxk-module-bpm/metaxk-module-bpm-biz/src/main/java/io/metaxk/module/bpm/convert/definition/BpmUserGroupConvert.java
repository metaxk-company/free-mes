package io.metaxk.module.bpm.convert.definition;

import java.util.*;

import io.metaxk.module.bpm.controller.admin.definition.vo.group.BpmUserGroupCreateReqVO;
import io.metaxk.module.bpm.controller.admin.definition.vo.group.BpmUserGroupRespVO;
import io.metaxk.module.bpm.controller.admin.definition.vo.group.BpmUserGroupUpdateReqVO;
import io.metaxk.module.bpm.dal.dataobject.definition.BpmUserGroupDO;
import io.metaxk.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * 用户组 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface BpmUserGroupConvert {

    BpmUserGroupConvert INSTANCE = Mappers.getMapper(BpmUserGroupConvert.class);

    BpmUserGroupDO convert(BpmUserGroupCreateReqVO bean);

    BpmUserGroupDO convert(BpmUserGroupUpdateReqVO bean);

    BpmUserGroupRespVO convert(BpmUserGroupDO bean);

    List<BpmUserGroupRespVO> convertList(List<BpmUserGroupDO> list);

    PageResult<BpmUserGroupRespVO> convertPage(PageResult<BpmUserGroupDO> page);

    @Named("convertList2")
    List<BpmUserGroupRespVO> convertList2(List<BpmUserGroupDO> list);

}
