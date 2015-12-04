package org.fao.fenix.export.plugins.input.fmd.dataModel.utils;

public enum FMDBlackListFields {
    ask1,
    ask22,
    ask23,
    ask26,
    ask27_1c,
    ask27_2c,
    ask27_3c,
    ask30,
    ask31,
    ask32,
    ask33,
    ask34,
    ask35,
    ask35_2b,
    ask3,
    ask6,
    ask9,
    ask12,
    ask14,
    ask17a,
    ask17b,
    ask18,
    yesno_sectors,
    yesno_movements,
    ask44_1;




    public static boolean isAForbiddenField(String beanName) {

        for (FMDBlackListFields c : FMDBlackListFields.values()) {
            if (c.name().equals(beanName)) {
                return true;
            }
        }

        return false;
    }

}


