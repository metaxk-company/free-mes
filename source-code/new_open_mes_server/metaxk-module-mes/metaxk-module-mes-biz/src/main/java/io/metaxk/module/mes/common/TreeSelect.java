package io.metaxk.module.mes.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.metaxk.module.mes.dal.dataobject.dv.MachineryType;
import io.metaxk.module.mes.dal.dataobject.md.ItemType;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author metax
 */
@Data
@AllArgsConstructor
public class TreeSelect {


    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {
    }

    public TreeSelect(ItemType item)
    {
        this.id = item.getId();
        this.label = item.getItemTypeName();
        this.children = item.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(MachineryType item) {
        this.id = item.getId();
        this.label = item.getMachineryTypeName();
        this.children = item.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());

    }
}
