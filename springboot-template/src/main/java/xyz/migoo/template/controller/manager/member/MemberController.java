package xyz.migoo.template.controller.manager.member;

import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.template.controller.manager.member.vo.*;
import xyz.migoo.template.convert.manager.member.MemberConvert;
import xyz.migoo.template.dal.dataobject.Member;
import xyz.migoo.template.service.UserService;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource(name = "memberServiceImpl")
    private UserService<Member, MemberQueryReqVO> memberService;

    @GetMapping
    public Result<PageResult<MemberRespVO>> getUserPage(MemberQueryReqVO req) {
        // 获得会员分页列表
        return Result.getSuccessful(MemberConvert.INSTANCE.convert(memberService.getPage(req)));
    }

    @PostMapping
    public Result<?> addUser(@RequestBody MemberAddReqVO reqVO) {
        memberService.verify(reqVO.getPhone());
        memberService.add(MemberConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }

    @PutMapping
    public Result<?> updateUser(@RequestBody MemberUpdateReqVO reqVO) {
        memberService.update(MemberConvert.INSTANCE.convert(reqVO));
        return Result.getSuccessful();
    }


    @GetMapping("/{id}")
    public Result<?> getUser(@PathVariable Long id) {
        return Result.getSuccessful(MemberConvert.INSTANCE.convert(memberService.get(id)));
    }

    @DeleteMapping("/{id}")
    public Result<?> removeUser(@PathVariable Long id) {
        memberService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<MemberSimpleRespVO>> getSimpleMenus() {
        List<Member> list = memberService.get();
        return Result.getSuccessful(MemberConvert.INSTANCE.convert(list));
    }
}
