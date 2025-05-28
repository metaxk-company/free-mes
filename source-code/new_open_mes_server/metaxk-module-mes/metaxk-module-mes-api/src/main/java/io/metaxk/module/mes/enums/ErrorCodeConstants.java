package io.metaxk.module.mes.enums;

import io.metaxk.framework.common.exception.ErrorCode;


/**
 * 错误码枚举类
 */
public interface ErrorCodeConstants {


    // ========== 主数据 错误码从500开始 ==========
    ErrorCode ITEM_TYPE_NOT_EXISTS = new ErrorCode(100800704, "产品类型不存在");
    ErrorCode PRODUCT_BOM_NOT_EXISTS = new ErrorCode(100800707, "产品BOM组成不存在");
    ErrorCode WORKSTATION_MACHINE_NOT_EXISTS = new ErrorCode(100800709, "工作站设备资源不存在");
    //============客户管理============
    ErrorCode WORKSTATION_PROTASK_NOT_EXISTS = new ErrorCode(100800706, "当前生产任务对应工作站不存在");


    ErrorCode ITEM_TYPE_CODE = new ErrorCode(500, "物料产品分类编码已存在");
    ErrorCode ITEM_TYPE_NAME = new ErrorCode(501, "物料产品分类名称已存在");
    ErrorCode ITEM_TYPE_IS_CHILD = new ErrorCode(502, "分类下有子分类，请先删除子分类");
    ErrorCode ITEM_TYPE_IS_ITEM = new ErrorCode(503, "分类下有物料，请先删除物料");
    ErrorCode ITEM_CODE = new ErrorCode(504, "物料产品编码已存在");
    ErrorCode ITEM_NAME = new ErrorCode(505, "物料产品名称已存在");
    ErrorCode EXPORT_DATA_ERROR = new ErrorCode(506, "数据导出失败");
    ErrorCode WORKSTATION_CODE_EXIST = new ErrorCode(509, "工作站编号已存在");
    ErrorCode WORKSTATION_NAME_EXIST = new ErrorCode(510, "工作站名称已存在");

    ErrorCode CLIENT_CODE_EXIST = new ErrorCode(512, "客户编码已存在");
    ErrorCode CLIENT_NAME_EXIST = new ErrorCode(513, "客户名称已存在");

    ErrorCode VENDOR_CODE_EXIST = new ErrorCode(515, "供应商编码已存在");
    ErrorCode VENDOR_NAME_EXIST = new ErrorCode(516, "供应商名称已存在");
    ErrorCode SHOP_CODE_EXIST = new ErrorCode(518, "车间编码已存在");
    ErrorCode SHOP_NAME_EXIST = new ErrorCode(519, "车间名称已存在");
    ErrorCode PROCESS_CODE_EXIST = new ErrorCode(520, "工序编码已存在");
    ErrorCode PROCESS_NAME_EXIST = new ErrorCode(521, "工序名称已存在");
    ErrorCode ROUTE_CODE_EXIST = new ErrorCode(522, "工艺编号已存在");

    ErrorCode ROUTE_PROCESS_CODE_EXIST = new ErrorCode(523, "序号已存在");
    ErrorCode NO_INSERTS = new ErrorCode(524, "不能重复添加工序");
    ErrorCode ALERY_SPECIFIED = new ErrorCode(525, "当前工艺路线已经指定过关键工序");
    ErrorCode ROUTE_PRODUCT_ALERY_EXIST = new ErrorCode(526, "此产品已配置了工艺路线");
    ErrorCode WORKORDER_CODE_EXIST = new ErrorCode(527, "订单编号已存在");
    ErrorCode ITEM_CODE_ERROR = new ErrorCode(528, "产品编号错误，请选择物料产品中的编码！");
    ErrorCode NO_ROUTER = new ErrorCode(529, "当前产品未配置工艺路线");
    ErrorCode NO_TASKCODE = new ErrorCode(530, "任务单号不存在");
    ErrorCode TASK_ALERY_EXIST = new ErrorCode(531, "当前任务已经添加,请勿重复提交！");


