package xyz.migoo.template.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.template.dal.dataobject.User;

@Mapper
public interface UserMapper extends BaseMapperX<User> {


    default User selectByPhone(String phone) {
        return selectOne("phone", phone);
    }
}
