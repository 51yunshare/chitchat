package com.chitchat.portal.aspect;

import cn.hutool.json.JSONUtil;
import com.chitchat.portal.annotation.EntryRoom;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Js 2022/9/8.
 */
//@Aspect
@Slf4j
//@Component
public class EntryRoomAspect {


    @Pointcut("@annotation(com.chitchat.portal.annotation.EntryRoom)")
    public void entryRoomPoint(){}


    @Around("entryRoomPoint()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        EntryRoom opLog = annotationClassProperty(joinPoint, EntryRoom.class);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String token = TokenUtil.getToken(request);
//        if(StringUtil.isBlank(token)){
//            throw new AdmsException(CodeMsg.LOGIN_INVALID, CodeMsg.LOGIN_INVALID.getMsg());
//        }
        Object responseValue = null;
        Object[] methodParams = null;
        String requestUrl = request.getRequestURI();
        long callTime = -1; //调用毫秒数
        long startTime = 0;
        long endTime = 0;

        try{
            startTime = System.currentTimeMillis();
            methodParams = joinPoint.getArgs(); //获取方法参数
            //过略文件下载接口的servlet对象 再次提交
            List<Object> objects = Arrays.asList(methodParams);
            //去除文件上传 下载对象的参数日志打印
            methodParams = objects.stream().filter(o -> !(o instanceof HttpServletResponse)).filter(o -> !(o instanceof MultipartFile)).collect(Collectors.toList()).toArray();
            responseValue = joinPoint.proceed();
            endTime = System.currentTimeMillis();
            callTime = endTime - startTime;
        }catch (Exception e){
            log.error("请求响应异常，接口地址=【{}】,startTime =【{}】，endTime=【{}】,耗时=【{}ms】，出现异常=【{}】", requestUrl, startTime,endTime,callTime,e.getMessage());
            throw e;
        }finally {
            try {
                log.info(">>>>>>>>>>>>> Controller执行之后 >>>>>>>>>>>>");
                String paramJsonStr = JSONUtil.toJsonStr(methodParams);
//                Integer targetId = AspectUtil.getTargetId(joinPoint,request,projectUrl);
//                operaLogRestServicesI.doInsert(OperaLog.builder()
//                        .projectId(AspectUtil.getProjectId(joinPoint,request,projectUrl))
//                        .targetId(targetId == null ? TargetIdThreadLocal.getId() : targetId)
//                        .operaFunction(opLog.function().getIndex())
//                        .operaDetail(opLog.description())
//                        .operaUserId(userInfo.getId())
//                        .operaUserRealName(userInfo.getRealName())
//                        .requestParamJson(paramJsonStr)
//                        .responseJson(JSON.toJSONString(responseValue))
//                        .state(state)
//                        .callTime(callTime)
//                        .build());
            } catch (Exception ex) {
                log.error("记录用户操作日志异常:" + ExceptionUtils.getStackTrace(ex));
            }
        }


        return responseValue;
    }


    public <T extends Annotation> T  annotationClassProperty(ProceedingJoinPoint joinPoint , Class<T> annotationClass) throws ClassNotFoundException {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    return method.getAnnotation(annotationClass);
                }
            }
        }
        return null;
    }
}
