package net.idik.crepecake.hugo.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Created by linshuaibin on 2018/1/5.
 */

public class DebugLogParser {

    private Types types;
    private Elements elements;
    private Messager messager;

    public DebugLogParser(Types types, Elements elements, Messager messager) {
        this.types = types;
        this.elements = elements;
        this.messager = messager;
    }

    public DebugLogSpec parse(ExecutableElement element) {

        return new DebugLogSpec(element);
    }
}
