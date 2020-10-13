package com.jesse.learn.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class WebLogAspect {
    private static final Logger log = LoggerFactory.getLogger(WebLogAspect.class);
    private static volatile AtomicInteger count = new AtomicInteger(0);
    private static Map<Method, AtomicInteger> map = new ConcurrentHashMap();
    ThreadLocal<Long> startTime = new ThreadLocal();

    public WebLogAspect() {
    }


    @Pointcut("@annotation(com.jesse.learn.annotation.LogNotion)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        this.startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("【WebLogAspect.doBefore】 URL : " + request.getRequestURL().toString());
        log.info("【WebLogAspect.doBefore】 HTTP_METHOD : " + request.getMethod());
        log.info("【WebLogAspect.doBefore】 IP : " + request.getRemoteAddr());
        log.info("【WebLogAspect.doBefore】 CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("【WebLogAspect.doBefore】 ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object var17;
        try {
            if (count.incrementAndGet() > 180) {
                throw new Exception("服务繁忙");
            }

            Signature signature = pjp.getSignature();
            if (signature instanceof MethodSignature) {
                Method method = ((MethodSignature)signature).getMethod();
                AtomicInteger num = (AtomicInteger)map.get(method);
                if (Objects.isNull(num)) {
                    synchronized(map) {
                        num = (AtomicInteger)map.get(method);
                        if (Objects.isNull(num)) {
                            num = new AtomicInteger(0);
                            map.put(method, num);
                        }
                    }
                }

                if (num.incrementAndGet() > 15) {
                    num.decrementAndGet();
                    log.info("请求被拒绝:{}{}", method.getDeclaringClass(), method.getName());
                    throw new Exception("服务繁忙");
                }

                num.decrementAndGet();
            }

            Object object = pjp.proceed();
            log.info("【WebLogAspect.doAround】total duration.{}", System.currentTimeMillis() - startTime);
            var17 = object;
        } catch (Exception var14) {
            log.info("调用接口失败");
            throw var14;
        } finally {
            count.decrementAndGet();
        }

        return var17;
    }

    @AfterReturning(
            returning = "ret",
            pointcut = "webLog()"
    )
    public void doAfterReturning(Object ret) throws Throwable {
        log.info("【WebLogAspect.doAfterReturning】RESPONSE : " + ret);
        log.info("【WebLogAspect.doAfterReturning】SPEND TIME : " + (System.currentTimeMillis() - (Long)this.startTime.get()));
    }
}
