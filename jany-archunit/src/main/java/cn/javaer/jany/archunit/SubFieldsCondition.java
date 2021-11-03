package cn.javaer.jany.archunit;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 检测类的字段是否是另一个类字段的子集.
 *
 * @author cn-src
 */
public class SubFieldsCondition extends ArchCondition<JavaClass> {

    public static final String TRANSIENT = "org.springframework.data.annotation.Transient";
    private Iterable<JavaClass> allObjectsToTest;

    public SubFieldsCondition(final String description, final Object... args) {
        super(description, args);
    }

    @Override
    public void init(final Iterable<JavaClass> allObjectsToTest) {
        this.allObjectsToTest = allObjectsToTest;
    }

    @Override
    public void check(final JavaClass projectionClass, final ConditionEvents events) {
        if (!projectionClass.isAnnotatedWith(SubFields.class)) {
            return;
        }
        final String entityClassName = projectionClass.getAnnotationOfType(SubFields.class)
            .value().getName();

        final Set<EqualField> projectionFields = this.getFields(projectionClass);

        boolean isOk = false;
        for (final JavaClass entityClass : this.allObjectsToTest) {
            if (entityClass.getName().equals(entityClassName)) {
                final Set<EqualField> entityFields = this.getFields(entityClass);
                if (entityFields.containsAll(projectionFields)) {
                    isOk = true;
                    break;
                }
            }
        }
        if (!isOk) {
            final String message = String.format(
                "The fields of class %s are not a subset of the fields of class %s, " +
                    "Please check the field name and type.",
                projectionClass.getName(), entityClassName);
            events.add(SimpleConditionEvent.violated(projectionClass, message));
        }
    }

    @NotNull
    private Set<EqualField> getFields(final JavaClass projectionClass) {
        return projectionClass.getAllFields().stream()
            .filter(it -> !it.getModifiers().contains(JavaModifier.STATIC))
            .filter(it -> !it.isAnnotatedWith(TRANSIENT))
            .map(it -> new EqualField(it.getName(), it.getRawType().getName()))
            .collect(Collectors.toSet());
    }
}