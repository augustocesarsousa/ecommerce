package com.ecommerce.ms_usuario.utils;

import com.ecommerce.ms_usuario.records.EnumRecord;

import java.util.*;

public class EnumUtil {

    public static <E extends Enum<E>> List<EnumRecord> convertEnumToList(Class<E> enumClass) {
        List<EnumRecord> lista = new ArrayList<>();
        E[] enumValues = enumClass.getEnumConstants();

        if(enumValues != null) {
            for (int i = 0; i < enumValues.length; i++) {
                lista.add(new EnumRecord(i, enumValues[i].name()));
            }
        }

        return lista;
    }
}
