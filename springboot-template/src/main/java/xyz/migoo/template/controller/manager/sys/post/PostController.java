package xyz.migoo.template.controller.manager.sys.post;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.template.controller.manager.sys.post.vo.*;
import xyz.migoo.template.convert.manager.sys.PostConvert;
import xyz.migoo.template.dal.dataobject.sys.Post;
import xyz.migoo.template.service.manager.sys.post.PostService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public Result<PageResult<PostRespVO>> getPage(PostQueryReqVO req) {
        PageResult<PostRespVO> result = PostConvert.INSTANCE.convert(postService.getPage(req));
        result.getList().sort(Comparator.comparing(PostRespVO::getSort));
        return Result.getSuccessful(result);
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:post:add')")
    public Result<?> add(@RequestBody PostAddReqVO req) {
        postService.verify(req.getCode(), req.getName(), null);
        postService.add(PostConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:post:edit')")
    public Result<?> update(@RequestBody PostUpdateReqVO req) {
        postService.verify(req.getCode(), req.getName(), req.getId());
        postService.update(PostConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:post:edit')")
    public Result<?> get(@PathVariable("id") Long id) {
        return Result.getSuccessful(PostConvert.INSTANCE.convert(postService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:post:remove')")
    public Result<?> remove(@PathVariable("id") Long id) {
        postService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<PostSimpleRespVO>> getSimplePosts() {
        // ??????????????????????????????????????????
        List<Post> list = postService.getList(null, Collections.singleton(CommonStatusEnum.ENABLE.getStatus()));
        // ???????????????????????????
        list.sort(Comparator.comparing(Post::getSort));
        return Result.getSuccessful(PostConvert.INSTANCE.convert(list));
    }
}
