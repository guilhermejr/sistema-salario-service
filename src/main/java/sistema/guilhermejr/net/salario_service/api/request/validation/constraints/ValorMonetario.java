package sistema.guilhermejr.net.salario_service.api.request.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sistema.guilhermejr.net.salario_service.api.request.validation.ValorMonetarioValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValorMonetarioValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValorMonetario {

    String message() default "Valor inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
