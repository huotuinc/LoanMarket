package com.huotu.loanmarket.service.support;


import com.huotu.loanmarket.common.enums.EnumHelper;
import com.huotu.loanmarket.common.enums.ICommonEnum;
import com.huotu.loanmarket.service.enums.UserAuthorizedStatusEnums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.lang.reflect.ParameterizedType;

/**
 * 枚举通用converter
 *
 * @author helloztt
 */
public class EnumToIntegerConverter<T extends ICommonEnum> implements AttributeConverter<T, Integer> {
    private Class<T> enumType;

    public EnumToIntegerConverter() {
        this.enumType = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments())[0];
    }

    @Override
    public Integer convertToDatabaseColumn(T attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public T convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return EnumHelper.getEnumType(enumType, dbData);
    }

    /**
     * 认证状态
     */
    @Converter
    public static class UserAuthorizedStatusEnumsConverter extends EnumToIntegerConverter<UserAuthorizedStatusEnums>
            implements AttributeConverter<UserAuthorizedStatusEnums, Integer> {
    }

}
