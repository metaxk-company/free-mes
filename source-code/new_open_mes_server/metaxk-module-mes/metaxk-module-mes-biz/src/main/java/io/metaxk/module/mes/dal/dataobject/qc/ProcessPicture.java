package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 工序检验单图片存放实体类
 * @author 万界星空
 * @time 2023/7/11 16:45
 */
@Data
@TableName("qc_process_picture")
public class ProcessPicture extends EntityCommon {

    private Long id;

    //工序检验单id
    private Long recordId;

    //序列号
    private String sortNumber;

    private String fileUrl;

    private String fileName;
}
