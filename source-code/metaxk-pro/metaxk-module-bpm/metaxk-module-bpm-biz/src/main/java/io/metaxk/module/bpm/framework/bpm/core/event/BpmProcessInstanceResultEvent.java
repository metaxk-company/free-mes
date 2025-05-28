package io.metaxk.module.bpm.framework.bpm.core.event;

import io.metaxk.module.bpm.dal.dataobject.task.BpmProcessInstanceExtDO;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import javax.validation.constraints.NotNull;

/**
 * 流程实例的结果发生变化的 Event
 * 定位：由于额外增加了 {@link BpmProcessInstanceExtDO#getResult()} 结果，所以增加该事件
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
@SuppressWarnings("ALL")
@Data
public class BpmProcessInstanceResultEvent extends ApplicationEvent {

    /**
     * 流程实例的编号
     */
    @NotNull(message = "流程实例的编号不能为空")
    private String id;
    /**
     * 流程实例的 key
     */
    @NotNull(message = "流程实例的 key 不能为空")
    private String processDefinitionKey;
    /**
     * 流程实例的结果
     */
    @NotNull(message = "流程实例的结果不能为空")
    private Integer result;
    /**
     * 流程实例对应的业务标识
     * 例如说，请假
     */
    private String businessKey;

    public BpmProcessInstanceResultEvent(Object source) {
        super(source);
    }

}
