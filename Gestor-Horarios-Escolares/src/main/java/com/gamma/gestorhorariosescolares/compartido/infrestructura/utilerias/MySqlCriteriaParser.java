package com.gamma.gestorhorariosescolares.compartido.infrestructura.utilerias;

import com.gamma.gestorhorariosescolares.compartido.dominio.criterio.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MySqlCriteriaParser {

    private final String tabla;
    private final List<String> columnas;
    private final List<Filter> filtrosObligatorios;
    private final List<Filter> filtrosOpcionales;
    private final Criteria criteria;

    public MySqlCriteriaParser(String tabla, Criteria criteria) {
        if (tabla == null)
            throw new NullPointerException();

        this.tabla = tabla;
        this.criteria = criteria;

        columnas = new ArrayList<>();
        filtrosObligatorios = new ArrayList<>();
        this.filtrosObligatorios.addAll(criteria.filters().filters().stream()
                .filter(filter -> filter.isObligatory())
                .collect(Collectors.toList())
        );

        filtrosOpcionales = new ArrayList<>();
        this.filtrosOpcionales.addAll(criteria.filters().filters().stream()
                .filter(filter -> !filter.isObligatory())
                .collect(Collectors.toList())
        );
    }


    public void agregarCampos(String... columnas) {
        this.columnas.addAll(List.of(columnas));
    }

    public String generarConsulta() {
        String consulta = "SELECT " + generarColumnas() + " FROM " + tabla;
        String condiciones = generarCondiciones();
        if (criteria.hasFilters()) {
            consulta += " WHERE " + condiciones;
        }

        consulta += ";";

        return consulta;
    }

    public String generarConsultaSql2o() {
        String consulta = "SELECT " + generarColumnas() + " FROM " + tabla;

        if (criteria.hasFilters()) {
            String condiciones = generarCondicionesSql2o();
            consulta += " WHERE " + condiciones;
        }

        Order orden = criteria.order();
        if (orden.orderType() != OrderType.NONE) {
            consulta += " ORDER BY " + orden.orderBy().value() + " " + orden.orderType().value().toUpperCase();
        }

        if (criteria.limit().isPresent()) {
            consulta += " LIMIT " + criteria.limit().get();
            if (criteria.offset().isPresent())
                consulta += " OFFSET " + criteria.offset().get();
        } else if (!criteria.offset().isEmpty()) {
            // https:dev.mysql.com/doc/refman/5.6/en/select.html
            consulta += " LIMIT " + criteria.offset().get() + ",18446744073709551615";
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

        if (!filtrosObligatorios.isEmpty()) {
            String condicionesObligatorias = filtrosObligatorios.stream().map(filter -> generarCondicion(filter))
                    .collect(Collectors.joining(" AND ", "(", ")"));
            condiciones.add(condicionesObligatorias);
        }

        if (!filtrosOpcionales.isEmpty()) {
            String condicionesOpcionales = filtrosOpcionales.stream().map(filter -> generarCondicion(filter))
                    .collect(Collectors.joining(" OR ", "(", ")"));
            condiciones.add(condicionesOpcionales);
        }

        String cadenaCondiciones = "";
        if (!condiciones.isEmpty())
            cadenaCondiciones = condiciones.stream().collect(Collectors.joining(" AND ", "(", ")"));

        return cadenaCondiciones;
    }

    private String generarCondicionesSql2o() {
        List<String> condiciones = new ArrayList<>();
        int numeroFiltro = 0;

        if (!filtrosObligatorios.isEmpty()) {
            List<String> listaCondiciones = new ArrayList<>();
            for (Filter filtro : filtrosObligatorios) {
                numeroFiltro += 1;
                listaCondiciones.add(generarCondicionSql2o(filtro, numeroFiltro));
            }
            String condicionesObligatorias = listaCondiciones.stream()
                    .collect(Collectors.joining(" AND ", "(", ")"));
            condiciones.add(condicionesObligatorias);
        }

        if (!filtrosOpcionales.isEmpty()) {
            List<String> listaCondiciones = new ArrayList<>();
            for (Filter filtro : filtrosOpcionales) {
                numeroFiltro += 1;
                listaCondiciones.add(generarCondicionSql2o(filtro, numeroFiltro));
            }
            String condicionesOpcionales = listaCondiciones.stream()
                    .collect(Collectors.joining(" OR ", "(", ")"));
            condiciones.add(condicionesOpcionales);
        }

        String cadenaCondiciones = "";
        if (!condiciones.isEmpty())
            cadenaCondiciones = condiciones.stream().collect(Collectors.joining(" AND ", "(", ")"));

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

    private String generarCondicionSql2o(Filter filtro, int numeroParamentro) {
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

        condicion += ":parametro" + numeroParamentro;

        return condicion;
    }

    public List<Map<Integer, String>> generarParametrosSql2o() {
        List<Map<Integer, String>> parametros = new ArrayList<>();
        int numeroParametro = 0;
        for (Filter filtro : filtrosObligatorios) {
            numeroParametro += 1;
            Map<Integer, String> parametro = new HashMap<>();
            parametro.put(0, "parametro" + numeroParametro);
            if (filtro.operator() == FilterOperator.CONTAINS || filtro.operator() == FilterOperator.NOT_CONTAINS) {
                String valor = "%" + filtro.value().value() + "%";
                parametro.put(1, valor);
            } else {
                parametro.put(1, filtro.value().value());
            }
            parametros.add(parametro);
        }

        for (Filter filtro : filtrosOpcionales) {
            numeroParametro += 1;
            Map<Integer, String> parametro = new HashMap<>();
            parametro.put(0, "parametro" + numeroParametro);
            if (filtro.operator() == FilterOperator.CONTAINS || filtro.operator() == FilterOperator.NOT_CONTAINS) {
                String valor = "%" + filtro.value().value() + "%";
                parametro.put(1, valor);
            } else {
                parametro.put(1, filtro.value().value());
            }
            parametros.add(parametro);
        }

        return parametros;
    }
}
