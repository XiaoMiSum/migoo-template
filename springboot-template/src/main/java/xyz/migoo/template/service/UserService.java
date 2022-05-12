package xyz.migoo.template.service;

import xyz.migoo.framework.common.pojo.PageParam;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.dal.dataobject.BaseUser;

import java.util.List;
import java.util.Set;

public interface UserService<T extends BaseUser, E extends PageParam> {

    PageResult<T> getPage(E req);

    List<T> get(Integer... status);

    T get(String phone);

    T get(Long id);

    void add(T user);

    void update(T user);

    void remove(Long id);

    void verify(String phone);

    Set<Long> getUserRoleList(Long userId);

    void assignUserRole(Long userId, Set<Long> roleIds);
}
