package org.example.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskStatus attribute) {
        return attribute.getValue();
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer dbData) {
        return Stream.of(TaskStatus.values())
                .filter(s -> s.getValue() == dbData)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
