package io.metaxk.module.infra.convert.job;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.controller.admin.job.vo.job.JobCreateReqVO;
import io.metaxk.module.infra.controller.admin.job.vo.job.JobExcelVO;
import io.metaxk.module.infra.controller.admin.job.vo.job.JobRespVO;
import io.metaxk.module.infra.controller.admin.job.vo.job.JobUpdateReqVO;
import io.metaxk.module.infra.dal.dataobject.job.JobDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 定时任务 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface JobConvert {

    JobConvert INSTANCE = Mappers.getMapper(JobConvert.class);

    JobDO convert(JobCreateReqVO bean);

    JobDO convert(JobUpdateReqVO bean);

    JobRespVO convert(JobDO bean);

    List<JobRespVO> convertList(List<JobDO> list);

    PageResult<JobRespVO> convertPage(PageResult<JobDO> page);

    List<JobExcelVO> convertList02(List<JobDO> list);

}
