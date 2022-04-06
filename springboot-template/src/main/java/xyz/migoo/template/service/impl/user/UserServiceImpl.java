package xyz.migoo.template.service.impl.user;

import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.template.dal.dataobject.User;
import xyz.migoo.template.dal.mapper.UserMapper;
import xyz.migoo.template.enums.ErrorCodeConstants;
import xyz.migoo.template.service.UserService;

import javax.annotation.Resource;
import java.util.Objects;

import static xyz.migoo.framework.common.enums.NumberConstants.N_0;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Override
    public User get(String phone) {
        return mapper.selectByPhone(phone);
    }

    @Override
    public User get(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void add(User user) {
        mapper.insert(user);
    }

    @Override
    public void update(User user) {
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
