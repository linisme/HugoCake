package net.idik.crepecake.hugo.compiler;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Created by linshuaibin on 2018/1/5.
 */

public class DebugLogSpec {
    private final List<? extends VariableElement> parameters;
    private final TypeMirror returnType;
    private final Set<Modifier> modifiers;
    private String className;
    private String methodName;

    public DebugLogSpec(ExecutableElement element) {
        methodName = element.getSimpleName().toString();
        parameters = element.getParameters();
        className = element.getEnclosingElement().asType().toString();
        returnType = element.getReturnType();
        modifiers = element.getModifiers();
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getId() {
        return Utils.MD5("HugoCake_DebugLog_" + className + "_" + methodName + parameters.size());
    }

    public TypeMirror getReturnType() {
        return returnType;
    }

    public List<? extends VariableElement> getParameters() {
        return parameters;
    }

    public Set<Modifier> getModifiers() {
        return modifiers;
    }
}
