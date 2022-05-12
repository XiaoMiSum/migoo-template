package xyz.migoo.template.dal.mapper;

import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.template.dal.dataobject.Member;

@Mapper
public interface MemberMapper extends BaseMapperX<Member> {


    default Member selectByPhone(String phone) {
        return selectOne("phone", phone);
    }
}
