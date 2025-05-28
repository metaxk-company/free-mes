package io.metaxk.module.infra.convert.test;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.controller.admin.test.vo.TestDemoCreateReqVO;
import io.metaxk.module.infra.controller.admin.test.vo.TestDemoExcelVO;
import io.metaxk.module.infra.controller.admin.test.vo.TestDemoRespVO;
import io.metaxk.module.infra.controller.admin.test.vo.TestDemoUpdateReqVO;
import io.metaxk.module.infra.dal.dataobject.test.TestDemoDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 字典类型 Convert
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Mapper
public interface TestDemoConvert {

    TestDemoConvert INSTANCE = Mappers.getMapper(TestDemoConvert.class);

    TestDemoDO convert(TestDemoCreateReqVO bean);

    TestDemoDO convert(TestDemoUpdateReqVO bean);

    TestDemoRespVO convert(TestDemoDO bean);

    List<TestDemoRespVO> convertList(List<TestDemoDO> list);

    PageResult<TestDemoRespVO> convertPage(PageResult<TestDemoDO> page);

    List<TestDemoExcelVO> convertList02(List<TestDemoDO> list);

}
