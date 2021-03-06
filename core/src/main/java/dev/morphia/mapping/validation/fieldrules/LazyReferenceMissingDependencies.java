package dev.morphia.mapping.validation.fieldrules;

import dev.morphia.annotations.Reference;
import dev.morphia.mapping.Mapper;
import dev.morphia.mapping.codec.pojo.EntityModel;
import dev.morphia.mapping.codec.pojo.FieldModel;
import dev.morphia.mapping.lazy.LazyFeatureDependencies;
import dev.morphia.mapping.validation.ConstraintViolation;
import dev.morphia.mapping.validation.ConstraintViolation.Level;

import java.util.Set;

/**
 * Checks that proxy deps are present if lazy references are used.
 */
public class LazyReferenceMissingDependencies extends FieldConstraint {

    @Override
    protected void check(Mapper mapper, EntityModel entityModel, FieldModel mf, Set<ConstraintViolation> ve) {
        final Reference ref = mf.getAnnotation(Reference.class);
        if (ref != null) {
            if (ref.lazy()) {
                if (!LazyFeatureDependencies.assertProxyClassesPresent()) {
                    ve.add(new ConstraintViolation(Level.SEVERE, entityModel, mf, getClass(),
                        "Lazy references need ByteBuddy on the classpath."));
                }
            }
        }
    }

}
