package sistema.guilhermejr.net.salario_service.api.request.validation.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import sistema.guilhermejr.net.salario_service.api.request.validation.TipoItemTipoValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TipoItemTipoValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TipoItemTipo {

    String message() default "Tipo inválido.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
