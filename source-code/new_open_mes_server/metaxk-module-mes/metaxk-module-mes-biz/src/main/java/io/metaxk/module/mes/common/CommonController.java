package io.metaxk.module.mes.common;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.metaxk.framework.common.pojo.CommonResult;
import io.metaxk.module.mes.config.MesConfig;
import io.metaxk.module.mes.config.ServerConfig;
import io.metaxk.module.mes.utils.FileUploadUtils;
import io.metaxk.module.mes.utils.FileUtils;
import io.metaxk.module.mes.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * 通用请求处理
 *
 *
 */
@RestController
@RequestMapping("/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    private static final String FILE_DELIMETER = ",";

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = MesConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public CommonResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = MesConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            HashMap<String, Object> map = new HashMap<>();
            map.put("url", url);
            map.put("fileName", fileName);
            map.put("newFileName", FileUtils.getName(fileName));
            map.put("originalFilename", file.getOriginalFilename());
            return CommonResult.success(map);
        }
        catch (Exception e)
        {
            return CommonResult.error(500,"未知错误，请联系管理员");
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    public CommonResult uploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = MesConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            HashMap<String, Object>  map = new HashMap<>();
            map.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            map.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            map.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            map.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return CommonResult.success(map);
        }
        catch (Exception e)
        {
            return CommonResult.error(500,"未知错误，请联系管理员");
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = MesConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    @PostMapping("/uploadMinio")
    public CommonResult uploadFileMinio(MultipartFile file) throws Exception{
        try{
            String fileName = FileUploadUtils.uploadMinio(file);
            HashMap<String, Object> map = new HashMap<>();
            map.put("url",fileName);
            map.put("fileName",fileName);
            map.put("newFileName",FileUtils.getName(fileName));
            map.put("originalFileName",file.getOriginalFilename());
            return CommonResult.success(map);
        }catch (Exception e){
            return CommonResult.error(500,"未知错误，请联系管理员");
        }
    }

}

