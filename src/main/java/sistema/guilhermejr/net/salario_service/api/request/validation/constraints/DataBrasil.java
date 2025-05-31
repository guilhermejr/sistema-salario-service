package sistema.guilhermejr.net.salario_service.api.request.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sistema.guilhermejr.net.salario_service.api.request.validation.DataBrasilValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DataBrasilValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataBrasil {

    String message() default "Data inválida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
