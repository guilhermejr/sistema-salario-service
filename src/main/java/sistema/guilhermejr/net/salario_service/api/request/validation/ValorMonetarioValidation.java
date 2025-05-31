package sistema.guilhermejr.net.salario_service.api.request.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import sistema.guilhermejr.net.salario_service.api.request.validation.constraints.ValorMonetario;

public class ValorMonetarioValidation implements ConstraintValidator<ValorMonetario, String> {

    @Override
    public void initialize(ValorMonetario constraintAnnotation) {
    }

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {

        if (valor == null) {
            return true;
        }

        return valor.matches("^([1-9]{1}[\\d]{0,2}(\\.[\\d]{3})*(\\,[\\d]{0,2})?|[1-9]{1}[\\d]{0,}(\\,[\\d]{0,2})?|0(\\,[\\d]{0,2})?|(\\,[\\d]{1,2})?)$");

    }
}
