package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 其他检验单图片存放实体类
 * @author 万界星空
 * @time 2023/8/19 14:28
 */
@Data
@TableName("qc_other_picture")
public class OtherPicture extends EntityCommon {

    private Long id;

    //其他检验单
    private String recordNumber;

    //序列号
    private String sortNumber;

    private String fileUrl;

    private String fileName;
}
