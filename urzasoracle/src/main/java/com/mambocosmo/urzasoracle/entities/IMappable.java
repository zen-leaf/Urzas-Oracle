package com.mambocosmo.urzasoracle.entities;

import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
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
                        Class<?> methodType = MethodType.methodType(m.getParameterTypes()[0]).wrap().returnType();// what class does the method take
                        Object param = null;
                        
                        if (methodType == String.class) { // if it takes a string just pass the string as is
                            param = in.get(k);
                        } else if (methodType == BigDecimal.class) { // if it takes a BigDecimal first to double then to BD
                            Double paramProxy = Double.valueOf(in.get(k));
                            param = BigDecimal.valueOf(paramProxy);
                        } else if (methodType == Boolean.class) { // if it takes a BigDecimal first to double then to BD 
                            param = Boolean.valueOf(in.get(k).equals("1") ? true : false);
                        } else if (methodType == BigInteger.class) {
                            Long paramProxy = Long.valueOf(in.get(k));
                            param = BigInteger.valueOf(paramProxy);
                        } else if (methodType == Byte[].class) { // if it's a Byte[]
                            String[] inString = in.get(k).replaceAll("\\s","").split(",");
                            Byte[] byteArray = new Byte[inString.length];
                            for (int i=0; i < inString.length; i++){
                                byteArray[i] = Byte.valueOf(inString[i]);
                            }
                            param = byteArray;
                        } else {                           
                            Method m2 = methodType.getMethod("valueOf", String.class); // ie: get Integer.valueOf
                            param = m2.invoke(null, in.get(k)); // use ie Integer.valueOf on the incoming parameter                           
                        }
                        m.invoke(this, param); // use setter on object with newly found correct arg (Integer.valueOf'd)
                    }
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    System.out.println("Invoking method " + m.getName() + " expecting parameter type: "
                    + m.getParameterTypes()[0]);
                    e.printStackTrace();
                }
            }
        }
    }


    //metodo che trasforma i nomi e i valori delle proprietà di un oggetto in una mappa
    default Map<String,String> toMap(){
        Map<String,String> result = new HashMap<>();
        for (Method m : this.getClass().getMethods()) {
            if((m.getName().startsWith("get") || m.getName().startsWith("is")) &&
                !m.getName().equalsIgnoreCase("getClass") && m.getParameterCount() == 0){
                    int index = m.getName().startsWith("get")?3:2;
                    String nome = m.getName().substring(index);
                    nome = Character.toLowerCase(nome.charAt(0)) + nome.substring(1);
                    try {
                        String valore = null;
                        if(index == 2){
                            valore = m.invoke(this).toString().equalsIgnoreCase("true")?"1":"0";
                        }else if(index == 3){
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
