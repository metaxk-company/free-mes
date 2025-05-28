package io.metaxk.module.infra.convert.file;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.controller.admin.file.vo.file.FileRespVO;
import io.metaxk.module.infra.dal.dataobject.file.FileDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FileConvert {

    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);

    FileRespVO convert(FileDO bean);

    PageResult<FileRespVO> convertPage(PageResult<FileDO> page);

}
