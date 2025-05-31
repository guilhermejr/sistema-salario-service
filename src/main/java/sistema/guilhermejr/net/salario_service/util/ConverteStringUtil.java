package sistema.guilhermejr.net.salario_service.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import sistema.guilhermejr.net.salario_service.exception.ExceptionDefault;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

@Log4j2
@Component
public class ConverteStringUtil {

    public LocalDate toLocalDate(String data) {

        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        return LocalDate.parse(data, parser);

    }

    public String toStringBigDecimal(BigDecimal valor) {
//        Locale local = new Locale("pt","BR");
//        NumberFormat nf = NumberFormat.getCurrencyInstance(local);
//        return nf.format(valor);

        if (valor == null) {
            valor = BigDecimal.ZERO;
        }

        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return df.format(valor);

    }

    public String toStringData(LocalDate data) {

        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        return data.format(parser);
    };

    public BigDecimal toBigDecimal(String valor) {

        if (valor == null) {
            valor = "0";
        }

        try {
            return new BigDecimal(valor.replace(".", "").replace(',','.'));
        } catch (Exception ex) {
            log.error("Erro ao converter String para BigDecimal");
            throw new ExceptionDefault("Erro ao converter String para BigDecimal");
        }

    }

}
