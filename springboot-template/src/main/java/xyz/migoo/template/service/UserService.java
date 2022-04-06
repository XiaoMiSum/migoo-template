package xyz.migoo.template.service;

import xyz.migoo.template.dal.dataobject.User;

public interface UserService {

    User get(String phone);

    User get(Long id);

    void add(User user);

    void update(User user);

    void remove(Long id);

    void verify(String phone);
}
