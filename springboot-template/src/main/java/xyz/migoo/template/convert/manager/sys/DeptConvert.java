package xyz.migoo.template.convert.manager.sys;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.template.controller.manager.sys.dept.vo.DeptAddReqVO;
import xyz.migoo.template.controller.manager.sys.dept.vo.DeptRespVO;
import xyz.migoo.template.controller.manager.sys.dept.vo.DeptSimpleRespVO;
import xyz.migoo.template.controller.manager.sys.dept.vo.DeptUpdateReqVO;
import xyz.migoo.template.dal.dataobject.sys.Dept;

import java.util.List;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    Dept convert(DeptAddReqVO updateReq);

    Dept convert(DeptUpdateReqVO updateReq);

    DeptRespVO convert(Dept list);

    List<DeptRespVO> convert(List<Dept> list);

    List<DeptSimpleRespVO> convert0(List<Dept> list);
}
