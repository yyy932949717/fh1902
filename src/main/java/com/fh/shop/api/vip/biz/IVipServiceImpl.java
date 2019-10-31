package com.fh.shop.api.vip.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.Code;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SytemConst;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.PhoneCodeUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.vip.mapper.IVipMapper;
import com.fh.shop.api.vip.po.Vip;
import com.fh.shop.api.vip.vo.VipVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service("vipService")
public class IVipServiceImpl implements IVipService {
    @Autowired
    private IVipMapper vipMapper;
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";


    @Override
    public ServerResponse addVip(Vip vip) {

        if (StringUtils.isEmpty(vip.getName())){  //验证用户名是否为空
            return ServerResponse.error(ResponseEnum.VIP_NAME_IS_NULL);
        }
        if(StringUtils.isEmpty(vip.getPhone())){  //验证手机号码是否为空
            return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        if(StringUtils.isEmpty(vip.getVipCode())){ //验证验证码是否为空
            return ServerResponse.error(ResponseEnum.VIP_CODE_IS_NULL);
        }
        if(StringUtils.isEmpty(vip.getEmail())){ //邮箱是否为空
            return ServerResponse.error(ResponseEnum.VIP_EMAIL_NULL);
        }

        String codeJson = RedisUtil.get(KeyUtil.buildPhoneKey(vip.getPhone()));
        if(StringUtils.isEmpty(codeJson)){ //redis中是否存在
            return ServerResponse.error(ResponseEnum.REDIS_CODE_IS_ERROR);
        }
        if (!codeJson.equals(vip.getVipCode())){
            return ServerResponse.error(ResponseEnum.PHONE_CODE_ERROR);
        }

        //保证 用户名 手机号码 邮箱唯一
        QueryWrapper nameQuery=new QueryWrapper();
        nameQuery.eq("name",vip.getName());
        Vip nameVip = vipMapper.selectOne(nameQuery);
        if (nameVip!=null){
            return ServerResponse.error(ResponseEnum.VIP_NAME_EXIST);
        }
        QueryWrapper phoneQuery=new QueryWrapper();
        phoneQuery.eq("phone",vip.getPhone());
        Vip phoneVip = vipMapper.selectOne(phoneQuery);
        if (phoneVip!=null){
            return ServerResponse.error(ResponseEnum.VIP_PHONE_EXIST);
        }
        QueryWrapper emailQuery=new QueryWrapper();
        emailQuery.eq("email",vip.getEmail());
        Vip emailVip = vipMapper.selectOne(emailQuery);
        if(emailVip!=null){
            return ServerResponse.error(ResponseEnum.VIP_EMAIL_EXIST);
        }

        vipMapper.insert(vip);
        return ServerResponse.success();
    }


    //redis 验证码存活时间
    private static final int miao=3*60;
    @Override
    public ServerResponse getCode(String phone) {
        //手机号码为空
        if (StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        //正则验证是否是手机号码
        if(!phone.matches(REGEX_MOBILE)){
            return ServerResponse.error(ResponseEnum.VIP_PHONE_NO);
        }

        String code = PhoneCodeUtil.sendPhoneCode(phone);
        //验证验证码是否发送成功
            Code code1 = JSONObject.parseObject(code, Code.class);
        //发成失败返回
        int phoneCode = code1.getCode();
        if (phoneCode !=200){
            return ServerResponse.error(ResponseEnum.PHONECODE_IS_ERROE);
        };
        //发送成功 存入到redis中 并返回客户端状态信息
        String obj = code1.getObj();
        RedisUtil.setEx(KeyUtil.buildPhoneKey(phone),obj,miao);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse verify(String name) {
        if(StringUtils.isEmpty(name)){
            return ServerResponse.error(ResponseEnum.VIP_NAME_IS_NULL);
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",name);
        Vip vip1 = vipMapper.selectOne(queryWrapper);
        if(vip1!=null){
            return   ServerResponse.error(ResponseEnum.VIP_NAME_EXIST);
        }
            return   ServerResponse.success();
    }

    @Override
    public ServerResponse verifyPhone(String phone) {
        if(StringUtils.isEmpty(phone)){
            return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        //正则验证是否是手机号码
        if(!phone.matches(REGEX_MOBILE)){
            return ServerResponse.error(ResponseEnum.VIP_PHONE_NO);
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("phone",phone);
        Vip vip1 = vipMapper.selectOne(queryWrapper);
        if(vip1!=null){
            return   ServerResponse.error(ResponseEnum.VIP_PHONE_EXIST);
        }
        return   ServerResponse.success();
    }

    @Override
    public ServerResponse verifyEmail(String email) {
        if(StringUtils.isEmpty(email)){
            return ServerResponse.error(ResponseEnum.VIP_EMAIL_NULL );
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("email",email);
        Vip vip1 = vipMapper.selectOne(queryWrapper);
        if(vip1!=null){
            return   ServerResponse.error(ResponseEnum.VIP_EMAIL_EXIST);
        }
        return   ServerResponse.success();
    }

    @Override
    public ServerResponse login(Vip vip) {
        //用信息的非空判断
        if(StringUtils.isEmpty(vip.getName())){
            return ServerResponse.error(ResponseEnum.VIP_NAME_IS_NULL);
        }
        if(StringUtils.isEmpty(vip.getPassword())){
            return ServerResponse.error(ResponseEnum.VIP_PASSWORD_NULL);
        }
        //用户是否存在
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("name",vip.getName());
        Vip vipInfo = vipMapper.selectOne(queryWrapper);
        if (null==vipInfo){
            return ServerResponse.error(ResponseEnum.VIPINFO_IS_NULL);
        }
        //密码是否正确
        if (!vip.getPassword().equals(vipInfo.getPassword())){
            return ServerResponse.error(ResponseEnum.VIP_PASSWORD_ERROR);
        }
        //生成编码返回 并设置该用户redis时间
        String uuid = UUID.randomUUID().toString();

        VipVo vo=new VipVo();
        vo.setName(vipInfo.getName());
        vo.setUuid(uuid);
        vo.setId(vipInfo.getId());
        vo.setRealName(vipInfo.getRealName());

        String jsonString = JSONObject.toJSONString(vo);
        String base64 = Base64.getEncoder().encodeToString(jsonString.getBytes());

        String qianMing = Md5Util.qianMing(base64, SytemConst.MI_YAO);
        String qianming64 = Base64.getEncoder().encodeToString(qianMing.getBytes());
        String string=base64+"."+qianming64;

        //设置redis 存活时间
        RedisUtil.setEx(KeyUtil.buidVipKey(vipInfo.getName(),uuid),"ok",SytemConst.VIP_REDIS);

        return ServerResponse.success(string);
    }

    @Override
    public ServerResponse phoneLogin(Vip vip) {
        String phone = vip.getPhone();
        String vipCode = vip.getVipCode();
        if (phone==null){
        return ServerResponse.error(ResponseEnum.PHONE_IS_NULL);
        }
        if(vipCode==null){
            return ServerResponse.error(ResponseEnum.VIP_CODE_IS_NULL);
        }
        //正则验证是否是手机号码
        if(!phone.matches(REGEX_MOBILE)){
            return ServerResponse.error(ResponseEnum.VIP_PHONE_NO);
        }
        String codeJson = RedisUtil.get(KeyUtil.buildPhoneKey(phone));
        //redis中是否存在
        if(StringUtils.isEmpty(codeJson)){
            return ServerResponse.error(ResponseEnum.REDIS_CODE_IS_ERROR);
        }
        //验证码错误
        if (!codeJson.equals(vipCode)){
            return ServerResponse.error(ResponseEnum.PHONE_CODE_ERROR);
        }
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("phone",phone);
        Vip vipInfo = vipMapper.selectOne(queryWrapper);
        if(vipInfo==null){
            return ServerResponse.error(ResponseEnum.VIPPHONE_ERROR);
        }
        String uuid = UUID.randomUUID().toString();
        VipVo vo=new VipVo();
        vo.setName(vipInfo.getName());
        vo.setUuid(uuid);
        vo.setId(vipInfo.getId());
        vo.setRealName(vipInfo.getRealName());

        String jsonString = JSONObject.toJSONString(vo);
        String base64 = Base64.getEncoder().encodeToString(jsonString.getBytes());

        String qianMing = Md5Util.qianMing(base64, SytemConst.MI_YAO);
        String qianming64 = Base64.getEncoder().encodeToString(qianMing.getBytes());
        String string=base64+"."+qianming64;

        //设置redis 存活时间
        RedisUtil.setEx(KeyUtil.buidVipKey(vipInfo.getName(),uuid),"ok",SytemConst.VIP_REDIS);

        return ServerResponse.success(string);

    }
}
