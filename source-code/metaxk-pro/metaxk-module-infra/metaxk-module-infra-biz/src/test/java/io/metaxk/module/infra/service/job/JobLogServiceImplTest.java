package io.metaxk.module.infra.service.job;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.test.core.ut.BaseDbUnitTest;
import io.metaxk.module.infra.controller.admin.job.vo.log.JobLogExportReqVO;
import io.metaxk.module.infra.controller.admin.job.vo.log.JobLogPageReqVO;
import io.metaxk.module.infra.dal.dataobject.job.JobLogDO;
import io.metaxk.module.infra.dal.mysql.job.JobLogMapper;
import io.metaxk.module.infra.enums.job.JobLogStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static io.metaxk.framework.common.util.date.LocalDateTimeUtils.buildTime;
import static io.metaxk.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static io.metaxk.framework.test.core.util.AssertUtils.assertPojoEquals;
import static io.metaxk.framework.test.core.util.RandomUtils.*;
import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import(JobLogServiceImpl.class)
public class JobLogServiceImplTest extends BaseDbUnitTest {

    @Resource
    private JobLogServiceImpl jobLogService;
    @Resource
    private JobLogMapper jobLogMapper;

    @Test
    public void testCreateJobLog() {
        // 准备参数
        JobLogDO reqVO = randomPojo(JobLogDO.class, o -> o.setExecuteIndex(1));

        // 调用
        Long id = jobLogService.createJobLog(reqVO.getJobId(), reqVO.getBeginTime(),
                reqVO.getHandlerName(), reqVO.getHandlerParam(), reqVO.getExecuteIndex());
        // 断言
        assertNotNull(id);
        // 校验记录的属性是否正确
        JobLogDO job = jobLogMapper.selectById(id);
        assertEquals(JobLogStatusEnum.RUNNING.getStatus(), job.getStatus());
    }

    @Test
    public void testUpdateJobLogResultAsync_success() {
        // mock 数据
        JobLogDO log = randomPojo(JobLogDO.class, o -> {
            o.setExecuteIndex(1);
            o.setStatus(JobLogStatusEnum.RUNNING.getStatus());
        });
        jobLogMapper.insert(log);
        // 准备参数
        Long logId = log.getId();
        LocalDateTime endTime = randomLocalDateTime();
        Integer duration = randomInteger();
        boolean success = true;
        String result = randomString();

        // 调用
        jobLogService.updateJobLogResultAsync(logId, endTime, duration, success, result);
        // 校验记录的属性是否正确
        JobLogDO dbLog = jobLogMapper.selectById(log.getId());
        assertEquals(endTime, dbLog.getEndTime());
        assertEquals(duration, dbLog.getDuration());
        assertEquals(JobLogStatusEnum.SUCCESS.getStatus(), dbLog.getStatus());
        assertEquals(result, dbLog.getResult());
    }

    @Test
    public void testUpdateJobLogResultAsync_failure() {
        // mock 数据
        JobLogDO log = randomPojo(JobLogDO.class, o -> {
            o.setExecuteIndex(1);
            o.setStatus(JobLogStatusEnum.RUNNING.getStatus());
        });
        jobLogMapper.insert(log);
        // 准备参数
        Long logId = log.getId();
        LocalDateTime endTime = randomLocalDateTime();
        Integer duration = randomInteger();
        boolean success = false;
        String result = randomString();

        // 调用
        jobLogService.updateJobLogResultAsync(logId, endTime, duration, success, result);
        // 校验记录的属性是否正确
        JobLogDO dbLog = jobLogMapper.selectById(log.getId());
        assertEquals(endTime, dbLog.getEndTime());
        assertEquals(duration, dbLog.getDuration());
        assertEquals(JobLogStatusEnum.FAILURE.getStatus(), dbLog.getStatus());
        assertEquals(result, dbLog.getResult());
    }

