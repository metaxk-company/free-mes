package io.metaxk.module.mes.controller.admin.qc.vo;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.metaxk.module.mes.dal.dataobject.qc.Project;
import io.metaxk.module.mes.dal.mysql.qc.ProjectMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;
import static io.metaxk.framework.common.exception.util.ServiceExceptionUtil.exception;
import static io.metaxk.module.mes.enums.ErrorCodeConstants.ONE_CLASSIFY_OR_TWO_CLASSIFY;

/**
 * @author 万界星空
 * @time 2023/7/7 17:26
 */
@Slf4j
@NoArgsConstructor
public class ProjectListener extends AnalysisEventListener<ProjectExcelVo> {




    private static final int BATCH_COUNT = 3000;

    List<ProjectExcelVo> list = new ArrayList();

    private ProjectMapper inspectProjectMapper;

    public ProjectListener(ProjectMapper inspectProjectMapper) {
        this.inspectProjectMapper = inspectProjectMapper;
    }


    @Override
    public void invoke(ProjectExcelVo data, AnalysisContext context) {
        log.info("解析到一条记录: {}", data);
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        log.info("所有数据解析完成！");
    }


    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        for(ProjectExcelVo inspectProjectExcelVO:list){
            Project inspectProject = new Project();
            BeanUtils.copyProperties(inspectProjectExcelVO,inspectProject);
            String classify = inspectProject.getClassify();
            if(!classify.equals("一级分类") && !classify.equals("二级分类")){
                throw exception(ONE_CLASSIFY_OR_TWO_CLASSIFY);
            }
          if(inspectProjectMapper.findProjectByCodeAndName(inspectProject.getProjectCode(),inspectProject.getProjectName()) == null){
              inspectProjectMapper.insert(inspectProject);
          }
        }
        log.info("存储数据库成功！");
    }



}
