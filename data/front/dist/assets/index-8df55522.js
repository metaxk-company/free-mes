import{f as e,d as a,e as l,g as s,o,c as t,k as r,w as n,a as u,j as i,F as d,n as p,G as c,X as m,t as v,aK as y,aR as f,H as h,Z as b,aa as k,ab as g,v as _,E as w,ae as T,a0 as x,_ as V}from"./index-4bfb878d.js";import{c as C}from"./index-529a6cde.js";import{c as S}from"./index-6c8a85ac.js";import{q as j,d as z,u as D,a as U,g as H}from"./workHoursType-c6ffc484.js";const P=e([{type:"select",label:"工时类型",prop:"workhoursType",placeholder:"选择工时类型",options:[]}]),q=e([{type:"primary",icon:"plus",state:"save",label:"新增",disabled:!1,permissions:[]},{type:"success",icon:"edit",state:"edit",label:"修改",disabled:!0,permissions:[]},{type:"danger",icon:"delete",state:"remove",label:"批量删除",disabled:!0,permissions:[]}]),A=[{label:"工时编码",prop:"id"},{prop:"workhoursType",label:"工时类型"},{prop:"createTime",label:"创建时间"},{prop:"remark",label:"备注"}],E={style:{"margin-bottom":"20px"}},F={class:"dialog-footer"},M=V(a({__name:"index",setup(a){const V=l(),M=e({workhoursType:[{required:!0,message:"工时名称不能为空",trigger:"blur"}]}),R=e({workhoursType:""});let B=e({total:0,pageSize:10,pageSizes:[5,10,15,20],currentPage:1});const G=l(!1),K=l(!1);let N=e([]),Q=l(""),X=l([]),Z=l({workhoursType:"",remark:"",id:""}),I=l([]);const J=()=>{se()},L=e=>{X.value=e.map((e=>null==e?void 0:e.id)),q[1].disabled=!X.value.length||X.value.length>=2,q[2].disabled=!X.value.length},O=()=>{Q.value="添加工时类型名称",K.value=!0},W=()=>{Q.value="修改工时类型名称";const e=X.value[0];Y(e)},Y=e=>{j(e).then((e=>{Z.value=e,K.value=!0}))},$=e=>{const a=X.value,l=Array.isArray(a)?a:[a];y.confirm("是否删除当前数据?").then((()=>{z(l).then((e=>{f.success("删除成功"),se()}))}))},ee=e=>{B=null==e?void 0:e.value,se()},ae=async()=>{V.value&&await V.value.validate((e=>{e&&(Z.value.id?D(Z.value).then((e=>{f.success("修改成功"),le(),se()})):U(Z.value).then((e=>{f.success("新增成功"),le(),se()})))}))},le=()=>{Z.value={workhoursType:"",remark:"",id:""},K.value=!1},se=()=>{const e={...R,pageNo:B.currentPage,pageSize:B.pageSize};G.value=!0,H(e).then((e=>{const{total:a,list:l=[]}=e||{};B.total=a,N=l,I.value=l.map((e=>({label:e.workhoursType,value:e.workhoursType}))),P[0].options=I.value})).finally((()=>{G.value=!1}))};return s((()=>{se()})),(e,a)=>{const l=h,s=b,y=k,f=g,j=_,z=w,D=T,U=x;return o(),t(d,null,[r(l,null,{default:n((()=>[r(C,{"conditions-list":u(P),"search-model":R,onQueryData:J},null,8,["conditions-list","search-model"])])),_:1}),r(l,null,{default:n((()=>[i("div",E,[(o(!0),t(d,null,p(u(q),((e,a)=>(o(),c(s,{plain:"",type:e.type,key:a,icon:e.icon,disabled:e.disabled,onClick:a=>(e=>{switch(e){case"save":O();break;case"edit":W();break;case"remove":$()}})(e.state)},{default:n((()=>[m(v(e.label),1)])),_:2},1032,["type","icon","disabled","onClick"])))),128))]),r(S,{loading:G.value,isSelection:!0,columns:u(A),tableData:u(N),pagination:u(B),onSelectionChange:L,onPaginationChange:ee},null,8,["loading","columns","tableData","pagination"])])),_:1}),r(U,{width:"40%",modelValue:K.value,"onUpdate:modelValue":a[2]||(a[2]=e=>K.value=e),title:u(Q),onBeforeClose:le},{footer:n((()=>[i("span",F,[r(s,{type:"primary",onClick:ae},{default:n((()=>[m(" 提交 ")])),_:1}),r(s,{onClick:le},{default:n((()=>[m("返回")])),_:1})])])),default:n((()=>[r(D,{ref_key:"ruleFormRef",ref:V,model:u(Z),rules:M,"label-width":"110px"},{default:n((()=>[r(z,null,{default:n((()=>[r(j,{span:22},{default:n((()=>[r(f,{label:"工时名称",prop:"workhoursType"},{default:n((()=>[r(y,{modelValue:u(Z).workhoursType,"onUpdate:modelValue":a[0]||(a[0]=e=>u(Z).workhoursType=e)},null,8,["modelValue"])])),_:1})])),_:1})])),_:1}),r(z,null,{default:n((()=>[r(j,{span:22},{default:n((()=>[r(f,{label:"备注",prop:"remark"},{default:n((()=>[r(y,{modelValue:u(Z).remark,"onUpdate:modelValue":a[1]||(a[1]=e=>u(Z).remark=e),type:"textarea",placeholder:"请输入内容"},null,8,["modelValue"])])),_:1})])),_:1})])),_:1})])),_:1},8,["model","rules"])])),_:1},8,["modelValue","title"])],64)}}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/prodMgmt/workHoursType/index.vue"]]);export{M as default};