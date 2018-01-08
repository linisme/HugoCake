package net.idik.crepecake.hugo.compiler;

import com.google.auto.service.AutoService;

import net.idik.crepecake.hugo.annotations.DebugLog;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

@AutoService(Processor.class)
public class HugoProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Messager messager;
    private Filer filer;
    private Elements elementUtils;

    private DebugLogParser debugLogParser;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        elementUtils = processingEnvironment.getElementUtils();
        debugLogParser = new DebugLogParser(typeUtils, elementUtils, messager);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(DebugLog.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<DebugLogSpec> datas = new HashSet<>();

        Set<? extends Element> elements = roundEnvironment.getRootElements();
        findTargets(elements, datas);

        new DebugLogCodeGenerator(filer, messager).generate(datas);

        return true;
    }

    private void findTargets(Collection<? extends Element> elements, Collection<DebugLogSpec> datas) {
        for (Element element : elements) {
            if (element.getKind() != ElementKind.PACKAGE && element.getKind() != ElementKind.CLASS && element.getKind() != ElementKind.METHOD) {
                continue;
            }
            if (element.getAnnotation(DebugLog.class) != null) {
                if (!Validator.valid(element, DebugLog.class, messager)) {
                    continue;
                }

                DebugLogSpec spec = debugLogParser.parse((ExecutableElement) element);

                if (spec != null) {
                    datas.add(spec);
                }
            }
            Collection<? extends Element> enclosedElements = element.getEnclosedElements();
            if (enclosedElements != null && enclosedElements.size() > 0) {
                findTargets(enclosedElements, datas);
            }
        }
    }
}
