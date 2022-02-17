package hu.webuni.university.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;



@Component
@Aspect
public class RetryAspect {
	   
    @Pointcut("@annotation(hu.webuni.university.aspect.Retry) || @within(hu.webuni.university.aspect.Retry)")
    public void retryPointCut() {
    }
 
    @Around(/*"retryPointCut()"*/"@annotation(hu.webuni.university.aspect.Retry) || @within(hu.webuni.university.aspect.Retry)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        
    	Retry retry = null;
        Signature signature = joinPoint.getSignature();
        //TODO: retry annotáció példány kinyerése
        if(signature instanceof MethodSignature) {
        	MethodSignature methodSignature = (MethodSignature) signature;
        	Method method = methodSignature.getMethod();
        	retry = method.getAnnotation(Retry.class);
        } else {
        	Class<?> declaringType = signature.getDeclaringType();
        	retry = declaringType.getAnnotation(Retry.class);
        }
        
        
        int times = retry.times();
        long waitTime = retry.waitTime();
        
        if (times <= 0) {
            times = 1;
        }
 
        for (int numTry=1; numTry <= times; numTry++) {
        	
        	System.out.format("Try times: %d %n", numTry);
            
        	try {
                return joinPoint.proceed();
            } catch (Exception e) {

                if (numTry == times) 
                    throw e;
 
                if (waitTime > 0) 
                    Thread.sleep(waitTime);
            }
        }
        
        return null;	//soha nem jutunk ide
    }
}