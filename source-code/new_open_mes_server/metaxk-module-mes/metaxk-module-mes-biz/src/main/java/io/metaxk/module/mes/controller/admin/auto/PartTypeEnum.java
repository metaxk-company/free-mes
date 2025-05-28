package io.metaxk.module.mes.controller.admin.auto;

public enum  PartTypeEnum {

    PART_TYPE_INPUTCHAR("INPUTCHAR","传入字符",0),
    PART_TYPE_NOWDATE("NOWDATE","当前日期",1),
    PART_TYPE_FIXCHAR("FIXCHAR","固定字符",2),
    PART_TYPE_SERIALNO("SERIALNO","流水号",3),
    PART_TYPE_OTHER("OTHER","其他",99);

    private final String code;
    private final String name;
    private final Integer beanIndex;

    PartTypeEnum(String code,String name,Integer beanIndex){
        this.code = code;
        this.name = name;
        this.beanIndex = beanIndex;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Integer getBeanIndex() {
        return beanIndex;
    }

    public static PartTypeEnum getByCode(String code){
        for(PartTypeEnum value: PartTypeEnum.values()){
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return PART_TYPE_OTHER;
    }

}
