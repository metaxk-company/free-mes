package io.metaxk.module.system.convert.oauth2;

import cn.hutool.core.date.LocalDateTimeUtil;
import io.metaxk.framework.common.core.KeyValue;
import io.metaxk.framework.common.enums.UserTypeEnum;
import io.metaxk.framework.common.util.collection.CollectionUtils;
import io.metaxk.framework.security.core.util.SecurityFrameworkUtils;
import io.metaxk.module.system.controller.admin.oauth2.vo.open.OAuth2OpenAccessTokenRespVO;
import io.metaxk.module.system.controller.admin.oauth2.vo.open.OAuth2OpenAuthorizeInfoRespVO;
import io.metaxk.module.system.controller.admin.oauth2.vo.open.OAuth2OpenCheckTokenRespVO;
import io.metaxk.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import io.metaxk.module.system.dal.dataobject.oauth2.OAuth2ApproveDO;
import io.metaxk.module.system.dal.dataobject.oauth2.OAuth2ClientDO;
import io.metaxk.module.system.util.oauth2.OAuth2Utils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface OAuth2OpenConvert {

    OAuth2OpenConvert INSTANCE = Mappers.getMapper(OAuth2OpenConvert.class);

    default OAuth2OpenAccessTokenRespVO convert(OAuth2AccessTokenDO bean) {
        OAuth2OpenAccessTokenRespVO respVO = convert0(bean);
        respVO.setTokenType(SecurityFrameworkUtils.AUTHORIZATION_BEARER.toLowerCase());
        respVO.setExpiresIn(OAuth2Utils.getExpiresIn(bean.getExpiresTime()));
        respVO.setScope(OAuth2Utils.buildScopeStr(bean.getScopes()));
        return respVO;
    }
    OAuth2OpenAccessTokenRespVO convert0(OAuth2AccessTokenDO bean);

    default OAuth2OpenCheckTokenRespVO convert2(OAuth2AccessTokenDO bean) {
        OAuth2OpenCheckTokenRespVO respVO = convert3(bean);
        respVO.setExp(LocalDateTimeUtil.toEpochMilli(bean.getExpiresTime()) / 1000L);
        respVO.setUserType(UserTypeEnum.ADMIN.getValue());
        return respVO;
    }
    OAuth2OpenCheckTokenRespVO convert3(OAuth2AccessTokenDO bean);

    default OAuth2OpenAuthorizeInfoRespVO convert(OAuth2ClientDO client, List<OAuth2ApproveDO> approves) {
        // 构建 scopes
        List<KeyValue<String, Boolean>> scopes = new ArrayList<>(client.getScopes().size());
        Map<String, OAuth2ApproveDO> approveMap = CollectionUtils.convertMap(approves, OAuth2ApproveDO::getScope);
        client.getScopes().forEach(scope -> {
            OAuth2ApproveDO approve = approveMap.get(scope);
            scopes.add(new KeyValue<>(scope, approve != null ? approve.getApproved() : false));
        });
        // 拼接返回
        return new OAuth2OpenAuthorizeInfoRespVO(
                new OAuth2OpenAuthorizeInfoRespVO.Client(client.getName(), client.getLogo()), scopes);
    }

}
