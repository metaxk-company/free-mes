package io.metaxk.module.mes.controller.admin.auto;

public enum  CycleMethodMnum {

    CYCLE_METHOD_YEAR("YEAR","按年"),
    CYCLE_METHOD_MONTH("MONTH","按月"),
    CYCLE_METHOD_DAY("DAY","按日"),
    CYCLE_METHOD_HOUR("HOUR","按小时"),
    CYCLE_METHOD_MINUTE("MINUTE","按分钟"),
    CYCLE_METHOD_OTHER("OTHER","其他");

    private String code;
    private String name;

    CycleMethodMnum(String code,String name){
        this.code=code;
        this.name=name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static CycleMethodMnum getByCode(String code){
        for(CycleMethodMnum value : CycleMethodMnum.values()){
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return CYCLE_METHOD_OTHER;
    }
}
