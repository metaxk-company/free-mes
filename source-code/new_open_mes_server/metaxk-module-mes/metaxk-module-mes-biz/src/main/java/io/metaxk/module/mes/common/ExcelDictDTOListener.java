package io.metaxk.module.mes.common;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import io.metaxk.module.mes.controller.admin.md.vo.ItemExcelVo;
import io.metaxk.module.mes.dal.mysql.md.ItemMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class ExcelDictDTOListener extends AnalysisEventListener<ItemExcelVo> {



    private static final int BATCH_COUNT = 3000;

    List<ItemExcelVo> list = new ArrayList();

    private ItemMapper itemMapper;

    public ExcelDictDTOListener(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     */
    @Override
    public void invoke(ItemExcelVo data, AnalysisContext context) {
        log.info("解析到一条记录: {}", data);
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }


    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        itemMapper.insertBatchs(list);  //批量插入
        log.info("存储数据库成功！");
    }



}
