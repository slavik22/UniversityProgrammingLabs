package org.example;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassDescriptionExtractor {
    public static void main(String[] args) {
        try {
            String className = "customObject.CustomObject";

            Class<?> clazz = loadClass(className);

            displayClassDescription(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Class<?> loadClass(String className) throws ClassNotFoundException {
        ClassLoader classLoader = ClassDescriptionExtractor.class.getClassLoader();
        return classLoader.loadClass(className);
    }

    private static void displayClassDescription(Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        System.out.println(Modifier.toString(modifiers) + " class " + clazz.getSimpleName() + " {");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int fieldModifiers = field.getModifiers();
            System.out.println("  " + Modifier.toString(fieldModifiers) + " " + field.getType().getSimpleName() + " " + field.getName() + ";");
        }

        System.out.println("}");
    }
}
