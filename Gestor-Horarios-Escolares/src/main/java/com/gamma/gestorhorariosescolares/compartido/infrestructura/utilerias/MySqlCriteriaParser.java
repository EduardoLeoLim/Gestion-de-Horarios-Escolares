package com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias;

import com.gamma.gestorhorariosescolares.compartido.dominio.CriteriaParser;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Criteria;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.Filter;
import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.FilterOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySqlCriteriaParser implements CriteriaParser {

    private final String tabla;
    private final List<String> columnas;
    private Criteria criteria;

    public MySqlCriteriaParser(String tabla) throws Exception {
        if (tabla == null)
            throw new NullPointerException();
        if (tabla.isBlank())
            throw new Exception();

        this.tabla = tabla;
        columnas = new ArrayList<>();
    }


    public void agregarCampos(String... columnas) {
        this.columnas.addAll(List.of(columnas));
    }

    public void agregarCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public String generarConsulta() {
        String consulta = "SELECT " + generarColumnas() + " FROM " + tabla;
        String condiciones = generarCondiciones();
        if (!condiciones.isEmpty()) {
            consulta += " WHERE " + condiciones;
        }

        consulta += ";";

        return consulta;
    }

    private String generarColumnas() {
        if (this.columnas.isEmpty())
            return "*";

        return this.columnas.stream().collect(Collectors.joining(", "));
    }

    private String generarCondiciones() {
        List<String> condiciones = new ArrayList<>();

        List<Filter> filtrosObligatorios = criteria.filters().filters().stream().filter(filter -> filter.isObligatory())
                .collect(Collectors.toList());
        List<Filter> filtrosOpcionales = criteria.filters().filters().stream().filter(filter -> !filter.isObligatory())
                .collect(Collectors.toList());

        if (!filtrosObligatorios.isEmpty()) {
            String condicionesObligatorias = filtrosObligatorios.stream().map(filter -> generarCondicion(filter))
                    .collect(Collectors.joining(" AND ", "(",")"));
            condiciones.add(condicionesObligatorias);
        }

        if (!filtrosOpcionales.isEmpty()) {
            String condicionesOpcionales = filtrosOpcionales.stream().map(filter -> generarCondicion(filter))
                    .collect(Collectors.joining(" OR ", "(",")"));
            condiciones.add(condicionesOpcionales);
        }

        String cadenaCondiciones = "";
        if (!condiciones.isEmpty())
            cadenaCondiciones = condiciones.stream().collect(Collectors.joining(") AND (", "(", ")"));

        return cadenaCondiciones;
    }

    private String generarCondicion(Filter filtro) {
        FilterOperator operador = filtro.operator();

        String condicion = "";
        condicion += filtro.field().value();
        switch (operador) {
            case EQUAL -> condicion += " = ";
            case NOT_EQUAL -> condicion += " != ";
            case GT -> condicion += " > ";
            case GTE -> condicion += " >= ";
            case LT -> condicion += " < ";
            case LTE -> condicion += " <= ";
            case CONTAINS -> condicion += " LIKE ";
            case NOT_CONTAINS -> condicion += " NOT LIKE ";
            default -> condicion += "";
        }

        if (operador == FilterOperator.CONTAINS || operador == FilterOperator.NOT_CONTAINS) {
            condicion += "'%" + filtro.value().value() + "%'";
        } else {
            condicion += "'" + filtro.value().value() + "'";
        }

        return condicion;
    }
}
