import{cz as e,cA as a,d as l,e as t,f as o,aS as d,g as r,o as u,c as n,k as s,w as i,F as m,n as p,a as c,G as v,X as b,t as f,J as g,as as k,j as y,aK as _,aR as w,Z as C,H as h,ab as V,E as T,ac as S,ad as I,v as N,aa as q,at as U,au as j,aL as D,cb as x,ae as Y,a0 as M,_ as z}from"./index-4bfb878d.js";import{c as H}from"./index-6c8a85ac.js";import{b as O,t as P,w as F,d as Q,u as $,a as A,g as R,q as B}from"./workStationSelect-30f7fdab.js";import{W as E,i as Z}from"./const-f443e30b.js";import{g as G}from"./shiftSet-d38e6bbc.js";import{g as J}from"./schedulingPlan-bc6ff850.js";const K=e("order-num-store",{state:()=>({orderCount:0}),getters:{},actions:{}}),L=()=>K(a),W={key:0,style:{"margin-bottom":"20px"}},X={class:"dialog-footer"},ee=z(l({__name:"proTask",props:{processId:{type:[Number,String],required:!0},workOrderId:{type:[Number,String],required:!0},isView:{type:Boolean,required:!0},orderNum:{type:[Number,String],required:!0},tabsId:{type:[Number,String],required:!0}},emits:["updateSchedulingTable"],setup(e,{emit:a}){const l=e,z=L(),K=t(null),ee=t(),ae=o({machineryName:[{required:!0,message:"设备名称不能为空",trigger:"blur"}],quantity:[{required:!0,message:"排产数量不能为空",trigger:"blur"}],startTime:[{required:!0,message:"请选择开始生产日期",trigger:"blur"}],duration:[{required:!0,message:"清输入估算的生产用时",trigger:"blur"}],workstationName:[{required:!0,message:"工作站不能为空",trigger:"blur"}],teamCode:[{required:!0,message:"班组编号不能为空",trigger:"blur"}]});let le=o({total:0,pageSize:2,pageSizes:[5,10,15,20],currentPage:1});const te=t(!1);let oe=t(!1);const de=t(!1);let re=o([]),ue=t(""),ne=t([]),se=t({workstationType:E,workstationName:"",machineryCode:"",colorCode:"",quantity:0,startTime:"",duration:1,endTime:"",workstationId:"",workstationCode:"",workorderId:l.workOrderId,processId:l.processId,remainQuantity:""});const ie=t([]),me=new Date;d((()=>se.value.quantity),((e,a)=>{if(e>a){const l=e-a;z.orderCount-=l}else if(e<a){const l=a-e;z.orderCount+=l}}));const pe=t([]),ce=()=>{if(se.value.startTime&&se.value.duration){let e=function(e){return`${e.getFullYear()}-${String(e.getMonth()+1).padStart(2,"0")}-${String(e.getDate()).padStart(2,"0")} ${String(e.getHours()).padStart(2,"0")}:00:00`};const a=se.value.startTime.split(" "),l=new Date(a[0]);l.setHours(a[1].split(":")[0],0,0);const t=l.getTime()/1e3,o=se.value.duration,d=e(new Date(1e3*(t+3600*o)));se.value.endTime=d}},ve=()=>{K.value.visibleClient=!0},be=e=>{se.value.workstationId=e.id,se.value.workstationCode=e.workstationCode,se.value.workstationName=e.workstationName},fe=e=>{},ge=e=>{ne.value=e.map((e=>null==e?void 0:e.id)),O[1].disabled=!ne.value.length||ne.value.length>=2,O[2].disabled=!ne.value.length},ke=()=>{ue.value="添加生产任务",de.value=!0},ye=e=>{ue.value="修改生产任务";const a=(null==e?void 0:e.id)||ne.value[0];Ce(a)},_e=e=>{const l=e||ne.value,t=Array.isArray(l)?l:[l];_.confirm("是否删除当前数据?").then((()=>{Q(t).then((e=>{w.success("删除成功"),Te(),a("updateSchedulingTable")}))}))},we=async()=>{ee.value&&(se.value.remainQuantity=z.orderCount,await ee.value.validate((e=>{e&&(se.value.id?$(se.value).then((e=>{w.success("修改成功"),Ve()})):A(se.value).then((e=>{w.success("新增成功"),Ve()})))})))},Ce=e=>{R(e).then((e=>{se.value=e,de.value=!0}))},he=e=>{le=null==e?void 0:e.value,Te()},Ve=()=>{ee.value&&(ee.value.resetFields(),se.value={workstationType:E,workstationName:"",machineryCode:"",colorCode:"",quantity:0,startTime:"",duration:1,endTime:"",workstationId:"",workstationCode:"",workorderId:l.workOrderId,processId:l.processId,remainQuantity:""},oe.value=!1,de.value=!1,Te(),a("updateSchedulingTable",l.tabsId))},Te=()=>{const e={workorderId:l.workOrderId,processId:l.processId,pageNo:le.currentPage,pageSize:le.pageSize};te.value=!0,B(e).then((e=>{const{countQuantity:a,taskPage:l={}}=e||{};console.log(e,"返回排产总数"),le.total=l.total,re=l.list})).finally((()=>{te.value=!1}))};d((()=>se.value.startTime),(e=>{e||(se.value.duration=1,se.value.endTime="")}));const Se=()=>{Te(),G({}).then((e=>{const{list:a=[]}=e||{};ie.value=a.map((e=>({value:e.teamCode,label:e.teamCode})))}))};return r((()=>{Se()})),(e,a)=>{const l=C,t=h,o=V,d=T,r=S,_=I,w=N,Q=q,$=U,A=j,R=D,B=x,G=Y,L=M;return u(),n(m,null,[s(t,null,{default:i((()=>[e.isView?g("",!0):(u(),n("div",W,[(u(!0),n(m,null,p(c(O).slice(0,3),((e,a)=>(u(),v(l,{plain:"",type:e.type,key:a,icon:e.icon,disabled:e.disabled,onClick:a=>(e=>{switch(e){case"save":ke();break;case"edit":ye();break;case"remove":_e()}})(e.state)},{default:i((()=>[b(f(e.label),1)])),_:2},1032,["type","icon","disabled","onClick"])))),128))])),s(H,{loading:te.value,isSelection:!0,columns:c(P),tableData:c(re),pagination:c(le),onSelectionChange:ge,onPaginationChange:he},k({taskCode:i((({scope:e})=>[s(l,{type:"primary",link:"",onClick:a=>{return l=e.row,ue.value="查看生产任务",se.value=l,de.value=!0,void(oe.value=!0);var l}},{default:i((()=>[b(f(e.row.taskCode),1)])),_:2},1032,["onClick"])])),_:2},[e.isView?void 0:{name:"operation",fn:i((({scope:e})=>[s(l,{link:"",type:"primary",onClick:a=>ye(e.row)},{default:i((()=>[b("修改")])),_:2},1032,["onClick"]),s(l,{link:"",type:"danger",onClick:a=>_e(e.row.id)},{default:i((()=>[b("删除")])),_:2},1032,["onClick"])])),key:"0"}]),1032,["loading","columns","tableData","pagination"])])),_:1}),s(L,{width:"65%",modelValue:de.value,"onUpdate:modelValue":a[11]||(a[11]=e=>de.value=e),title:c(ue),onBeforeClose:Ve},{footer:i((()=>[y("span",X,[c(oe)||e.isView?g("",!0):(u(),v(l,{key:0,type:"primary",onClick:we},{default:i((()=>[b(" 提交 ")])),_:1})),s(l,{onClick:Ve},{default:i((()=>[b("返回")])),_:1})])])),default:i((()=>[s(G,{ref_key:"ruleFormRef",ref:ee,model:c(se),rules:ae,"label-width":"120px"},{default:i((()=>[s(d,null,{default:i((()=>[s(o,{label:"订单未排产数量"},{default:i((()=>[b(f(c(z).orderCount),1)])),_:1})])),_:1}),s(d,null,{default:i((()=>[s(w,{span:7},{default:i((()=>[s(o,{label:"工作站类型"},{default:i((()=>[s(_,{modelValue:c(se).workstationType,"onUpdate:modelValue":a[0]||(a[0]=e=>c(se).workstationType=e),disabled:c(oe)},{default:i((()=>[(u(!0),n(m,null,p(c(Z),((e,a)=>(u(),v(r,{disabled:e.disabled,label:e.label,key:a},{default:i((()=>[b(f(e.value),1)])),_:2},1032,["disabled","label"])))),128))])),_:1},8,["modelValue","disabled"])])),_:1})])),_:1}),s(w,{span:8},{default:i((()=>[c(se).workstationType===c(E)?(u(),v(o,{key:0,label:"工作站",prop:"workstationName"},{default:i((()=>[s(Q,{disabled:c(oe),modelValue:c(se).workstationName,"onUpdate:modelValue":a[1]||(a[1]=e=>c(se).workstationName=e),placeholder:"请选择工作站"},{append:i((()=>[s(l,{icon:"ZoomIn",onClick:ve})])),_:1},8,["disabled","modelValue"])])),_:1})):(u(),v(o,{key:1,label:"设备",prop:"machineryName"},{default:i((()=>[s(A,{onChange:fe,modelValue:c(se).machineryCode,"onUpdate:modelValue":a[2]||(a[2]=e=>c(se).machineryCode=e),placeholder:"请选择设备",clearable:"",filterable:""},{default:i((()=>[(u(!0),n(m,null,p(pe.value,((e,a)=>(u(),v($,{label:e.label,value:e.value,key:a},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1})),s(F,{ref_key:"workSelectRef",ref:K,processId:e.processId,onHandleCurrentSubmit:be},null,8,["processId"])])),_:1}),s(w,{span:7},{default:i((()=>[s(o,{label:"排产数量",prop:"quantity"},{default:i((()=>[s(R,{disabled:c(oe),min:0,max:e.orderNum,modelValue:c(se).quantity,"onUpdate:modelValue":a[3]||(a[3]=e=>c(se).quantity=e),placeholder:"请输入排产数量"},null,8,["disabled","max","modelValue"])])),_:1})])),_:1})])),_:1}),s(d,null,{default:i((()=>[s(w,{span:7},{default:i((()=>[s(o,{label:"开始时间",prop:"startTime"},{default:i((()=>[s(B,{clearable:"",modelValue:c(se).startTime,"onUpdate:modelValue":a[4]||(a[4]=e=>c(se).startTime=e),type:"datetime","value-format":"YYYY-MM-DD HH",format:"YYYY-MM-DD hh:mm:ss",placeholder:"请选择开始生产时间",onChange:ce,"default-time":c(me),disabled:c(oe)},null,8,["modelValue","default-time","disabled"])])),_:1})])),_:1}),s(w,{span:8},{default:i((()=>[s(o,{label:"生产时长",prop:"duration"},{default:i((()=>[s(R,{min:1,precision:0,step:1,onChange:ce,modelValue:c(se).duration,"onUpdate:modelValue":a[5]||(a[5]=e=>c(se).duration=e),placeholder:"请输入生产时长",disabled:c(oe)},null,8,["modelValue","disabled"])])),_:1})])),_:1}),s(w,{span:7},{default:i((()=>[s(o,{label:"完成时间",prop:"endTime"},{default:i((()=>[s(Q,{modelValue:c(se).endTime,"onUpdate:modelValue":a[6]||(a[6]=e=>c(se).endTime=e),disabled:""},null,8,["modelValue"])])),_:1})])),_:1})])),_:1}),s(d,null,{default:i((()=>[s(w,{span:7},{default:i((()=>[s(o,{label:"班组编号",prop:"teamCode"},{default:i((()=>[s(A,{modelValue:c(se).teamCode,"onUpdate:modelValue":a[7]||(a[7]=e=>c(se).teamCode=e),placeholder:"请选择班组编号",onChange:a[8]||(a[8]=e=>{return a=c(se).teamCode,void J(a).then((e=>{se.value.teamType=e.teamType,se.value.teamName=e.teamName}));var a})},{default:i((()=>[(u(!0),n(m,null,p(ie.value,(e=>(u(),v($,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1})])),_:1}),s(w,{span:8},{default:i((()=>[s(o,{label:"班组类型",prop:"teamType"},{default:i((()=>[s(Q,{modelValue:c(se).teamType,"onUpdate:modelValue":a[9]||(a[9]=e=>c(se).teamType=e),disabled:""},null,8,["modelValue"])])),_:1})])),_:1}),s(w,{span:7},{default:i((()=>[s(o,{label:"班组名称",prop:"teamName"},{default:i((()=>[s(Q,{modelValue:c(se).teamName,"onUpdate:modelValue":a[10]||(a[10]=e=>c(se).teamName=e),disabled:""},null,8,["modelValue"])])),_:1})])),_:1})])),_:1})])),_:1},8,["model","rules"])])),_:1},8,["modelValue","title"])],64)}}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/prodMgmt/productionScheduling/components/proTask.vue"]]),ae=Object.freeze(Object.defineProperty({__proto__:null,default:ee},Symbol.toStringTag,{value:"Module"}));export{ae as a,ee as p,L as u};