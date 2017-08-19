/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package consolidation;

import consolidation.forms.model.DataSetModel;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.Modifier;

/**
 *
 * @author Димитрий
 */
public class ScriptExecutor {

    private static Class scriptClass;

    private static void initScriptClass(String script) {

        ClassPool classPool = ClassPool.getDefault();
        
        //classPool.importPackage("consolidation.forms.model");
        classPool.importPackage("consolidation");

        CtClass newClass = classPool.makeClass("consolidation.script");

        try {
            newClass.setModifiers(Modifier.PUBLIC);
            CtConstructor constructor;
            try {
                constructor = CtNewConstructor.make("public script(){}", newClass);
                newClass.addConstructor(constructor);
            } catch (CannotCompileException ex) {
                Logger.getLogger(ScriptExecutor.class.getName()).log(Level.SEVERE, null, ex);
            }
            CtMethod method = CtNewMethod.make(script, newClass);
            newClass.addMethod(method);
            scriptClass = newClass.toClass();
        } catch (CannotCompileException ex) {
            Logger.getLogger(ScriptExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public static void executeScript(DataSetModel model, int row, String script) {

        if (scriptClass == null) {
            initScriptClass(script);
        }
        try {
            Method[] ms = scriptClass.getMethods();
            Method m = ms[0];
            Object ob = scriptClass.newInstance();

            DataManagerImpl dm = new DataManagerImpl(model);
            dm.setCurrentRow(row);

            m.invoke(ob, dm);
        } catch (InstantiationException ex) {
            Logger.getLogger(ScriptExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(ScriptExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(ScriptExecutor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(ScriptExecutor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