    ErrorCode WORKHOURS_TYPE = new ErrorCode(537, "工时类型已经存在");
    ErrorCode CREATETIMEANDENDTIME_NOT_NULL = new ErrorCode(538, "开始时间与结束时间必须同时输入");
    ErrorCode MACHINEWORKSTATION = new ErrorCode(539, "设备已分配至工作站");
    ErrorCode MACHINERYTYPEHASMACHINERT = new ErrorCode(540, "设备类型下关联了设备，请勿删除");
    ErrorCode MACHINERY_STATUS_EXIST = new ErrorCode(542, "设备状态已经存在");
    ErrorCode WORKSHOP_NO_EXIST_DELTED = new ErrorCode(543, "车间不存在或已经被删除");
    ErrorCode QUENTITY_NO_BIG_ORDER = new ErrorCode(544, "排产数量不能大于订单数量");
    ErrorCode  SCHEDULING_QUENTITY_TOO_MUCH = new ErrorCode(545, "排产数量已达订单总数");
    ErrorCode  ALREADY_BEEN_SCHEDULED = new ErrorCode(547, "当前订单已经排完产请不要继续修改");
    ErrorCode  REPORTWORKQUENTITY_EQUAL_TASKQUENTITY = new ErrorCode(548, "报工数量须于排产数量相同");
    ErrorCode  NOT_SCHEDULED_FOR_PRODUCTION = new ErrorCode(549, "未排产,请先去排产，排产之后才可打印任务单");
    ErrorCode  PROTASK_ALERY_STARTEDED = new ErrorCode(550, "任务已经开工,不能删除");
    ErrorCode  IMPORT_ITEM_OR_PRODUCT = new ErrorCode(551, "物料或产品填写的格式为ITEM或PRODUCT");
    ErrorCode  START_TIME_EXCEED_ORDER_DATE = new ErrorCode(552, "排产任务开始时间需要大于订单日期");
    ErrorCode  CAPACITY_NOT_EXCEED_ORDER_QUANTITY = new ErrorCode(553, "排产数量不能超过设备产能");
    ErrorCode  EXIST_TASK = new ErrorCode(554, "任务已经排满");
    ErrorCode  PROCESS_TYPE_SCHEDULE_TASK = new ErrorCode(555, "当前订单已选择按逐个工序排产");
    ErrorCode  ORDER_TYPE_SCHEDULE_TASK = new ErrorCode(556, "当前订单已选择按工单数量排产");
    ErrorCode  ORDER_TYPE_SCHEDULE_TASK_FILL_UP = new ErrorCode(557, "订单已按工单数量排产完成");
    ErrorCode  CAL_TEAM_TYPE_NAME_EXIST = new ErrorCode(558, "班组类型名称已经存在");

    ErrorCode  CAL_TEAM__CODE_EXIST = new ErrorCode(558, "班组编号已经存在");
    ErrorCode  CAL_TEAM__NAME_EXIST = new ErrorCode(559, "班组名称已经存在");
    ErrorCode  PERSON_JOIN_TEAM = new ErrorCode(562, "班组已经添加排班计划");
    ErrorCode  IMPORT_DATA_ERROR = new ErrorCode(563, "数据导入失败");
    ErrorCode  IMPORT_DATA_WORKSHOP_NAME_ERROR = new ErrorCode(564, "请检查导入数据中的车间名称是否存在");
    ErrorCode  IMPORT_DATA_DEPT_NAME_ERROR = new ErrorCode(566, "请检查导入数据中的部门名称是否存在");
    ErrorCode  ONE_CLASSIFY_OR_TWO_CLASSIFY = new ErrorCode(567, "分类的名称必须是一级分类或者二级分类");
    ErrorCode  INSPECT_WAY_NAME_EXIST = new ErrorCode(568, "检验名称已经存在");
    ErrorCode  DEVICE_NAME_EXIST = new ErrorCode(569, "器具名称已经存在");
    ErrorCode  CLASSIFY_NAME_EXIST = new ErrorCode(570, "检测分类名称已经存在");
    ErrorCode  PROJECT_NAME_EXIST = new ErrorCode(571, "检测项名称已经存在");
    ErrorCode  PROCESS_STANDARD_ENABLED = new ErrorCode(572, "当前工序检验标准已经启用");
    ErrorCode  PAN_HAO_EXIST = new ErrorCode(573, "盘号已经存在");
    ErrorCode  COLOR_EXIST = new ErrorCode(574, "颜色名已经存在");
    ErrorCode  MODEL_EXIST = new ErrorCode(575, "型号已经存在");
    ErrorCode  SPEC_EXIST = new ErrorCode(576, "规格名已经存在");
    ErrorCode  WAREHOUSE_EXIST = new ErrorCode(577, "仓库已存在");
    ErrorCode  AREA_EXIST = new ErrorCode(578, "库区已存在");
    ErrorCode  LOCATION_EXIST = new ErrorCode(579, "库位已存在");
    ErrorCode  RECEIVE_STANDARD_ENABLED = new ErrorCode(573, "当前来料检验标准已经启用");
    ErrorCode  MODEL_STANDARD_ENABLED = new ErrorCode(573, "当前型号检验标准已经启用");
    ErrorCode  SPC_STANDARD_ENABLED = new ErrorCode(573, "当前规格检验标准已经启用");
    ErrorCode  LINE_TYPE_STANDARD_ENABLED = new ErrorCode(573, "当前线别检验标准已经启用");
    ErrorCode  NO_JOIN_DB = new ErrorCode(574, "数据暂未添加进数据库");


