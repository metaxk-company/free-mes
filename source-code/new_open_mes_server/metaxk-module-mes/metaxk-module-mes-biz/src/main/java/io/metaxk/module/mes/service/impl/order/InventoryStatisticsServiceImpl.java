package io.metaxk.module.mes.service.impl.order;

import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.module.mes.controller.admin.order.vo.InventoryStatisticsQueryVO;
import io.metaxk.module.mes.controller.admin.order.vo.InventoryStatisticsVO;
import io.metaxk.module.mes.dal.mysql.order.InventoryStatisticsQueryVOMapper;
import io.metaxk.module.mes.service.order.InventoryStatisticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * io.metaxk.module.mes.service.impl.order
 *
 * @author xx
 * @time 2023/8/17 13:19
 */

@Service
public class InventoryStatisticsServiceImpl implements InventoryStatisticsService {

    @Resource
    private InventoryStatisticsQueryVOMapper inventoryStatisticsQueryVOMapper;

    /**
     * 库存查询
     *
     * @param mode 查询模式——日月年
     * @param inventoryStatisticsQueryVO 筛选参数 线别、型号、规格、颜色、盘号、（起始时间、结束时间）当天不需要筛选
     * @return 线别、型号、规格、颜色、盘号、入库总数量、总件数；出库总数量、总件数
     */
    @Override
    public PageResult<InventoryStatisticsVO> InventoryStatistics(String mode, InventoryStatisticsQueryVO inventoryStatisticsQueryVO) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String startDate = "";
        String endDate = "";

        try {
            if ("day".equals(mode)) {
                startDate = dateFormat.format(now);
                /*Calendar cal = Calendar.getInstance();
                cal.setTime(now);
                cal.add(Calendar.DATE, 1);*/
                endDate = this.dayPlus(startDate, dateFormat);
            } else if ("month".equals(mode)) {
                startDate = dateFormat.format(now).substring(0, 7) + "-01";
                Calendar cld = Calendar.getInstance();
                cld.setTime(dateFormat.parse(startDate));
                cld.add(Calendar.MONTH, 1);
                cld.set(Calendar.DAY_OF_MONTH, 0);
                endDate = dateFormat.format(cld.getTime()).substring(0, 10);
                if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getStartTime())) {
                    if (!(dateFormat.parse(inventoryStatisticsQueryVO.getStartTime()).before(dateFormat.parse(startDate))))
                        startDate = inventoryStatisticsQueryVO.getStartTime();
                }
                if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getEndTime())) {
                    if ((dateFormat.parse(inventoryStatisticsQueryVO.getEndTime()).before(dateFormat.parse(endDate)))) {
                        /*Calendar cal = Calendar.getInstance();
                        cal.setTime(dateFormat.parse(inventoryStatisticsQueryVO.getEndTime()));
                        cal.add(Calendar.DATE, 1);
                        endDate = dateFormat.format(cal.getTime()).substring(0, 10);*/
                        endDate = this.dayPlus(inventoryStatisticsQueryVO.getEndTime(), dateFormat);
                    }
                }
            } else if ("year".equals(mode)) {
                startDate = dateFormat.format(now).substring(0, 4) + "-01-01";
                endDate = this.dayPlus(dateFormat.format(now).substring(0, 4) + "-12-31", dateFormat);
                if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getStartTime())) {
                    if (!(dateFormat.parse(inventoryStatisticsQueryVO.getStartTime()).before(dateFormat.parse(startDate))))
                        startDate = inventoryStatisticsQueryVO.getStartTime();
                }
                if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getEndTime())) {
                    if ((dateFormat.parse(inventoryStatisticsQueryVO.getEndTime()).before(dateFormat.parse(endDate)))){
                        /*Calendar cal = Calendar.getInstance();
                        cal.setTime(dateFormat.parse(inventoryStatisticsQueryVO.getEndTime()));
                        cal.add(Calendar.DATE, 1);
                        endDate = dateFormat.format(cal.getTime()).substring(0, 10);*/
                        endDate = this.dayPlus(inventoryStatisticsQueryVO.getEndTime(), dateFormat);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        String lineType = null;
        String model = null;
        String spec = null;
        String color = null;
        String reelNumber = null;
        Integer pageNo = 1;
        Integer pageSize = 5;
        if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getLineType()))
            lineType = inventoryStatisticsQueryVO.getLineType();
        if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getModel()))
            model = inventoryStatisticsQueryVO.getModel();
        if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getSpec()))
            spec = inventoryStatisticsQueryVO.getSpec();
        if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getColor()))
            color = inventoryStatisticsQueryVO.getColor();
        if (StringUtils.isNotBlank(inventoryStatisticsQueryVO.getReelNumber()))
            reelNumber = inventoryStatisticsQueryVO.getReelNumber();
        if (inventoryStatisticsQueryVO.getPageNo() != null)
            pageNo = inventoryStatisticsQueryVO.getPageNo();
        if (inventoryStatisticsQueryVO.getPageSize() != null)
            pageSize = inventoryStatisticsQueryVO.getPageSize();


        List<InventoryStatisticsVO> inventoryStatisticsVOList = inventoryStatisticsQueryVOMapper.inventoryStatistics(startDate, endDate, lineType, model, spec, color, reelNumber, pageNo, pageSize);

        return new PageResult<>(inventoryStatisticsVOList, (long) inventoryStatisticsVOList.size());
    }

    private String dayPlus(String day, SimpleDateFormat dateFormat) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(day));
        cal.add(Calendar.DATE, 1);
        return dateFormat.format(cal.getTime()).substring(0, 10);
    }

}
