package com.mambocosmo.urzasoracle.entities;

import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public interface IMappable {
    default void fromMap(Map<String, String> in) {
        for (Method m : this.getClass().getMethods()) {
            String k = null;
            if (m.getName().startsWith("set")) {
                k = m.getName().substring(3);
            }
            if (k != null) {
                try {
                    k = k.substring(0, 1).toLowerCase() + k.substring(1);

                    if (in.containsKey(k) && m.getParameterTypes().length == 1) {
                        // this returns identity if a class is an Object (always) and will return the
                        // boxed type if a class is a primitive utility class such as int.class
                        Class<?> methodType = MethodType.methodType(m.getParameterTypes()[0]).wrap().returnType();
                        // Now we know we are never dealing with primitive types and can assume our
                        // types will implement a version of Class.valueOf(String)
                        Object param;
                        if (methodType == String.class) {
                            param = in.get(k);
                        } else {
                            Method m2 = methodType.getMethod("valueOf", String.class);

                            param = m2.invoke(null, in.get(k));
                        }
                        System.out.println("Invoking method " + m.getName() + " expecting parameter type: "
                                + m.getParameterTypes()[0]);
                        m.invoke(this, param);
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    default void fromMapObj(Map<String, Object> in) {
        Map<String, Object> temp = new HashMap<>(in);
        for (Method m : this.getClass().getMethods()) {
            String k = null;
            Class<?> methodType = null;
            if (m.getName().startsWith("set")) {
                k = m.getName().substring(3);
            }
            if (k != null) {
                try {
                    k = k.substring(0, 1).toLowerCase() + k.substring(1);

                    if (temp.containsKey(k) && m.getParameterTypes().length == 1) {
                        // this returns identity if a class is an Object (always) and will return the
                        // boxed type if a class is a primitive utility class such as int.class
                        methodType = MethodType.methodType(m.getParameterTypes()[0]).wrap().returnType();
                        // Now we know we are never dealing with primitive types and can assume our
                        // types will implement a version of Class.valueOf(String)
                        Object param;
                        // System.out.println(
                        // "Method " + m.getName() + " expects:" + methodType + ", provided param type:"
                        // + in.get(k).getClass());
                        if (methodType.isAssignableFrom(temp.get(k).getClass())) {
                            // System.out.println("Skipping valueof parsing because type is compatible");
                            param = temp.get(k);
                        } else {
                            Method m2 = methodType.getMethod("valueOf", String.class);

                            param = m2.invoke(null, temp.get(k));
                        }
                        // System.out.println("Invoking method " + m.getName() + " expecting parameter
                        // type: "
                        // + m.getParameterTypes()[0]);
                        m.invoke(this, param);

                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException noMethod) {
                    System.err.println(
                            "Trying to map a value of type " + temp.get(k).getClass().getSimpleName()
                                    + " to object " + this.getClass().getSimpleName() + " via setter method \""
                                    + m.getName() + "\" which expects " + methodType.getSimpleName() + ".");

                } finally {
                    temp.remove(k);
                }
            }
        }
        temp.forEach((k, v) -> System.out.println("Failed to map field " + k + " to class "
                + this.getClass().getSimpleName() + ", check if it has been intentionally left out"));
    }

    // metodo che trasforma i nomi e i valori delle proprietà di un oggetto in una
    // mappa
    default Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>();
        for (Method m : this.getClass().getMethods()) {
            if ((m.getName().startsWith("get") || m.getName().startsWith("is")) &&
                    !m.getName().equalsIgnoreCase("getClass") && m.getParameterCount() == 0) {
                int index = m.getName().startsWith("get") ? 3 : 2;
                String nome = m.getName().substring(index);
                nome = Character.toLowerCase(nome.charAt(0)) + nome.substring(1);
                try {
                    String valore = null;
                    if (index == 2) {
                        valore = m.invoke(this).toString().equalsIgnoreCase("true") ? "1" : "0";
                    } else if (index == 3) {
                        valore = String.valueOf(m.invoke(this));
                    }
                    result.put(nome, valore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
