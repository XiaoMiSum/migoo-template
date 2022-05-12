package xyz.migoo.template.convert.manager.member;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.migoo.framework.common.pojo.PageResult;
import xyz.migoo.template.controller.manager.member.vo.MemberAddReqVO;
import xyz.migoo.template.controller.manager.member.vo.MemberRespVO;
import xyz.migoo.template.controller.manager.member.vo.MemberSimpleRespVO;
import xyz.migoo.template.controller.manager.member.vo.MemberUpdateReqVO;
import xyz.migoo.template.dal.dataobject.Member;

import java.util.List;

@Mapper
public interface MemberConvert {

    MemberConvert INSTANCE = Mappers.getMapper(MemberConvert.class);

    PageResult<MemberRespVO> convert(PageResult<Member> result);

    List<MemberSimpleRespVO> convert(List<Member> list);

    Member convert(MemberAddReqVO reqVO);

    Member convert(MemberUpdateReqVO reqVO);

    MemberRespVO convert(Member member);
}
