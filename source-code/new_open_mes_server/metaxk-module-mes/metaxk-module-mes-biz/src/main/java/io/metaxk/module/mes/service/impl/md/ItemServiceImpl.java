package io.metaxk.module.mes.service.impl.md;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.common.ExcelDictDTOListener;
import io.metaxk.module.mes.controller.admin.md.vo.ItemExcelVo;
import io.metaxk.module.mes.controller.admin.md.vo.ItemQueryVo;
import io.metaxk.module.mes.dal.dataobject.md.Item;
import io.metaxk.module.mes.dal.mysql.md.ItemMapper;
import io.metaxk.module.mes.service.md.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import java.io.InputStream;
import java.util.*;
import io.metaxk.framework.common.pojo.PageResult;

/**
 * 物料产品 Service 实现类
 * @author 万界星空MES
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private ItemMapper itemMapper;


    @Override
    public Item getItem(Long id) {
        return itemMapper.selectById(id);
    }

    @Override
    public List<Item> getItemList(Collection<Long> ids) {
        return itemMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<Item> getItemPage(ItemQueryVo pageReqVO) {
        return itemMapper.selectPage(pageReqVO);
    }



    @Override
    public Integer removeItemByIds(List<Long> ids) {
      return itemMapper.deleteBatchIds(ids);
    }



    @Override
    public Integer insertItem(Item item) {
        item.setCreateTime(new Date());
        return itemMapper.insert(item);
    }




    @Override
    public Integer updateItems(Item item) {
        Item itemDO = itemMapper.selectById(item.getId());
        item.setCreateTime(itemDO.getCreateTime());
        return itemMapper.updateById(item);
    }


    @Override
    public List<ItemExcelVo> exportData() {
        List<Item> itemDOS = itemMapper.selectList();
        ArrayList<ItemExcelVo> excelDictDTOList = new ArrayList<>(itemDOS.size());
        itemDOS.forEach(dict -> {
            ItemExcelVo excelDictDTO = new ItemExcelVo();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        });
        return excelDictDTOList;
    }

    @Override
    public Item selectMdItemByItemCode(String productCode) {
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("item_code",productCode);
        return itemMapper.selectOne(queryWrapper);
    }




    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ItemExcelVo.class,new ExcelDictDTOListener(itemMapper)).sheet().doRead();
    }


    @Override
    public Item findByCodeAndNameSpc(String itemCode, String itemName, String specification) {
        LambdaQueryWrapperX<Item> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Item::getItemCode,itemCode);
        queryWrapperX.eq(Item::getItemName,itemName);
        queryWrapperX.eq(Item::getSpec,specification);
        return itemMapper.selectOne(queryWrapperX);
    }

    @Override
    public Item findItemByCode(String itemCode) {
        LambdaQueryWrapperX<Item> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Item::getItemCode,itemCode);
        return itemMapper.selectOne(queryWrapperX);
    }

    @Override
    public Item findItemByName(String itemName) {
        LambdaQueryWrapperX<Item> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(Item::getItemName,itemName);
        return itemMapper.selectOne(queryWrapperX);
    }

    @Override
    public PageResult<Item> findItemPage(ItemQueryVo itemPage) {
        return itemMapper.findItemPage(itemPage);
    }


}
