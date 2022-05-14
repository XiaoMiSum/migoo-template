package xyz.migoo.template.service.manager.member;

import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.member.vo.MemberQueryReqVO;
import xyz.migoo.template.dal.dataobject.Member;
import xyz.migoo.template.dal.mapper.MemberMapper;
import xyz.migoo.template.enums.ErrorCodeConstants;
import xyz.migoo.template.service.UserService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static xyz.migoo.framework.common.enums.NumberConstants.N_0;

@Service
public class MemberServiceImpl implements UserService<Member, MemberQueryReqVO> {

    @Resource
    private MemberMapper mapper;

    @Override
    public PageResult<Member> getPage(MemberQueryReqVO req) {
        // todo
        return null;
    }

    @Override
    public List<Member> get(Integer... status) {
        return null;
    }

    @Override
    public Member get(String phone) {
        return mapper.selectByPhone(phone);
    }

    @Override
    public Member get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void add(Member user) {
        mapper.insert(user);
    }

    @Override
    public void update(Member user) {
        if (mapper.updateById(user) == N_0) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.USER_NOT_EXISTS);
        }
    }

    @Override
    public void remove(Long id) {
        if (mapper.deleteById(id) == N_0) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.USER_NOT_EXISTS);
        }
    }

    @Override
    public void verify(String phone) {
        if (Objects.nonNull(mapper.selectByPhone(phone))) {
            throw ServiceExceptionUtil.get(ErrorCodeConstants.USER_IS_EXISTS);
        }
    }
}
