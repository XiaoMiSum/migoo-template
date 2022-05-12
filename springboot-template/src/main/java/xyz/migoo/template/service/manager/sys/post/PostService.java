package xyz.migoo.template.service.manager.sys.post;

import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.sys.post.vo.PostQueryReqVO;
import xyz.migoo.template.dal.dataobject.sys.Post;

public interface PostService {
    PageResult<Post> getPage(PostQueryReqVO req);

    void verify(String code);

    void add(Post post);

    void update(Post convert);

    void get(Long id);

    void remove(Long id);
}
