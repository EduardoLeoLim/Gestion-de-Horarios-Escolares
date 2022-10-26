package com.gamma.gestorhorariosescolares.compartido.dominio.criterio;

import java.util.HashMap;

public final class Filter {
    private final FilterField field;
    private final FilterOperator operator;
    private final FilterValue value;
    private Boolean isObligatory;

    public Filter(FilterField field, FilterOperator operator, FilterValue value) {
        this.field = field;
        this.operator = operator;
        this.value = value;
        isObligatory = true;
    }

    public static Filter create(String field, String operator, String value) {
        return new Filter(
                new FilterField(field),
                FilterOperator.fromValue(operator.toUpperCase()),
                new FilterValue(value)
        );
    }

    public static Filter fromValues(HashMap<String, String> values) {
        return new Filter(
                new FilterField(values.get("field")),
                FilterOperator.fromValue(values.get("operator")),
                new FilterValue(values.get("value"))
        );
    }

    public FilterField field() {
        return field;
    }

    public FilterOperator operator() {
        return operator;
    }

    public FilterValue value() {
        return value;
    }

    public Boolean isObligatory() {
        return isObligatory;
    }

    public void optional() {
        isObligatory = false;
    }

    public void obligatory() {
        isObligatory = true;
    }

    public String serialize() {
        return String.format("%s.%s.%s.%s", field.value(), operator.value(), value.value(), isObligatory());
    }
}
