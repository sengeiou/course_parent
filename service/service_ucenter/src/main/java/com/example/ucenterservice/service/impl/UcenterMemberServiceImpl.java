package com.example.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.CommonUtil.jwtUtil.JwtUtils;
import com.example.CommonUtil.jwtUtil.MD5;
import com.example.CommonUtil.orderInfo.OrderMemberInfo;
import com.example.base.exceptionHandler.ExceptionInfo;
import com.example.ucenterservice.entity.UcenterMember;
import com.example.ucenterservice.entity.vo.RegisterVo;
import com.example.ucenterservice.mapper.UcenterMemberMapper;
import com.example.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-12-30
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Override
    public String login(UcenterMember member) {
        String userPhone=member.getMobile();
        String password=member.getPassword();
        //非空判断
        if (StringUtils.isEmpty(userPhone)||StringUtils.isEmpty(password)){
            throw  new ExceptionInfo(20001,"登录失败!");
        }
        //根据手机号查询
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",userPhone);
        UcenterMember user = baseMapper.selectOne(queryWrapper);
        if (user==null){
            throw  new ExceptionInfo(20001,"登录失败!");
        }
        //判断(加密)密码是否正确
        if (!MD5.encrypt(password).equals(user.getPassword())){
            throw  new ExceptionInfo(20001,"登录失败!");
        }
        //判断用户是否被禁用
        if (user.getIsDisabled()){
            throw  new ExceptionInfo(20001,"登录失败!");
        }
        //登录成功,通jwt生成token
        String token = JwtUtils.getJwtToken(user.getId(), user.getNickname());
        return token;
    }

    @Override
    public boolean register(RegisterVo registerVo) {
        String code=registerVo.getCode();
        String nickName=registerVo.getNickname();
        String userPhone=registerVo.getMobile();
        String password=registerVo.getPassword();
        //判断是否为空
        if (StringUtils.isEmpty(code)||StringUtils.isEmpty(nickName)||
                StringUtils.isEmpty(userPhone)||StringUtils.isEmpty(password)){
            throw  new ExceptionInfo(20001,"注册失败!");
        }
        //判断验证码
        //判断手机号是否重复
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("mobile",userPhone);
        UcenterMember ucenterMember = baseMapper.selectOne(queryWrapper);
        if (ucenterMember!=null){
            throw  new ExceptionInfo(20001,"注册失败!手机号已注册");
        }
        //注册成功
        UcenterMember member=new UcenterMember();
        member.setMobile(userPhone);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickName);
        member.setIsDisabled(false); //用户不禁用
        member.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        int flag = baseMapper.insert(member);
        return flag > 0;
    }

    @Override
    public UcenterMember getInfoByToken(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        QueryWrapper<UcenterMember> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("id",memberId);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        return member;
    }

    @Override
    public OrderMemberInfo getByMemberId(String memberId) {
        UcenterMember member = baseMapper.selectById(memberId);
        OrderMemberInfo orderMemberInfo=new OrderMemberInfo();
        BeanUtils.copyProperties(member,orderMemberInfo);
        return orderMemberInfo;
    }
}
