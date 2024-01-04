package org.togudv.itemcustomeffects.utils;

import java.util.HashSet;
import java.util.Set;

public class TriggerTypes {
    private static final Set<String> effects = new HashSet<>();
    static {
        effects.add("hiteffects");
        effects.add("hurteffects");
        effects.add("holdeffects");
    }


    public static boolean isValidEffectTypeName(String name) {
        name = name.toLowerCase();

        if (effects.contains(name)) {
            return true;
        } else {
            return false;
        }
    }


}
