import{d as e,e as a,f as t,g as o,C as r,o as s,c as n,k as i,w as l,a as d,j as c,F as u,n as p,G as m,X as g,t as k,J as w,z as f,I as v,aK as C,aR as y,ce as h,H as _,Z as N,cy as b,bN as S,a0 as j,aq as x,_ as D}from"./index-4bfb878d.js";import{c as P}from"./index-6c8a85ac.js";import{c as I}from"./index-529a6cde.js";import M from"./orderDialog-63bb76f2.js";import{e as U,f as z,t as q,g as V}from"./data-6c43379c.js";import{f as F,h as O,i as E,j as G,k as R,l as T}from"./pmOrder-563fbf59.js";import H from"./printContent-4bae174b.js";import{S as J,A as K,d as A,e as L,f as Q,g as X,h as Z}from"./const-f443e30b.js";import"./index-6ebcd94a.js";import"./treeMaterial-9b585804.js";import"./materialData-d3bde0f1.js";import"./index-b537b466.js";import"./index-e08c5726.js";const B={style:{"margin-bottom":"20px"}},W={id:"printMe",style:{height:"400px"}},Y=D(e({__name:"index",setup(e){const D=a([]),Y=t({id:"printMe"});let $=a(!1);const ee=t({workorderCode:"",workorderName:"",productCode:"",productName:"",clientName:"",requestDate:""});let ae=a([]),te=a(!1);const oe=t({total:0,pageSize:10,pageSizes:[5,10,15,20],currentPage:1});let re=t({total:0,pageSize:10,pageSizes:[5,10,15,20],currentPage:1});const se=a(!1),ne=t({visible:!1,tips:"新增数据",state:"view",ruleForm:{workorderCode:"",autoGenFlag:!1,workorderName:"",orderSource:"STORE",productCode:"",productName:"",productSpc:"",unitOfMeasure:"",quantity:1,requestDate:"",batchCode:"",clientCode:"",clientName:"",remark:"",id:null,orderDate:"",produceDate:"",parentId:null}}),ie=a([]),le=a(),de=a(),ce=e=>{switch(e){case Z:return"success";case X:return"info";case Q:case L:return"success"}},ue=e=>e<30?"#909399":e<70?"#e6a23c":"#67c23a",pe=()=>{const e={workorderCode:le.value,pageNo:re.currentPage};return de.value=!0,new Promise(((a,t)=>{R(e).then((e=>{ie.value=e.list,re.total=e.total,a(e)})).finally((()=>{de.value=!1}))}))},me=e=>{re=e.value,pe()},ge=e=>{ne.tips="查看生产订单",ne.state="view",Ne(null==e?void 0:e.id)},ke=e=>{oe.currentPage=e.value.currentPage,be()},we=()=>{be()};let fe=t([]);const ve=()=>{be()},Ce=e=>{switch(e){case"save":_e();break;case"download":ye();break;case"printer":z[2].loading=!0,T(fe[0]).then((e=>{D.value=e.map((e=>{const{processCode:a,processName:t,remark:o,processUrl:r}=e;return{taskUrl:e.taskUrl,printDescribe:{taskCode:e.taskCode,itemCode:e.itemCode,measureName:e.measureName,workorderCode:e.workorderCode,itemName:e.itemName,quantity:parseInt(e.quantity)},printTable:[{processCode:a,processName:t,remark:o,processUrl:r}]}})),$.value=!0})).finally((()=>{z[2].loading=!1}))}},ye=async()=>{const e=await O();h.excel(e,"生产订单.xls")},he=async e=>{fe=e.map((e=>e.workorderCode)),z[2].disabled=!fe.length||fe.length>=2},_e=()=>{ne.tips="添加生产订单",ne.state="edit",ne.visible=!0},Ne=e=>{E(e).then((e=>{ne.ruleForm=e,ne.visible=!0}))},be=()=>{te.value=!0;const e={pageNo:oe.currentPage,pageSize:oe.pageSize,...ee};G(e).then((e=>{const{list:a=[],total:t}=e||{};ae.value=a,oe.total=t})).finally((()=>{te.value=!1}))};return o((()=>{be()})),(e,a)=>{const t=_,o=N,h=b,O=S,E=j,G=x,R=r("print");return s(),n(u,null,[i(t,null,{default:l((()=>[i(I,{"conditions-list":d(U),"search-model":ee,onQueryData:ve},null,8,["conditions-list","search-model"])])),_:1}),i(t,null,{default:l((()=>[c("div",B,[(s(!0),n(u,null,p(d(z),((e,a)=>(s(),m(o,{plain:"",type:e.type,key:a,icon:e.icon,disabled:e.disabled,loading:e.loading,onClick:a=>Ce(e.state)},{default:l((()=>[g(k(e.label),1)])),_:2},1032,["type","icon","disabled","loading","onClick"])))),128))]),i(P,{loading:d(te),isSelection:!0,columns:d(q),tableData:d(ae),pagination:oe,onSelectionChange:he,onPaginationChange:ke},{workorderCode:l((({scope:e})=>[i(o,{link:"",type:"primary",onClick:a=>ge(e.row)},{default:l((()=>[g(k(e.row.workorderCode),1)])),_:2},1032,["onClick"])])),productionSchedule:l((({scope:e})=>[i(h,{color:ue,"text-inside":!0,"stroke-width":26,percentage:isNaN(parseInt(e.row.productionSchedule))?0:parseInt(e.row.productionSchedule)},null,8,["percentage"])])),orderSource:l((({scope:e})=>[g(k(d(J)[e.row.orderSource]),1)])),status:l((({scope:e})=>[g(k(d(K)[e.row.status]),1)])),operation:l((({scope:e})=>["CONFIRMED"==e.row.status?(s(),m(o,{key:0,link:"",type:"primary",onClick:a=>ge(e.row)},{default:l((()=>[g("详情")])),_:2},1032,["onClick"])):w("",!0),i(o,{link:"",type:"primary",onClick:a=>{return t=e.row,le.value=t.workorderCode,void pe().then((e=>{se.value=!0}));var t}},{default:l((()=>[g("查看任务")])),_:2},1032,["onClick"]),i(o,{link:"",type:"primary",onClick:a=>(async e=>{ne.tips="修改生产订单",ne.state="edit",await Ne(null==e?void 0:e.id)})(e.row)},{default:l((()=>[g("修改")])),_:2},1032,["onClick"]),i(o,{link:"",type:"danger",onClick:a=>{return t=e.row,void C.confirm("是否删除当前订单?").then((()=>{F([null==t?void 0:t.id]).then((e=>{e&&(y.success("删除成功"),be())}))}));var t}},{default:l((()=>[g("删除")])),_:2},1032,["onClick"])])),_:1},8,["loading","columns","tableData","pagination"])])),_:1}),i(M,{"pm-order-form":ne,onUpDateTableList:we},null,8,["pm-order-form"]),i(E,{modelValue:d($),"onUpdate:modelValue":a[1]||(a[1]=e=>f($)?$.value=e:$=e),width:"60%",title:"打印任务单"},{footer:l((()=>[i(O),i(o,{onClick:a[0]||(a[0]=e=>f($)?$.value=!1:$=!1)},{default:l((()=>[g("关闭")])),_:1})])),default:l((()=>[v((s(),m(o,{type:"primary",icon:"printer"},{default:l((()=>[g("打印")])),_:1})),[[R,Y]]),i(O),c("div",W,[i(H,{"print-data-info":D.value},null,8,["print-data-info"])])])),_:1},8,["modelValue"]),(s(),m(E,{modelValue:se.value,"onUpdate:modelValue":a[2]||(a[2]=e=>se.value=e),width:"60%",title:"生产任务详情",key:Math.random()},{default:l((()=>[i(P,{loading:de.value,columns:d(V),tableData:ie.value,pagination:d(re),onPaginationChange:me},{status:l((({scope:e})=>[i(G,{type:ce(e.row.status)},{default:l((()=>[g(k(d(A)[e.row.status]),1)])),_:2},1032,["type"])])),reportingProgress:l((({scope:e})=>[i(h,{color:ue,"text-inside":!0,"stroke-width":26,percentage:isNaN(parseInt(e.row.reportingProgress))?0:parseInt(e.row.reportingProgress)},null,8,["percentage"])])),_:1},8,["loading","columns","tableData","pagination"])])),_:1},8,["modelValue"]))],64)}}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/prodMgmt/pmOrder/index.vue"]]);export{Y as default};