    // ========== 计划管理 ==========
    ErrorCode  PLAN_MONTH_NUMBER_EXIST = new ErrorCode(900, "月计划编号已存在或被废弃");
    ErrorCode  PLAN_DAY_NUMBER_EXIST = new ErrorCode(901, "日计划编号已存在或被废弃");
    ErrorCode  PLAN_MONTH_NUMBER_NOT_EXIST = new ErrorCode(902, "没有找到该月计划编号");
    ErrorCode  PLAN_MONTH_ID_NOT_EXIST = new ErrorCode(903, "没有找到月计划id");
    ErrorCode  PLAN_DAY_ID_NOT_EXIST = new ErrorCode(904, "没有找到日计划id");
    ErrorCode  SHIFT_ATTENDANCE_NUMBER_EXIST = new ErrorCode(905, "考勤编号已存在或被废弃");
    ErrorCode  SHIFT_ATTENDANCE_ID_NOT_EXIST = new ErrorCode(906, "没有找到考勤id");
    ErrorCode  PLAN_MONTH_MONTH_FORMAT_NOT_MATCHED = new ErrorCode(907, "月计划的日期格式不正确");
    ErrorCode  PLAN_DAY_DAY_FORMAT_NOT_MATCHED = new ErrorCode(908, "日计划的日格式不正确");
    ErrorCode  SHIFT_ATTENDANCE_DATE_FORMAT_NOT_MATCHED = new ErrorCode(909, "考勤的日期格式不正确");
    ErrorCode  SHIFT_ATTENDANCE_UPDATED_NEED_ACTUAL_AND_REQUIRED = new ErrorCode(910, "需要同时传入实到和应到人数或都不传");
    ErrorCode  PLAN_BATCH_NEED_ID = new ErrorCode(911, "删除时至少需要1个id");
    ErrorCode  PLAN_PAGE_RESULT_NEED_PAGE_INFO = new ErrorCode(912, "分页查询时必须提供pageNo和pageSize");
    ErrorCode  SHIFT_ATTENDANCE_REQUIRED_GREATER_OR_EQUAL_ACTUAL = new ErrorCode(913, "应到人数必须大于等于实到人数");
    ErrorCode  SHIFT_ATTENDANCE_REQUIRED_IS_ZERO = new ErrorCode(914, "应到人数不可以为0");
    ErrorCode  PLAN_DAY_MONTH_NUMBER_NOT_MATCH = new ErrorCode(915, "删除列表中所有的PlanDay对应的PlanMonth必须是一样的");
    ErrorCode  PLAN_DAY_QTY_NOT_NEGATIVE = new ErrorCode(916, "日计划的计划数量不能为负");
    ErrorCode  SHIFT_ATTENDANCE_REQUIRED_NOT_NEGATIVE = new ErrorCode(917, "考勤应到人数不能为负");
    ErrorCode  SHIFT_ATTENDANCE_ACTUAL_NOT_NEGATIVE = new ErrorCode(918, "考勤实到人数不能为负");
    ErrorCode  OUTBOUND_DETAIL_FINISHED = new ErrorCode(921, "当前出库明细已完成");
    ErrorCode  CUSTOMER_NAME_IS_THE_SAME = new ErrorCode(922, "出库单中客户名不一致，请选择相同客户名");
    ErrorCode  QUOTE_ALERADY_EXIST = new ErrorCode(924, "客户报价单已存在");
    ErrorCode  ISSUE_TYPE_NOT_EXIST = new ErrorCode(925, "问题类型不存在");
    ErrorCode  ISSUE_SOURCE_NOT_EXIST = new ErrorCode(926, "问题来源不存在");
    ErrorCode  ISSUE_COMPONENT_NOT_EXIST = new ErrorCode(927, "问题零部件不存在");
    ErrorCode  ISSUE_MODE_NOT_EXIST = new ErrorCode(928, "问题模式不存在");
    ErrorCode  LABEL_QR_CODE_NOT_EXIST = new ErrorCode(929, "没有找到二维码编号，退货失败");
    ErrorCode  OUTBOUND_QUANTITY_NOT_LEGAL = new ErrorCode(930, "出库数量不能超过客户设定的出库上限");


}
