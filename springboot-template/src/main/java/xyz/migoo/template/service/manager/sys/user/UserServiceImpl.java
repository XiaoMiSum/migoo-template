package xyz.migoo.template.service.manager.sys.user;

import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.exception.util.ServiceExceptionUtil;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.sys.user.vo.UserQueryReqVO;
import xyz.migoo.template.dal.dataobject.sys.User;
import xyz.migoo.template.dal.mapper.sys.UserMapper;
import xyz.migoo.template.enums.ErrorCodeConstants;
import xyz.migoo.template.service.UserService;
import xyz.migoo.template.util.PasswordUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static xyz.migoo.framework.common.enums.NumberConstants.N_0;

@Service
public class UserServiceImpl implements UserService<User, UserQueryReqVO> {

    @Resource
    private UserMapper mapper;

    @Override
    public PageResult<User> getPage(UserQueryReqVO req) {
        return mapper.selectPage(req);
    }

    @Override
    public List<User> get(Integer... status) {
        return mapper.selectList(Objects.isNull(status) || status.length < 1 ? null : status[0]);
    }

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
        user.setPassword(PasswordUtils.encode(user.getPassword()));
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

    @Override
    public Set<Long> getUserRoleList(Long userId) {
        return null;
    }

    @Override
    public void assignUserRole(Long userId, Set roleIds) {

    }
}
