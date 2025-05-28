package io.metaxk.framework.file.core.client.local;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LocalFileClientTest {

    @Test
    @Disabled
    public void test() {
        // 创建客户端
        LocalFileClientConfig config = new LocalFileClientConfig();
        config.setDomain("http://127.0.0.1:48080");
        config.setBasePath("/Users/metaxk/file_test");
        LocalFileClient client = new LocalFileClient(0L, config);
        client.init();
        // 上传文件
        String path = IdUtil.fastSimpleUUID() + ".jpg";
        byte[] content = ResourceUtil.readBytes("file/erweima.jpg");
        String fullPath = client.upload(content, path, "image/jpeg");
        System.out.println("访问地址：" + fullPath);
        client.delete(path);
    }

}
