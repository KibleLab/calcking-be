package xyz.calcking.api.calckingwebbe.annotations;

import java.lang.annotation.ElementType; // Add this import statement
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logger {
}
