package io.metaxk.module.infra.service.test;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.infra.controller.admin.test.vo.TestDemoCreateReqVO;
import io.metaxk.module.infra.controller.admin.test.vo.TestDemoExportReqVO;
import io.metaxk.module.infra.controller.admin.test.vo.TestDemoPageReqVO;
import io.metaxk.module.infra.controller.admin.test.vo.TestDemoUpdateReqVO;
import io.metaxk.module.infra.convert.test.TestDemoConvert;
import io.metaxk.module.infra.dal.dataobject.test.TestDemoDO;
import io.metaxk.module.infra.dal.mysql.test.TestDemoMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.infra.enums.ErrorCodeConstants.TEST_DEMO_NOT_EXISTS;

/**
 * 字典类型 Service 实现类
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@Service
@Validated
public class TestDemoServiceImpl implements TestDemoService {

    @Resource
    private TestDemoMapper testDemoMapper;

    @Override
    public Long createTestDemo(TestDemoCreateReqVO createReqVO) {
        // 插入
        TestDemoDO testDemo = TestDemoConvert.INSTANCE.convert(createReqVO);
        testDemoMapper.insert(testDemo);
        // 返回
        return testDemo.getId();
    }

    @Override
    @CacheEvict(value = "test", key = "#updateReqVO.id")
    public void updateTestDemo(TestDemoUpdateReqVO updateReqVO) {
        // 校验存在
        validateTestDemoExists(updateReqVO.getId());
        // 更新
        TestDemoDO updateObj = TestDemoConvert.INSTANCE.convert(updateReqVO);
        testDemoMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = "test", key = "#id")
    public void deleteTestDemo(Long id) {
        // 校验存在
        validateTestDemoExists(id);
        // 删除
        testDemoMapper.deleteById(id);
    }

    private void validateTestDemoExists(Long id) {
        if (testDemoMapper.selectById(id) == null) {
            throw exception(TEST_DEMO_NOT_EXISTS);
        }
    }

    @Override
    @Cacheable(cacheNames = "test", key = "#id")
    public TestDemoDO getTestDemo(Long id) {
        return testDemoMapper.selectById(id);
    }

    @Override
    public List<TestDemoDO> getTestDemoList(Collection<Long> ids) {
        return testDemoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TestDemoDO> getTestDemoPage(TestDemoPageReqVO pageReqVO) {
        testDemoMapper.selectList2();
        return testDemoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TestDemoDO> getTestDemoList(TestDemoExportReqVO exportReqVO) {
        return testDemoMapper.selectList(exportReqVO);
    }

}
