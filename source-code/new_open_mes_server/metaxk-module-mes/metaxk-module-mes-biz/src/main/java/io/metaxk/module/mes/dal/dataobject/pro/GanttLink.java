package io.metaxk.module.mes.dal.dataobject.pro;

import lombok.Data;


/**
 * 甘特图Link
 * @author 万界星空
 */
@Data
public class GanttLink {

    private int id;

    private int source;

    private int target;

    private String itemName;

    private String type;




}
