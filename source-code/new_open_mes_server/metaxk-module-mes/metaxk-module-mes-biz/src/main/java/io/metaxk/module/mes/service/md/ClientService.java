package io.metaxk.module.mes.service.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.ClientExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.ClientQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Client;

import java.util.List;

/**
 * 客户 Service 接口
 *
 * @author 万界星空
 */
public interface ClientService  {


    /**
     * 根据id查询客户详情
     * @param  id
     * @return Client
     */
    Client getClient(Long id);


    /**
     * 客户管理条件分页查询
     * @param  pageReqVO
     * @return Client
     */
    PageResult<Client> getClientPage(ClientQueryVo pageReqVO);


    /**
     * 新增客户管理
     * @param  createReqVO
     * @return Client
     */
    Integer insertClient(Client createReqVO);
    /**
     * 修改客户管理
     * @param  updateReqVO
     * @return Client
     */
    Integer updatesClient(Client updateReqVO);
    /**
     * 删除客户管理
     * @param  ids
     * @return Client
     */
    Integer deleteClientByIds(List<Long> ids);

    /**
     * 导出客户
     * @return Client
     */
    List<ClientExcelVo> listData();

    /**
     * 根据客户编号查询客户信息
     * @param  clientCode
     * @return Client
     */
    Client findClientByCode(String clientCode);

    /**
     * 根据客户名称查询客户信息
     * @param  clientName
     * @return Client
     */
    Client findClientByName(String clientName);

    Client findCustomerCode(String customerNumber);

    /**
     * 查询所有客户信息
     * @return
     */
    List<Client> listAll();

    String getOrderNumberByName(String clientName);

}
