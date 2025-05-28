package io.metaxk.module.mes.dal.dataobject.qc;

import com.baomidou.mybatisplus.annotation.TableName;
import io.metaxk.module.mes.common.EntityCommon;
import lombok.Data;

/**
 * 来料检验单图片存放实体类
 * @author 万界星空
 * @time 2023/7/26 14:28
 */
@Data
@TableName("qc_receive_picture")
public class ReceivePicture extends EntityCommon {

    private Long id;

    //来料检验单编号
    private Long recordId;

    //序列号
    private String sortNumber;

    private String fileUrl;

    private String fileName;
}
