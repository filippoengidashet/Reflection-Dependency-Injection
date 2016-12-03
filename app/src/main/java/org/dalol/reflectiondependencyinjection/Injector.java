/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dalol.reflectiondependencyinjection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Filippo Engidashet <filippo.eng@gmail.com>
 * @version 1.0.0
 * @since 12/3/2016
 */
public class Injector {

    public static void inject(Object target) {
        Class<?> aClass = target.getClass();

        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Solve.class)) {
                Object value = null;
                try {
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    value = type.newInstance();
                    if (value != null) field.set(target, value);
                    field.setAccessible(accessible);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Access not authorized on field '" + field + "' of object '" + target + "' with value: '" + value + "'", e);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void inject(Object target, String instanceOwnerName) {
        Class<?> aClass = target.getClass();

        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Solve.class)) {
                Object value = null;
                try {
                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    Class<?> type = field.getType();
                    value = type.getDeclaredConstructor(String.class).newInstance(instanceOwnerName);
                    if (value != null) field.set(target, value);
                    field.setAccessible(accessible);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Access not authorized on field '" + field + "' of object '" + target + "' with value: '" + value + "'", e);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
