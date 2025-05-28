package io.metaxk.module.infra.convert.file;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.controller.admin.file.vo.config.FileConfigCreateReqVO;
import io.metaxk.module.infra.controller.admin.file.vo.config.FileConfigRespVO;
import io.metaxk.module.infra.controller.admin.file.vo.config.FileConfigUpdateReqVO;
import io.metaxk.module.infra.dal.dataobject.file.FileConfigDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 文件配置 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface FileConfigConvert {

    FileConfigConvert INSTANCE = Mappers.getMapper(FileConfigConvert.class);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigCreateReqVO bean);

    @Mapping(target = "config", ignore = true)
    FileConfigDO convert(FileConfigUpdateReqVO bean);

    FileConfigRespVO convert(FileConfigDO bean);

    List<FileConfigRespVO> convertList(List<FileConfigDO> list);

    PageResult<FileConfigRespVO> convertPage(PageResult<FileConfigDO> page);

}
