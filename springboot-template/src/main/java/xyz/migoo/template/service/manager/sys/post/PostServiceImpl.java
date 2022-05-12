package xyz.migoo.template.service.manager.sys.post;

import org.springframework.stereotype.Service;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.sys.post.vo.PostQueryReqVO;
import xyz.migoo.template.dal.dataobject.sys.Post;

@Service
public class PostServiceImpl implements PostService {
    @Override
    public PageResult<Post> getPage(PostQueryReqVO req) {
        return null;
    }

    @Override
    public void verify(String code) {

    }

    @Override
    public void add(Post post) {

    }

    @Override
    public void update(Post convert) {

    }

    @Override
    public void get(Long id) {

    }

    @Override
    public void remove(Long id) {

    }
}
