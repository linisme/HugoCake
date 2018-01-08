package net.idik.crepecake.hugo.compiler;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;

/**
 * Created by linshuaibin on 2018/1/5.
 */

public class DebugLogCodeGenerator extends CodeGenerator<Set<DebugLogSpec>> {

    private static final String PACKAGE_NAME = "net.idik.crepecake.hugo.aspects";

    DebugLogCodeGenerator(Filer filer, Messager messager) {
        super(filer, messager);
    }

    @Override
    public void generate(Set<DebugLogSpec> specs) {
        specs.forEach(this::generate);
    }

    private void generate(DebugLogSpec spec) {

        TypeSpec.Builder debugAspectClass = TypeSpec.classBuilder("HugoCake_DebugLog_" + spec.getId())
                .addModifiers(Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(ClassName.get("net.idik.crepecake.annotation", "Aspect"))
                        .addMember("value", spec.getClassName() + ".class")
                        .build());


        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(spec.getMethodName()).addModifiers(spec.getModifiers())
                .returns(TypeName.get(spec.getReturnType()))
                .addParameter(ClassName.get("net.idik.crepecake.api", "InvocationHandler"), "invocationHandler");

        StringBuilder argsBuilder = new StringBuilder();

        StringBuilder argSignatureBuilder = new StringBuilder();
        StringBuilder argValuesBuilder = new StringBuilder();

        spec.getParameters().forEach(param -> {
            String name = param.getSimpleName().toString();
            methodBuilder.addParameter(TypeName.get(param.asType()), name);
            argsBuilder.append(name).append(", ");
            argSignatureBuilder.append(name).append("=\\\"%s\\\"").append(", ");
            argValuesBuilder.append(", ").append(name);
        });
        if (argSignatureBuilder.length() > 0) {
            argSignatureBuilder.delete(argSignatureBuilder.length() - 2, argSignatureBuilder.length());
        }

        String args = "";
        if (argsBuilder.length() > 0) {
            args = argsBuilder.substring(0, argsBuilder.length() - 2);
        }

        methodBuilder.addStatement("System.out.println(String.format(\"⇢ $L($L)\"$L))", spec.getMethodName(), argSignatureBuilder.toString(), argValuesBuilder.toString());

        methodBuilder.addStatement("long __time1 = System.currentTimeMillis()");
        if (TypeName.VOID == TypeName.get(spec.getReturnType())) {
            methodBuilder.addStatement("invocationHandler.invoke($L)", args)
                    .addStatement("System.out.println(String.format(\"⇠ $L [%dms]\", System.currentTimeMillis() - __time1))", spec.getMethodName());
        } else {
            methodBuilder.addStatement("$L _____r = ($L)invocationHandler.invoke($L)", spec.getReturnType().toString(), spec.getReturnType().toString(), args)
                    .addStatement("System.out.println(String.format(\"⇠ $L [%dms] = \\\"%s\\\"\", System.currentTimeMillis() - __time1, _____r))", spec.getMethodName())
                    .addStatement("return _____r");
        }

        debugAspectClass.addMethod(methodBuilder.build());

        JavaFile classFile = JavaFile.builder(PACKAGE_NAME, debugAspectClass.build()).build();

        try {
            classFile.writeTo(getFiler());
        } catch (FilerException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
