package xyz.migoo.template.convert.manager.sys;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.sys.post.vo.PostAddReqVO;
import xyz.migoo.template.controller.manager.sys.post.vo.PostRespVO;
import xyz.migoo.template.controller.manager.sys.post.vo.PostSimpleRespVO;
import xyz.migoo.template.controller.manager.sys.post.vo.PostUpdateReqVO;
import xyz.migoo.template.dal.dataobject.sys.Post;

import java.util.List;

@Mapper
public interface PostConvert {

    PostConvert INSTANCE = Mappers.getMapper(PostConvert.class);

    PageResult<PostRespVO> convert(PageResult<Post> page);

    Post convert(PostAddReqVO req);

    Post convert(PostUpdateReqVO req);

    List<PostSimpleRespVO> convert(List<Post> list);

    PostRespVO convert(Post post);
}
