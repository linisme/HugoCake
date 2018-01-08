package net.idik.crepecake.hugo.compiler;

import net.idik.crepecake.hugo.annotations.DebugLog;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

/**
 * Created by linshuaibin on 2018/1/5.
 */

class Validator {
    static boolean valid(Element element, Class<DebugLog> debugLogClass, Messager messager) {
        return element.getKind() == ElementKind.METHOD;
    }
}
