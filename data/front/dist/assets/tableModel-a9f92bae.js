import{_ as a}from"./Dialog-da8c6c8d.js";import{r as e,d as l,e as t,f as n,o,c as i,j as u,k as d,w as s,X as r,F as p,n as m,a as c,G as v,I as y,b4 as g,aR as b,aK as f,Z as _,b7 as h,b8 as C,aa as w,ab as k,at as V,au as q,ae as D,_ as T}from"./index-4bfb878d.js";import{_ as N}from"./index-05818f1a.js";const x=[{type:"selection",width:"55"},{label:"数值",prop:"quantityFrom"},{label:"数原单位名称值",prop:"unitName"},{label:"转换后数值",prop:"quantityTo"},{label:"现单位名称",prop:"unitTo"}],F=async a=>await e.get({url:"/mes/md/conversion/list",params:a}),j={style:{"margin-bottom":"16px"}},U=T(l({__name:"tableModel",props:{tableDataList:{type:Array,default:()=>[]},paginationData:{type:Object,default:()=>{}}},emits:["handlePagination","handleRenewData"],setup(l,{emit:T}){const F=l,U=t(),I=n({quantityTo:[{required:!0,message:"请输入转换后数值",trigger:"change"}]}),S=t("");let z=n({quantityFrom:"",unitCode:"",quantityTo:"",unitTo:"",conversionId:""});const M=t(!1),P=t(!0),R=t(!0),O=t([]),L=t([{measureCode:"",measureName:"",enableFlag:""}]),$=()=>{T("handleRenewData")},A=()=>{var a;let l={unitName:null==(a=L.value.find((a=>{a.measureCode,z.unitCode})))?void 0:a.measureName,...z};if(l.unitName==l.unitTo)return b({message:"现单位名称不能与原单位名称一致",type:"warning"});null!=l.conversionId?(async a=>e.put({url:"/mes/md/conversion",data:a}))(l).then((a=>{const{code:e}=a;200==e&&$()})):(async a=>await e.post({url:"/mes/md/conversion",data:a}))(l).then((a=>{const{code:e}=a;200==e&&$()}))},E=async()=>{z.conversionId="",z.quantityFrom="",z.quantityTo="",z.unitCode="",z.unitTo="",M.value=!M.value},G=a=>{O.value=a.map((a=>a.conversionId)),R.value=!O.value.length,P.value=!O.value.length||O.value.length>=2},K=()=>{S.value="添加单位换算",M.value=!M.value},X=a=>{let e=a.conversionId||O.value;f.confirm(`是否删除"${e}"数据`).then((()=>{console.log(e)})).catch((()=>{}))},Z=a=>{M.value=!M.value,S.value="修改单位换算",(async a=>await e.get({url:`/mes/md/conversion/${a}`}))(a.conversionId||O.value).then((a=>{const{code:e,data:l}=a||{};200===e&&(z=l)}))},B=a=>{T("handlePagination",a)};return(e,t)=>{const n=_,b=h,f=C,T=N,O=w,$=k,H=V,J=q,Q=D,W=a;return o(),i(p,null,[u("div",j,[d(n,{type:"primary",plain:"",onClick:K},{default:s((()=>[r("新增")])),_:1}),d(n,{type:"success",plain:"",onClick:Z,disabled:P.value},{default:s((()=>[r("修改")])),_:1},8,["disabled"]),d(n,{type:"danger",plain:"",onClick:X,disabled:R.value},{default:s((()=>[r("删除")])),_:1},8,["disabled"])]),u("div",null,[d(f,{data:F.tableDataList,style:{width:"100%"},onSelectionChange:G},{default:s((()=>[(o(!0),i(p,null,m(c(x),((a,e)=>(o(),v(b,{key:e,prop:a.prop,label:a.label,width:a.width,align:a.align,type:a.type},null,8,["prop","label","width","align","type"])))),128)),d(b,{fixed:"right",label:"操作",width:"120"},{default:s((a=>[d(n,{link:"",type:"primary",size:"small",onClick:e=>Z(a.row)},{default:s((()=>[r(" 修改 ")])),_:2},1032,["onClick"]),d(n,{link:"",type:"primary",size:"small",onClick:e=>X(a.row)},{default:s((()=>[r(" 删除 ")])),_:2},1032,["onClick"])])),_:1})])),_:1},8,["data"]),y(d(T,{total:l.paginationData.total,page:l.paginationData.pageNo,"onUpdate:page":t[0]||(t[0]=a=>l.paginationData.pageNo=a),limit:l.paginationData.pageSize,"onUpdate:limit":t[1]||(t[1]=a=>l.paginationData.pageSize=a),onPagination:B},null,8,["total","page","limit"]),[[g,l.paginationData.total>0]])]),d(W,{title:S.value,modelValue:M.value,"onUpdate:modelValue":t[7]||(t[7]=a=>M.value=a)},{footer:s((()=>[d(n,{onClick:t[6]||(t[6]=a=>(async a=>{a&&await a.validate((a=>{a&&A()}))})(U.value)),type:"primary",plain:""},{default:s((()=>[r("确定")])),_:1}),d(n,{onClick:E},{default:s((()=>[r("取消")])),_:1})])),default:s((()=>[d(Q,{ref_key:"ruleFormRef",ref:U,rules:c(I),"label-position":"right","label-width":"100px",model:c(z),style:{"max-width":"460px"}},{default:s((()=>[d($,{label:"数值"},{default:s((()=>[d(O,{modelValue:c(z).quantityFrom,"onUpdate:modelValue":t[2]||(t[2]=a=>c(z).quantityFrom=a)},null,8,["modelValue"])])),_:1}),d($,{label:"原单位名称"},{default:s((()=>[d(J,{modelValue:c(z).unitCode,"onUpdate:modelValue":t[3]||(t[3]=a=>c(z).unitCode=a),placeholder:"原单位名称"},{default:s((()=>[(o(!0),i(p,null,m(L.value,(a=>(o(),v(H,{key:a.measureCode,label:a.measureName,value:a.measureCode,disabled:"N"==a.enableFlag},null,8,["label","value","disabled"])))),128))])),_:1},8,["modelValue"])])),_:1}),d($,{label:"转换后数值",prop:"quantityTo"},{default:s((()=>[d(O,{modelValue:c(z).quantityTo,"onUpdate:modelValue":t[4]||(t[4]=a=>c(z).quantityTo=a)},null,8,["modelValue"])])),_:1}),d($,{label:"现单位名称"},{default:s((()=>[d(J,{modelValue:c(z).unitTo,"onUpdate:modelValue":t[5]||(t[5]=a=>c(z).unitTo=a),placeholder:"转换后单位名称"},{default:s((()=>[(o(!0),i(p,null,m(L.value,(a=>(o(),v(H,{key:a.measureCode,label:a.measureName,value:a.measureCode,disabled:"N"==a.enableFlag},null,8,["label","value","disabled"])))),128))])),_:1},8,["modelValue"])])),_:1})])),_:1},8,["rules","model"])])),_:1},8,["title","modelValue"])],64)}}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/masterData/unitConversion/components/tableModel.vue"]]),I=Object.freeze(Object.defineProperty({__proto__:null,default:U},Symbol.toStringTag,{value:"Module"}));export{I as a,F as q,U as t};