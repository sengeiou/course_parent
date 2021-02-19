package com.example.ucenterservice.service;

import com.example.CommonUtil.orderInfo.OrderMemberInfo;
import com.example.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ucenterservice.entity.vo.RegisterVo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-12-30
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    boolean register(RegisterVo registerVo);

    UcenterMember getInfoByToken(HttpServletRequest request);

    OrderMemberInfo getByMemberId(String memberId);
}
