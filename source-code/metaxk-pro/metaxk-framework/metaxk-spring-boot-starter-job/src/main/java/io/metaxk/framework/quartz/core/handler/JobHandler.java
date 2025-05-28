package io.metaxk.framework.quartz.core.handler;

/**
 * 任务处理器
 *
 * @author 万界星空科技
* https://www.metaxk.io
* open@metaxk.io
 */
public interface JobHandler {

    /**
     * 执行任务
     *
     * @param param 参数
     * @return 结果
     * @throws Exception 异常
     */
    String execute(String param) throws Exception;

}
