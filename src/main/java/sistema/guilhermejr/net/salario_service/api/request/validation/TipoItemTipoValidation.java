package sistema.guilhermejr.net.salario_service.api.request.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sistema.guilhermejr.net.salario_service.api.request.validation.constraints.TipoItemTipo;

public class TipoItemTipoValidation implements ConstraintValidator<TipoItemTipo, Character> {

    @Override
    public void initialize(TipoItemTipo constraintAnnotation) {
    }

    @Override
    public boolean isValid(Character tipo, ConstraintValidatorContext constraintValidatorContext) {

        if (tipo == null) {
            return false;
        }

        return tipo == 'P' || tipo == 'D';

    }

}
