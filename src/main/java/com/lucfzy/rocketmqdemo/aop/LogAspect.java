package com.lucfzy.rocketmqdemo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

// 定义切面（也就是注解接口的行为）
// 所以每当一个接口使用到该注解都会运行这一段代码，去实现相应的功能。
// 而这一段代码是利用了aop的通知功能，原理上就是代码增强
@Aspect
@Component
public class LogAspect {

    //
    @Pointcut("@annotation(com.lucfzy.rocketmqdemo.aop.Action)")
    public void annotationPoinCut(){}

//    @Before("annotationPoinCut()")
//    public void before(JoinPoint joinPoint){
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        Action action = method.getAnnotation(Action.class);
//        System.out.println("注解式拦截 "+action.name());
//    }
    @Before(value = "annotationPoinCut()")
    public void before(JoinPoint joinPoint) {
//        Method method = signature.getMethod();
//        = method.getAnnotation(Action.class);
        Action action = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Action.class);
        System.out.println("注解式拦截 "+action.name());
    }

//    @Before("annotationPoinCut1()")
//    public void round1(JoinPoint joinPoint) {
////        Method method = signature.getMethod();
////        = method.getAnnotation(Action.class);
//        Action action = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Action.class);
//        System.out.println("注解式拦截 "+action.name());
//    }

//    @Before("execution(* com.lucfzy.rocketmqdemo.aop.DemoMethodService.*(..))")
//    public void before(JoinPoint joinPoint){
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        System.out.println("方法规则式拦截,"+method.getName());
//    }
}