package io.metaxk.module.mes.service.impl.qc;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.metaxk.framework.common.pojo.PageResult;
import io.metaxk.framework.file.core.client.FileClientFactory;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordExcelVo;
import io.metaxk.module.mes.controller.admin.qc.vo.ProcessRecordQueryVo;
import io.metaxk.module.mes.dal.dataobject.qc.*;
import io.metaxk.module.mes.dal.mysql.qc.ProcessRecordMapper;
import io.metaxk.module.mes.dal.mysql.qc.ProcessPictureMapper;
import io.metaxk.module.mes.dal.mysql.qc.StandardDetailMapper;
import io.metaxk.module.mes.dal.mysql.qc.StandardMapper;
import io.metaxk.module.mes.service.qc.ProcessRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 万界星空
 * @time 2023/7/10 13:34
 */
@Service
public class ProcessRecordServiceImpl implements ProcessRecordService {

    @Resource
    private ProcessRecordMapper processRecordMapper;

    @Resource
    private ProcessPictureMapper processPictureMapper;
    @Resource
    private StandardDetailMapper standardDetailMapper;
    @Resource
    private StandardMapper standardMapper;

    /**
     * 文件上传服务
     */
    @Resource
    private FileClientFactory fileClientFactory;


    @Override
    public PageResult<ProcessRecord> findPage(ProcessRecordQueryVo processRecordQueryVo) {
        return processRecordMapper.findPage(processRecordQueryVo);
    }

    @Override
    public List<ProcessRecordExcelVo> exportProcessFormDate() {
        List<ProcessRecord> list = processRecordMapper.selectList();
        ArrayList<ProcessRecordExcelVo> excelList = new ArrayList<>(list.size());
        list.forEach(dict -> {
            ProcessRecordExcelVo excelDTO = new ProcessRecordExcelVo();
            BeanUtils.copyProperties(dict, excelDTO);
            if ("fullInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("全检");
            }else if("casualInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("抽检");
            }else if ("batchInspection".equals(excelDTO.getInspectWay())){
                excelDTO.setInspectWay("批量检");
            }

            excelList.add(excelDTO);
        });
        return excelList;
    }


    @Override
    public ProcessRecord selectByIdAndCodeAndName(Long id, String processCode) {
        return processRecordMapper.selectByIdAndCodeAndName(id, processCode);
    }

    @Override
    public void updateProcessForm(ProcessRecord processRecord) {
        ProcessRecord p = processRecordMapper.selectById(processRecord.getId());
        if (processRecord.getInspectTime() != null && !"".equals(processRecord.getInspectTime())){
            p.setInspectTime(processRecord.getInspectTime());//质检时间
        }
        if (processRecord.getInspectStartTime() != null && !"".equals(processRecord.getInspectStartTime())){
            p.setInspectStartTime(processRecord.getInspectStartTime());//检验开始时间
        }
        if (processRecord.getInspectEndTime() != null && !"".equals(processRecord.getInspectEndTime())){
            p.setInspectEndTime(processRecord.getInspectEndTime());//检验结束时间
        }
        if (processRecord.getInspectUser() != null && !"".equals(processRecord.getInspectUser())){
            p.setInspectUser(processRecord.getInspectUser());
        }
        processRecordMapper.updateById(p);
    }

    @Override
    public void updateStatus(Long id,String status) {
        ProcessRecord p = processRecordMapper.selectById(id);
        p.setStatus(status);
        processRecordMapper.updateById(p);
    }

    @Override
    public ProcessRecord findProcessFormById(Long id) {
        QueryWrapper<ProcessRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return processRecordMapper.selectOne(queryWrapper);
    }

    @Override
    public List<ProcessRecord> findProcessRecordByProcessCode(String processCode) {
        LambdaQueryWrapperX<ProcessRecord> queryWrapperX = new LambdaQueryWrapperX<>();
        queryWrapperX.eq(ProcessRecord::getProcessCode,processCode);
        return processRecordMapper.selectList(queryWrapperX);
    }

    @Override
    public Integer updateProcessRecords(ProcessRecord processRecord) {
        //首先查工序对应的检验标准是否有启用的版本，是启用则查询是否存在已启用的工序检验标准对应工序的检测项目，不存在inspectFlag赋值为0,存在inspectFlag赋值为1。
        //是禁用则直接inspectFlag赋值为0。
        //查询工序标准明细表
        List<StandardDetail> standardDetailList = standardDetailMapper.findDetailByProcessCode(processRecord.getProcessCode());
        System.out.println("==========bbbbb=====standardDetailList:"+standardDetailList);
        //如果检验标准为空
        if(standardDetailList.isEmpty()){
            List<Standard> standardList = standardMapper.findStandByProcessCode(processRecord.getProcessCode());
            System.out.println("==========bbbbb==111===standardList:"+standardList);
            for(Standard standard:standardList) {
                if ("true".equals(standard.getEnableFlag())) {
                    //检验方式
                    processRecord.setInspectWay(standard.getInspectMethod());
                    //版本
                    processRecord.setVersion(standard.getVersion());
                    //是否有检验项目0：否1：是
                    processRecord.setInspectFlag("0");
                }
            }
        }else {
            for (StandardDetail standardDetail : standardDetailList) {
                String[] processCodeArray = standardDetail.getProcessCode().split(",");
                for (String code : processCodeArray) {
                    List<Standard> standardList = standardMapper.findStandByProcessCode(code);
                    System.out.println("==========bbbbb==222===standardList:"+standardList);
                    for(Standard standard:standardList){
                        if("true".equals(standard.getEnableFlag())){
                            //检验方式
                            processRecord.setInspectWay(standard.getInspectMethod());
                            //版本
                            processRecord.setVersion(standard.getVersion());

                            //如果检验标准抽检数>检验单的数量，则抽检数为检验单的数量。
                            if (new BigDecimal(standard.getQuantity()).compareTo(new BigDecimal(processRecord.getQuantity())) == 1){
                                //抽检数
                                processRecord.setCheckNumber(processRecord.getQuantity());
                            }else {
                                //抽检数
                                processRecord.setCheckNumber(standard.getQuantity());
                            }
                        }
                    }
                    if (code.equals(processRecord.getProcessCode()) && "true".equals(standardDetail.getEnableFlag())) {
                        processRecord.setInspectFlag("1");
                        break;
                    } else {
                        processRecord.setInspectFlag("0");
                    }
                }
            }
        }

        return processRecordMapper.updateById(processRecord);
    }

    @Override
    public void uploadPicture(MultipartFile[] mfs,Long id) {
        try{
            String fileName = "";//文件路径

            if(mfs.length > 0){
                for (MultipartFile file : mfs) {
                    //文件扩展名
                    String originalFilename = file.getOriginalFilename();
                    //新修改的文件名
                    String newFileName = IdUtil.fastSimpleUUID() + originalFilename;

                    //调用平台自带的文件上传服务，new Long(18) 是文件配置ID
                    fileName = fileClientFactory.getFileClient(new Long(18)).upload(file.getBytes(), newFileName, "image/jpeg");

                    //将图片路径和图片名信息加入数据库
                    ProcessPicture pic = new ProcessPicture();
                    pic.setRecordId(id);
                    pic.setFileUrl(fileName);//文件路径
                    pic.setFileName(newFileName);
                    processPictureMapper.insert(pic);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
