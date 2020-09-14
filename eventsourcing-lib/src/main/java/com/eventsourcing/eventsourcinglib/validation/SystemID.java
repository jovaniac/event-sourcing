package com.eventsourcing.eventsourcinglib.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import com.eventsourcing.eventsourcinglib.util.CommonConstants;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = SystemIDValidator.class)
public @interface SystemID {

String message() default CommonConstants.SYS_ID_VALIDATION_MESSAGE;

Class<?>[] groups() default { };

Class<? extends Payload>[] payload() default { };
}