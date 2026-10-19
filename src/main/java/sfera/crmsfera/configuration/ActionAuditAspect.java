package sfera.crmsfera.configuration;

import lombok.RequiredArgsConstructor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import sfera.crmsfera.service.ActionEventService;

@Aspect
@Component
@RequiredArgsConstructor
public class ActionAuditAspect {

    private final ActionEventService actionEventService;

    @AfterReturning(
            value = "@annotation(trackAction)",
            returning = "result"
    )
    public void afterSuccess(
            JoinPoint jp,
            TrackAction trackAction,
            Object result
    ) {
        actionEventService.handle(
                trackAction.type(),
                trackAction.description(),
                result
        );
    }
}
