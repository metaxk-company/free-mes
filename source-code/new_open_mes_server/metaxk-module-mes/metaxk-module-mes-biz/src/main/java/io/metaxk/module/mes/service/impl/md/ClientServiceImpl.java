package io.metaxk.module.mes.service.impl.md;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.md.vo.ClientExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.ClientQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Client;
import io.metaxk.module.mes.dal.mysql.md.ClientMapper;
import io.metaxk.module.mes.service.md.ClientService;
import io.metaxk.module.mes.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户 Service 实现类
 * @author 万界星空MES
 */
@Service
public class ClientServiceImpl implements ClientService {



    @Resource
    private ClientMapper clientMapper;

    @Override
    public Client getClient(Long id) {
        return clientMapper.selectById(id);
    }


    @Override
    public PageResult<Client> getClientPage(ClientQueryVo pageReqVO) {
        return clientMapper.selectPage(pageReqVO);
    }





    @Override
    public Integer insertClient(Client client) {
        return clientMapper.insert(client);


    }

    @Override
    public Integer updatesClient(Client updateReqVO) {
        return clientMapper.updateById(updateReqVO);
    }

    @Override
    public Integer deleteClientByIds(List<Long> ids) {
        return clientMapper.deleteBatchIds(ids);
    }

    @Override
    public List<ClientExcelVo> listData() {
        List<Client> dictList = clientMapper.selectList();
        ArrayList<ClientExcelVo> excelDictDTOList = new ArrayList<>(dictList.size());
        dictList.forEach(dict -> {
            ClientExcelVo excelDictDTO = new ClientExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            if ("Y".equals(excelDictDTO.getEnableFlag())){
                excelDictDTO.setEnableFlag("启用");
            }else if("N".equals(excelDictDTO.getEnableFlag())){
                excelDictDTO.setEnableFlag("禁用");
            }
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public Client findClientByCode(String clientCode) {
        LambdaQueryWrapperX<Client> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Client::getClientCode,clientCode);
        return clientMapper.selectOne(queryWrapperX);
    }

    @Override
    public Client findClientByName(String clientName) {
        LambdaQueryWrapperX<Client> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Client::getClientName,clientName);
        return clientMapper.selectOne(queryWrapperX);
    }

    @Override
    public Client findCustomerCode(String customerNumber) {
        LambdaQueryWrapperX<Client> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Client::getClientCode,customerNumber);
        return clientMapper.selectOne(queryWrapperX);
    }

    @Override
    public List<Client> listAll() {
        return clientMapper.selectList();
    }

    @Override
    public String getOrderNumberByName(String clientName) {
        return clientMapper.selectOne(new LambdaQueryWrapperX<Client>().eq(Client::getClientName, clientName)).getOrderNumber();
    }

}
