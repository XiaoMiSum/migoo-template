package xyz.migoo.template.dal.mapper.sys;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.mybatis.core.BaseMapperX;
import xyz.migoo.framework.mybatis.core.QueryWrapperX;
import xyz.migoo.template.controller.manager.sys.post.vo.PostQueryReqVO;
import xyz.migoo.template.dal.dataobject.sys.Post;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapperX<Post> {

    default List<Post> selectList(Collection<Long> ids, Collection<Integer> statuses) {
        return selectList(new QueryWrapperX<Post>().inIfPresent("id", ids)
                .inIfPresent("status", statuses));
    }

    default PageResult<Post> selectPage(PostQueryReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<Post>()
                .likeIfPresent("code", reqVO.getCode())
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("status", reqVO.getStatus()));
    }

    default Post selectByName(String name) {
        return selectOne(new QueryWrapper<Post>().eq("name", name));
    }

    default Post selectByCode(String code) {
        return selectOne(new QueryWrapper<Post>().eq("code", code));
    }

}
