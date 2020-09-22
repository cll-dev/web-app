package com.yitongyin.common.interceptor;

import com.yitongyin.common.annotion.DuplicateSubmitToken;
import com.yitongyin.common.exception.DuplicateSubmitException;
import com.yitongyin.common.exception.RRException;
import com.yitongyin.modules.ad.entity.AdUserEntity;
import com.yitongyin.modules.ad.entity.AdUserTokenEntity;
import com.yitongyin.modules.ad.service.AdUserTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Aspect
@Component
@Slf4j
public class DuplicateSubmitAspect {
    public  String  DUPLICATE_TOKEN_KEY="";
    @Autowired
    AdUserTokenService adUserTokenService;

    @Pointcut("execution(public * com.yitongyin.modules.ad.controller..*(..))")

    public void webLog() {
    }

    @Before("webLog() && @annotation(token)")
    public void before(final JoinPoint joinPoint, DuplicateSubmitToken token){
        AdUserEntity userEntity=(AdUserEntity)SecurityUtils.getSubject().getPrincipal();
        AdUserTokenEntity tokenEntity = adUserTokenService.getById(userEntity.getUserId());
        DUPLICATE_TOKEN_KEY=tokenEntity.getToken();
        if (token!=null){
            Object[]args=joinPoint.getArgs();
            HttpServletRequest request=null;
            HttpServletResponse response=null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest){
                    request= (HttpServletRequest) args[i];
                }
                if (args[i] instanceof HttpServletResponse){
                    response= (HttpServletResponse) args[i];
                }
            }

            boolean isSaveSession=token.save();
            if (isSaveSession){
                String key = getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                if (null==t){
                    String uuid= UUID.randomUUID().toString();
                    request.getSession().setAttribute(key.toString(),uuid);
                    log.info("token-key="+key);
                    log.info("token-value="+uuid.toString());
                }else {
                    throw new RRException("请不要重复提交");
                }
            }

        }
    }

    /**
     * 获取重复提交key-->duplicate_token_key+','+请求方法名
     * @param joinPoint
     * @return
     */
    public String getDuplicateTokenKey(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        StringBuilder key=new StringBuilder(DUPLICATE_TOKEN_KEY);
        key.append(",").append(methodName);
        return key.toString();
    }

    @AfterReturning("webLog() && @annotation(token)")
    public void doAfterReturning(JoinPoint joinPoint,DuplicateSubmitToken token) {
        // 处理完请求，返回内容
        log.info("出来方法：");
        if (token!=null){
            Object[]args=joinPoint.getArgs();
            HttpServletRequest request=null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest){
                    request= (HttpServletRequest) args[i];
                }
            }
            boolean isSaveSession=token.save();
            if (isSaveSession){
                String key = getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                if (null!=t){
                    //方法执行完毕移除请求重复标记
                    request.getSession(false).removeAttribute(key);
                    log.info("方法执行完毕移除请求重复标记！");
                }
            }
        }
    }

    /**
     * 异常
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "webLog()&& @annotation(token)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e, DuplicateSubmitToken token) {
        if (null!=token
                && e instanceof DuplicateSubmitException==false){
            //处理处理重复提交本身之外的异常
            Object[]args=joinPoint.getArgs();
            HttpServletRequest request=null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest){
                    request= (HttpServletRequest) args[i];
                }
            }
            boolean isSaveSession=token.save();
            //获得方法名称
            if (isSaveSession){
                String key=getDuplicateTokenKey(joinPoint);
                Object t = request.getSession().getAttribute(key);
                if (null!=t){
                    //方法执行完毕移除请求重复标记
                    request.getSession(false).removeAttribute(key);
                    log.info("异常情况--移除请求重复标记！");
                }
            }
        }
    }
}
