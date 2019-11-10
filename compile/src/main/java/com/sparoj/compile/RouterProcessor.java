package com.sparoj.compile;

import com.google.auto.service.AutoService;
import com.sparoj.annotation.Action;
import com.sparoj.annotation.Module;
import com.sparoj.annotation.Modules;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * description:
 * Created by sdh on 2019/3/13
 */

@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private String className = "RouterModuleRegister";
    private String entryClassName = "LiteRouterRegister";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            return false;
        }
        Set<? extends Element> actionSet = roundEnv.getElementsAnnotatedWith(Action.class);


        Set<? extends Element> modulesSet = roundEnv.getElementsAnnotatedWith(Modules.class);
        if (actionSet.isEmpty() && modulesSet.isEmpty()) {
            return false;
        }
        Set<? extends Element> moduleSet = roundEnv.getElementsAnnotatedWith(Module.class);
        if (!modulesSet.isEmpty()) {
            Element element = modulesSet.iterator().next();
            Modules modules = element.getAnnotation(Modules.class);
            generateModuleInit(modules.modules());
        } else if (moduleSet.isEmpty()) {
            generateDefaultInit();
        } else if(moduleSet.size()>1) {
            throw new IllegalArgumentException("Only allow one Modules annotation exist");
        }
        if (!moduleSet.isEmpty()) {
            Element element = moduleSet.iterator().next();
            Module module = element.getAnnotation(Module.class);
            className += "_" + module.moduleName();
        }
        generateSingleModuleInit(className, roundEnv);
        return true;
    }

    private void generateSingleModuleInit(String className, RoundEnvironment roundEnv) {
        Set<? extends Element> moduleSet = roundEnv.getElementsAnnotatedWith(Module.class);
        Set<? extends Element> actionSet = roundEnv.getElementsAnnotatedWith(Action.class);
        MethodSpec.Builder singleBuilder = MethodSpec.methodBuilder("register")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addParameter(HashMap.class, "moduleMap")
                .addParameter(HashMap.class, "actionMap");
        ClassName arrayList = ClassName.get("java.util", "ArrayList");
        for (Element element:
             moduleSet) {
            if (element.getKind() == ElementKind.CLASS) {
                ClassName moduleClassName = ClassName.get((TypeElement) element);
                Module module = element.getAnnotation(Module.class);
//                String name = module.moduleName();
                String packageName = module.providePackageName();
                singleBuilder.addStatement("$T $N = new $T()", moduleClassName, moduleClassName.simpleName().toLowerCase(), moduleClassName);
                singleBuilder.addCode("if( moduleMap.get($S) == null ) {\n", packageName);
                singleBuilder.addCode("moduleMap.put($S, new $T()); \n}\n", packageName, arrayList);
                singleBuilder.addCode("(($T)moduleMap.get($S)).add($N);\n", arrayList, packageName, moduleClassName.simpleName().toLowerCase());
            }
        }

        for (Element element:
             actionSet) {
            if (element.getKind() == ElementKind.CLASS) {
                ClassName actionClassName = ClassName.get((TypeElement) element);

                Action action = element.getAnnotation(Action.class);
                String provideModuleName = action.provideModuleName();
                singleBuilder.addStatement("$T $N = new $T()", actionClassName, actionClassName.simpleName().toLowerCase(), actionClassName);
                singleBuilder.addCode("if( actionMap.get($S) == null ) {\n", provideModuleName);
                singleBuilder.addCode("actionMap.put($S, new $T()); \n}\n", provideModuleName, arrayList);
                singleBuilder.addCode("(($T)actionMap.get($S)).add($N);\n", arrayList, provideModuleName, actionClassName.simpleName().toLowerCase());
            }
        }

        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(singleBuilder.build())
                .build();

        try {
            JavaFile.builder("com.sparoj.literouter", typeSpec)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void generateDefaultInit() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("register")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addParameter(HashMap.class, "moduleMap")
                .addParameter(HashMap.class, "actionMap")
                .addStatement("$N.register(moduleMap, actionMap)", className);
        TypeSpec typeSpec = TypeSpec.classBuilder(entryClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build())
                .build();
        try {
            JavaFile.builder("com.sparoj.literouter", typeSpec)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateModuleInit(String[] modules) {
        MethodSpec.Builder registBuilder =
                MethodSpec.methodBuilder("register")
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(TypeName.VOID)
                        .addParameter(HashMap.class, "moduleMap")
                        .addParameter(HashMap.class, "actionMap")
                        .addStatement("$N.register(moduleMap, actionMap)", className);
        for (String moduleName:
             modules) {
            registBuilder.addStatement("$N_"+ moduleName + ".register(moduleMap, actionMap)", className);
        }
        TypeSpec routerRegister = TypeSpec.classBuilder(entryClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(registBuilder.build())
                .build();
        try {
            JavaFile.builder("com.sparoj.literouter", routerRegister)
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Module.class.getCanonicalName());
        set.add(Action.class.getCanonicalName());
        set.add(Modules.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void log(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }
}
