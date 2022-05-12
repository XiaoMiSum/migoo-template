package xyz.migoo.template.controller.manager.sys.post;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.template.controller.manager.sys.post.vo.PostAddReqVO;
import xyz.migoo.template.controller.manager.sys.post.vo.PostQueryReqVO;
import xyz.migoo.template.controller.manager.sys.post.vo.PostRespVO;
import xyz.migoo.template.controller.manager.sys.post.vo.PostUpdateReqVO;
import xyz.migoo.template.convert.manager.sys.PostConvert;
import xyz.migoo.template.service.manager.sys.post.PostService;

import javax.annotation.Resource;

@RestController
@RequestMapping("/post")
public class PostController {

    @Resource
    private PostService postService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:post:query')")
    public Result<PageResult<PostRespVO>> getUserPage(PostQueryReqVO req) {
        return Result.getSuccessful(PostConvert.INSTANCE.convert(postService.getPage(req)));
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:post:add')")
    public Result<?> add(@RequestBody PostAddReqVO req) {
        postService.verify(req.getCode());
        postService.add(PostConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:post:edit')")
    public Result<?> update(@RequestBody PostUpdateReqVO req) {
        postService.update(PostConvert.INSTANCE.convert(req));
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:post:edit')")
    public Result<?> get(@PathVariable("id") Long id) {
        postService.get(id);
        return Result.getSuccessful();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:post:remove')")
    public Result<?> remove(@PathVariable("id") Long id) {
        postService.remove(id);
        return Result.getSuccessful();
    }
}