    @Test
    public void testGetJobLog() {
        // mock 数据
        JobLogDO dbJobLog = randomPojo(JobLogDO.class, o -> o.setExecuteIndex(1));
        jobLogMapper.insert(dbJobLog);
        // 准备参数
        Long id = dbJobLog.getId();

        // 调用
        JobLogDO jobLog = jobLogService.getJobLog(id);
        // 断言
        assertPojoEquals(dbJobLog, jobLog);
    }

    @Test
    public void testGetJobLogList() {
        // mock 数据
        JobLogDO dbJobLog = randomPojo(JobLogDO.class, o -> o.setExecuteIndex(1));
        jobLogMapper.insert(dbJobLog);
        // 测试 handlerName 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> {}));
        // 准备参数
        Collection<Long> ids = singleton(dbJobLog.getId());

        // 调用
        List<JobLogDO> list = jobLogService.getJobLogList(ids);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbJobLog, list.get(0));
    }

    @Test
    public void testGetJobPage() {
        // mock 数据
        JobLogDO dbJobLog = randomPojo(JobLogDO.class, o -> {
            o.setExecuteIndex(1);
            o.setHandlerName("handlerName 单元测试");
            o.setStatus(JobLogStatusEnum.SUCCESS.getStatus());
            o.setBeginTime(buildTime(2021, 1, 8));
            o.setEndTime(buildTime(2021, 1, 8));
        });
        jobLogMapper.insert(dbJobLog);
        // 测试 jobId 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setJobId(randomLongId())));
        // 测试 handlerName 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setHandlerName(randomString())));
        // 测试 beginTime 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setBeginTime(buildTime(2021, 1, 7))));
        // 测试 endTime 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setEndTime(buildTime(2021, 1, 9))));
        // 测试 status 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setStatus(JobLogStatusEnum.FAILURE.getStatus())));
        // 准备参数
        JobLogPageReqVO reqVo = new JobLogPageReqVO();
        reqVo.setJobId(dbJobLog.getJobId());
        reqVo.setHandlerName("单元");
        reqVo.setBeginTime(dbJobLog.getBeginTime());
        reqVo.setEndTime(dbJobLog.getEndTime());
        reqVo.setStatus(JobLogStatusEnum.SUCCESS.getStatus());

        // 调用
        PageResult<JobLogDO> pageResult = jobLogService.getJobLogPage(reqVo);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbJobLog, pageResult.getList().get(0));
    }

    @Test
    public void testGetJobList_export() {
        // mock 数据
        JobLogDO dbJobLog = randomPojo(JobLogDO.class, o -> {
            o.setExecuteIndex(1);
            o.setHandlerName("handlerName 单元测试");
            o.setStatus(JobLogStatusEnum.SUCCESS.getStatus());
            o.setBeginTime(buildTime(2021, 1, 8));
            o.setEndTime(buildTime(2021, 1, 8));
        });
        jobLogMapper.insert(dbJobLog);
        // 测试 jobId 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setJobId(randomLongId())));
        // 测试 handlerName 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setHandlerName(randomString())));
        // 测试 beginTime 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setBeginTime(buildTime(2021, 1, 7))));
        // 测试 endTime 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setEndTime(buildTime(2021, 1, 9))));
        // 测试 status 不匹配
        jobLogMapper.insert(cloneIgnoreId(dbJobLog, o -> o.setStatus(JobLogStatusEnum.FAILURE.getStatus())));
        // 准备参数
        JobLogExportReqVO reqVo = new JobLogExportReqVO();
        reqVo.setJobId(dbJobLog.getJobId());
        reqVo.setHandlerName("单元");
        reqVo.setBeginTime(dbJobLog.getBeginTime());
        reqVo.setEndTime(dbJobLog.getEndTime());
        reqVo.setStatus(JobLogStatusEnum.SUCCESS.getStatus());

        // 调用
        List<JobLogDO> list = jobLogService.getJobLogList(reqVo);
        // 断言
        assertEquals(1, list.size());
        assertPojoEquals(dbJobLog, list.get(0));
    }

}
