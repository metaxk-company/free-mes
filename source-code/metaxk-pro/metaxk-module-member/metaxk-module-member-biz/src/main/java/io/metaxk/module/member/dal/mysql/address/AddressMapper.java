package io.metaxk.module.member.dal.mysql.address;

import io.metaxk.framework.mybatis.core.mapper.BaseMapperX;
import io.metaxk.framework.mybatis.core.query.LambdaQueryWrapperX;
import io.metaxk.module.member.dal.dataobject.address.AddressDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper extends BaseMapperX<AddressDO> {

    default AddressDO selectByIdAndUserId(Long id, Long userId) {
        return selectOne(AddressDO::getId, id, AddressDO::getUserId, userId);
    }

    default List<AddressDO> selectListByUserIdAndDefaulted(Long userId, Boolean defaulted) {
        return selectList(new LambdaQueryWrapperX<AddressDO>().eq(AddressDO::getUserId, userId)
                .eqIfPresent(AddressDO::getDefaulted, defaulted));
    }

}
