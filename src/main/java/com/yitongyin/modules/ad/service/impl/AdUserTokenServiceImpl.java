package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.dao.AdUserTokenDao;
import com.yitongyin.modules.ad.entity.AdUserEntity;
import com.yitongyin.modules.ad.entity.AdUserTokenEntity;
import com.yitongyin.modules.ad.oauth2.TokenGenerator;
import com.yitongyin.modules.ad.service.AdUserService;
import com.yitongyin.modules.ad.service.AdUserTokenService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdUserTokenServiceImpl extends ServiceImpl<AdUserTokenDao, AdUserTokenEntity> implements AdUserTokenService {
     @Autowired
     private AdUserService adUserService;
    //12小时后过期
    private final static int EXPIRE = 3600 * 12;


    @Override
    public R createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        AdUserTokenEntity tokenEntity = this.getById(userId);
        if(tokenEntity == null){
            tokenEntity = new AdUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            if(this.save(tokenEntity)){
                AdUserEntity userEntity = adUserService.getById(userId);
                userEntity.setLastLoginTime(now);
                adUserService.updateById(userEntity);
            }
            R r = R.ok().put("token", token).put("expire", EXPIRE).put("isFirst",true);
            return r;
        }else{
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //
            if(this.updateById(tokenEntity)){
                AdUserEntity userEntity = adUserService.getById(userId);
                userEntity.setLastLoginTime(now);
                adUserService.updateById(userEntity);
            }
            R r = R.ok().put("token", token).put("expire", EXPIRE).put("isFirst",false);
            return r;
        }

    }
    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //修改token
        AdUserTokenEntity tokenEntity = new AdUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }

    @Override
    public R createTokenByExpireTime(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        AdUserTokenEntity tokenEntity = this.getById(userId);
        if(tokenEntity == null){
            tokenEntity = new AdUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);

            //保存token
            if(this.save(tokenEntity)){
                AdUserEntity userEntity = adUserService.getById(userId);
                userEntity.setLastLoginTime(now);
                adUserService.updateById(userEntity);
            }
            R r = R.ok().put("token", token).put("expire", EXPIRE).put("isFirst",true);
            return r;
        }else{
            if(tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
                tokenEntity.setToken(token);
                tokenEntity.setUpdateTime(now);
                tokenEntity.setExpireTime(expireTime);
            }
            //
            if(this.updateById(tokenEntity)){
                AdUserEntity userEntity = adUserService.getById(userId);
                userEntity.setLastLoginTime(now);
                adUserService.updateById(userEntity);
            }
            R r = R.ok().put("token", tokenEntity.getToken()).put("expire", tokenEntity.getExpireTime()).put("isFirst",false);
            return r;
        }

    }

    @Override
    public AdUserTokenEntity getByToken(String token) {
        QueryWrapper<AdUserTokenEntity> query = new QueryWrapper<>();
        query.select("user_id");
        query.eq("token",token);
        return this.getOne(query);
    }
}
