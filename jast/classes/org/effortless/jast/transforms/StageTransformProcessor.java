package org.effortless.jast.transforms;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;


@SupportedAnnotationTypes("org.effortless.jast.transforms.StageTransform")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class StageTransformProcessor extends AbstractProcessor {

    public StageTransformProcessor() {
        super();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    	for (Element elem : roundEnv.getElementsAnnotatedWith(StageTransform.class)) {
    		StageTransform ann = elem.getAnnotation(StageTransform.class);
            String message = "annotation found in " + elem.getSimpleName()
                           + " with complexity " + ann.value();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
        }
        return true; // no further processing of this annotation type    	
    }
    
}