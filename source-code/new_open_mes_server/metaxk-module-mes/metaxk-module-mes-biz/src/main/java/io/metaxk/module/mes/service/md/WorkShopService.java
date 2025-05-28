package io.metaxk.module.mes.service.md;

import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.md.vo.WorkShopQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.WorkShop;



/**
 * 车间 Service 接口
 * @author 万界星空
 */
public interface WorkShopService {


    /**
     * 新增车间
     * @param createReqVO
     * @return Integer
     */
    Integer insertWorkshopDO(WorkShop createReqVO);


    /**
     * 删除车间
     * @param ids
     * @return Integer
     */
    Integer removeWorkshop(List<Long> ids);

    /**
     * 修改车间
     * @param updateReqVO
     * @return Integer
     */
    Integer  updateWorkshops(WorkShop updateReqVO);


    /**
     * 根据车间id查询车间
     * @param workshopId
     * @return Workshop
     */
    WorkShop selectMdWorkshopByWorkshopId(Long workshopId);

    /**
     * 车间列表
     * @param workshop
     * @return List<Workshop>
     */
    List<WorkShop> selectMdWorkshopList(WorkShop workshop);

    /**
     * 车间条件分页查询
     * @param workshopPage
     * @return PageResult<Workshop>
     */
    PageResult<WorkShop> listWorkshopPage(WorkShopQueryVo workshopPage);

    /**
     * 根据id查询车间
     * @param id
     * @return Workshop
     */
    WorkShop findWorkshopById(Long id);

    /**
     * 根据车间编号与名称查询车间
     * @param workshopCode
     * @param workshopName
     * @return Workshop
     */
    WorkShop findByCodeAndName(String workshopCode, String workshopName);

    /**
     * 根据车间编号查询车间
     * @param workshopCode
     * @return Workshop
     */
    WorkShop findWorkShopByCode(String workshopCode);

    /**
     * 根据车间名称查询车间
     * @param workshopName
     * @return Workshop
     */
    WorkShop findWorkShopByName(String workshopName);

    List<WorkShop> findavailableList();
}
