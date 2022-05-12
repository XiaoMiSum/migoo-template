package xyz.migoo.template.controller.manager.sys.dept;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import xyz.migoo.framework.common.enums.CommonStatusEnum;
import xyz.migoo.framework.common.pojo.Result;
import xyz.migoo.template.controller.manager.sys.dept.vo.*;
import xyz.migoo.template.convert.manager.sys.DeptConvert;
import xyz.migoo.template.dal.dataobject.sys.Dept;
import xyz.migoo.template.service.manager.sys.dept.DeptService;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {
    @Resource
    private DeptService deptService;

    @GetMapping
    @PreAuthorize("@ss.hasPermission('system:dept:query')")
    public Result<List<DeptRespVO>> getDeptPage(DeptQueryReqVO req) {
        // 获得用户分页列表
        List<Dept> list = deptService.getList(req);
        list.sort(Comparator.comparing(Dept::getSort));
        return Result.getSuccessful(DeptConvert.INSTANCE.convert(list));
    }

    @PostMapping
    @PreAuthorize("@ss.hasPermission('system:dept:add')")
    public Result<?> addDept(@RequestBody DeptAddReqVO addReq) {
        deptService.verify(null, addReq.getParentId(), addReq.getName());
        deptService.add(DeptConvert.INSTANCE.convert(addReq));
        return Result.getSuccessful();
    }

    @PutMapping
    @PreAuthorize("@ss.hasPermission('system:dept:edit')")
    public Result<?> updateDept(@RequestBody DeptUpdateReqVO updateReqVO) {
        deptService.verify(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());
        deptService.update(DeptConvert.INSTANCE.convert(updateReqVO));
        return Result.getSuccessful();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dept:edit')")
    public Result<?> getDept(@PathVariable Long id) {
        return Result.getSuccessful(DeptConvert.INSTANCE.convert(deptService.get(id)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@ss.hasPermission('system:dept:remove')")
    public Result<?> removeDept(@PathVariable Long id) {
        deptService.remove(id);
        return Result.getSuccessful();
    }

    @GetMapping("/simple")
    public Result<List<DeptSimpleRespVO>> getDeptSimple(DeptQueryReqVO req) {
        // 获得用户分页列表
        req.setStatus(CommonStatusEnum.ENABLE.getStatus());
        List<Dept> list = deptService.getList(req);
        list.sort(Comparator.comparing(Dept::getSort));
        return Result.getSuccessful(DeptConvert.INSTANCE.convert0(list));
    }
}
