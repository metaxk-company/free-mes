import{d as e,f as r,e as l,a_ as a,o as d,G as o,w as u,j as m,a as t,X as p,J as s,k as i,c as n,F,n as c,t as b,aK as f,aH as O,aR as _,aa as V,ab as g,v,aO as k,E as C,ac as D,ad as y,Z as h,aL as w,cb as q,ae as M,A as U,B as N,a0 as j,_ as S}from"./index-4bfb878d.js";import{S as R}from"./data-6c43379c.js";import x from"./index-6ebcd94a.js";import Y from"./index-b537b466.js";import E from"./index-e08c5726.js";import{c as G,u as I,e as A}from"./pmOrder-563fbf59.js";import"./const-f443e30b.js";import"./treeMaterial-9b585804.js";import"./materialData-d3bde0f1.js";import"./index-6c8a85ac.js";import"./index-529a6cde.js";const H=m("div",null,null,-1),K={class:"dialog-footer"},L=S(e({__name:"orderDialog",props:{pmOrderForm:{type:Object,default:()=>{}}},emits:["upDateTableList"],setup(e,{emit:S}){const L=e,P=r({workorderCode:[{required:!0,message:"订单编码不能为空",trigger:"blur"}],workorderName:[{required:!0,message:"订单名称不能为空",trigger:"blur"}],orderSource:[{required:!0,message:"来源类型不能为空",trigger:"blur"}],productId:[{required:!0,message:"产品不能为空",trigger:"blur"}],productCode:[{required:!0,message:"产品编号不能为空",trigger:"blur"}],productName:[{required:!0,message:"产品名称不能为空",trigger:"blur"}],unitOfMeasure:[{required:!0,message:"单位不能为空",trigger:"blur"}],quantity:[{required:!0,message:"生产数量不能为空",trigger:"blur"}],requestDate:[{required:!0,message:"预计结束日期不能为空",trigger:"blur"}],orderDate:[{required:!0,message:"收订单日期不能为空",trigger:"blur"}],produceDate:[{required:!0,message:"预计生产日期不能为空",trigger:"blur"}]}),B=l(),J=l(null),T=l(null),z=l(!1),W=a((()=>"edit"==L.pmOrderForm.state)),X=()=>{f.confirm("是否完成订单编制？【完成后将不能更改】").then((()=>{L.pmOrderForm.ruleForm.status="CONFIRMED",re()}))},Z=()=>{T.value.visibleProduct=!0},Q=()=>{J.value.visibleClient=!0},$=e=>{null!=e&&(L.pmOrderForm.ruleForm.productId=e.id,L.pmOrderForm.ruleForm.productCode=e.itemCode,L.pmOrderForm.ruleForm.productName=e.itemName,L.pmOrderForm.ruleForm.productSpc=e.specification,L.pmOrderForm.ruleForm.unitOfMeasure=e.unitOfMeasure)},ee=e=>{L.pmOrderForm.ruleForm.clientCode=e.clientCode,L.pmOrderForm.ruleForm.clientName=e.clientName},re=()=>{B.value&&B.value.validate(((e,r)=>{if(e)if(L.pmOrderForm.ruleForm.id){const e={workorderId:L.pmOrderForm.ruleForm.id,...L.pmOrderForm.ruleForm};console.log(e),I(e).then((e=>{_({message:"修改成功",type:"success"}),ae()}))}else z.value=!0,A(L.pmOrderForm.ruleForm).then((e=>{_({message:"新增成功",type:"success"}),L.pmOrderForm.ruleForm.id=e||"",le()})).finally((()=>{z.value=!1}))}))},le=()=>{S("upDateTableList")},ae=()=>{B.value&&(B.value.resetFields(),L.pmOrderForm.ruleForm={},le(),L.pmOrderForm.visible=!1)};return(r,l)=>{const a=V,f=g,_=v,S=k,I=C,A=D,le=y,de=h,oe=w,ue=q,me=M,te=U,pe=N,se=j;return d(),o(se,{modelValue:e.pmOrderForm.visible,"onUpdate:modelValue":l[17]||(l[17]=r=>e.pmOrderForm.visible=r),title:e.pmOrderForm.tips,width:"70%",onClose:ae},{footer:u((()=>{var r,l;return[m("span",K,[W.value?(d(),o(de,{key:0,type:"primary",onClick:re,loading:t(z)},{default:u((()=>[p("确定")])),_:1},8,["loading"])):s("",!0),"PREPARE"==(null==(r=e.pmOrderForm.ruleForm)?void 0:r.status)&&(null==(l=e.pmOrderForm.ruleForm)?void 0:l.id)?(d(),o(de,{key:1,type:"success",loading:t(z),onClick:X},{default:u((()=>[p("完成")])),_:1},8,["loading"])):s("",!0),i(de,{onClick:ae},{default:u((()=>[p("取消")])),_:1})])]})),default:u((()=>[i(me,{ref_key:"ruleFormRef",ref:B,model:e.pmOrderForm.ruleForm,rules:t(P),"label-width":"120px"},{default:u((()=>[i(I,null,{default:u((()=>[i(_,{span:8},{default:u((()=>[i(f,{label:"订单编号",prop:"workorderCode"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.workorderCode,"onUpdate:modelValue":l[0]||(l[0]=r=>e.pmOrderForm.ruleForm.workorderCode=r),placeholder:"请输入订单编号",disabled:!W.value},null,8,["modelValue","disabled"])])),_:1})])),_:1}),W.value?(d(),o(_,{key:0,span:8},{default:u((()=>[i(f,{label:"自动生成",prop:"autoGenFlag"},{default:u((()=>["view"!=e.pmOrderForm.ruleForm.status?(d(),o(S,{key:0,modelValue:e.pmOrderForm.ruleForm.autoGenFlag,"onUpdate:modelValue":l[1]||(l[1]=r=>e.pmOrderForm.ruleForm.autoGenFlag=r),"active-color":"#13ce66",onChange:l[2]||(l[2]=r=>(e=>{if(!e)return;L.pmOrderForm.ruleForm.workorderCode="",G("WORKORDER_CODE").then((e=>{O((()=>{L.pmOrderForm.ruleForm.workorderCode=e}))}))})(e.pmOrderForm.ruleForm.autoGenFlag))},null,8,["modelValue"])):s("",!0)])),_:1})])),_:1})):s("",!0),i(_,{span:8},{default:u((()=>[i(f,{label:"订单名称",prop:"workorderName"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.workorderName,"onUpdate:modelValue":l[3]||(l[3]=r=>e.pmOrderForm.ruleForm.workorderName=r),placeholder:"请输入订单名称",disabled:!W.value},null,8,["modelValue","disabled"])])),_:1})])),_:1})])),_:1}),i(I,null,{default:u((()=>[i(_,{span:8},{default:u((()=>[i(f,{label:"来源类型",prop:"orderSource"},{default:u((()=>[i(le,{modelValue:e.pmOrderForm.ruleForm.orderSource,"onUpdate:modelValue":l[4]||(l[4]=r=>e.pmOrderForm.ruleForm.orderSource=r),class:"ml-4"},{default:u((()=>[(d(!0),n(F,null,c(t(R),((e,r)=>(d(),o(A,{label:e.label,key:r},{default:u((()=>[p(b(e.text),1)])),_:2},1032,["label"])))),128))])),_:1},8,["modelValue"])])),_:1})])),_:1}),i(_,{span:8},{default:u((()=>[i(f,{label:"产品编号",prop:"productCode"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.productCode,"onUpdate:modelValue":l[5]||(l[5]=r=>e.pmOrderForm.ruleForm.productCode=r),placeholder:"请输入产品编号",disabled:!W.value},{append:u((()=>[i(de,{icon:"DocumentAdd",onClick:Z,disabled:!W.value},null,8,["disabled"])])),_:1},8,["modelValue","disabled"]),i(x,{ref_key:"itemSelectRef",ref:T,onHandleCurrentSubmit:$},null,512)])),_:1})])),_:1}),i(_,{span:8},{default:u((()=>[i(f,{label:"产品名称",prop:"productName"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.productName,"onUpdate:modelValue":l[6]||(l[6]=r=>e.pmOrderForm.ruleForm.productName=r),placeholder:"请输入产品名称",disabled:!W.value},null,8,["modelValue","disabled"])])),_:1})])),_:1})])),_:1}),i(I),i(I,null,{default:u((()=>[i(_,{span:8},{default:u((()=>[i(f,{label:"规格型号",prop:"productSpc"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.productSpc,"onUpdate:modelValue":l[7]||(l[7]=r=>e.pmOrderForm.ruleForm.productSpc=r),placeholder:"请输入规格型号",disabled:!W.value},null,8,["modelValue","disabled"])])),_:1})])),_:1}),i(_,{span:8},{default:u((()=>[i(f,{label:"单位名称",prop:"unitOfMeasure"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.unitOfMeasure,"onUpdate:modelValue":l[8]||(l[8]=r=>e.pmOrderForm.ruleForm.unitOfMeasure=r),placeholder:"请输入单位名称",disabled:!W.value},null,8,["modelValue","disabled"])])),_:1})])),_:1}),i(_,{span:8},{default:u((()=>[i(f,{label:"订单数量",prop:"quantity"},{default:u((()=>[i(oe,{min:1,disabled:!W.value,modelValue:e.pmOrderForm.ruleForm.quantity,"onUpdate:modelValue":l[9]||(l[9]=r=>e.pmOrderForm.ruleForm.quantity=r),placeholder:"请输入生产数量"},null,8,["disabled","modelValue"])])),_:1})])),_:1})])),_:1}),i(I,null,{default:u((()=>[i(_,{span:8},{default:u((()=>[i(f,{label:"批次号",prop:"batchCode"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.batchCode,"onUpdate:modelValue":l[10]||(l[10]=r=>e.pmOrderForm.ruleForm.batchCode=r),placeholder:"请输入批次号",disabled:!W.value},null,8,["modelValue","disabled"])])),_:1})])),_:1}),i(_,{span:8},{default:u((()=>[i(f,{label:"收订单日期",prop:"orderDate"},{default:u((()=>[i(ue,{clearable:"",disabled:!W.value,modelValue:e.pmOrderForm.ruleForm.orderDate,"onUpdate:modelValue":l[11]||(l[11]=r=>e.pmOrderForm.ruleForm.orderDate=r),type:"date","value-format":"YYYY-MM-DD",placeholder:"请选择收订单日期"},null,8,["disabled","modelValue"])])),_:1})])),_:1}),i(_,{span:8},{default:u((()=>[i(f,{label:"预计生产日期",prop:"produceDate"},{default:u((()=>[i(ue,{clearable:"",disabled:!W.value,modelValue:e.pmOrderForm.ruleForm.produceDate,"onUpdate:modelValue":l[12]||(l[12]=r=>e.pmOrderForm.ruleForm.produceDate=r),type:"date","value-format":"YYYY-MM-DD",placeholder:"请选择预计生产日期"},null,8,["disabled","modelValue"])])),_:1})])),_:1}),i(_,{span:8},{default:u((()=>[i(f,{label:"预计结束日期",prop:"requestDate"},{default:u((()=>[i(ue,{clearable:"",disabled:!W.value,modelValue:e.pmOrderForm.ruleForm.requestDate,"onUpdate:modelValue":l[13]||(l[13]=r=>e.pmOrderForm.ruleForm.requestDate=r),type:"date","value-format":"YYYY-MM-DD",placeholder:"请选择预计结束日期"},null,8,["disabled","modelValue"])])),_:1})])),_:1}),H,"ORDER"==e.pmOrderForm.ruleForm.orderSource?(d(),o(_,{key:0,span:8},{default:u((()=>[i(f,{label:"客户编码",prop:"clientCode"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.clientCode,"onUpdate:modelValue":l[14]||(l[14]=r=>e.pmOrderForm.ruleForm.clientCode=r),placeholder:"请输入客户编码",disabled:!W.value},{append:u((()=>[i(de,{icon:"DocumentAdd",onClick:Q,disabled:!W.value},null,8,["disabled"])])),_:1},8,["modelValue","disabled"]),i(Y,{onHandleCurrentSubmit:ee,ref_key:"clientModelRef",ref:J},null,512)])),_:1})])),_:1})):s("",!0),"ORDER"==e.pmOrderForm.ruleForm.orderSource?(d(),o(_,{key:1,span:8},{default:u((()=>[i(f,{label:"客户名称",prop:"clientName"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.clientName,"onUpdate:modelValue":l[15]||(l[15]=r=>e.pmOrderForm.ruleForm.clientName=r),placeholder:"请输入客户名称",disabled:!W.value},null,8,["modelValue","disabled"])])),_:1})])),_:1})):s("",!0),i(_,{span:8},{default:u((()=>[i(f,{label:"备注",prop:"remark"},{default:u((()=>[i(a,{modelValue:e.pmOrderForm.ruleForm.remark,"onUpdate:modelValue":l[16]||(l[16]=r=>e.pmOrderForm.ruleForm.remark=r),type:"textarea",autosize:"",placeholder:"请输入内容",disabled:!W.value},null,8,["modelValue","disabled"])])),_:1})])),_:1})])),_:1}),i(I)])),_:1},8,["model","rules"]),e.pmOrderForm.ruleForm.id?(d(),o(pe,{key:0,type:"border-card"},{default:u((()=>[i(te,{label:"BOM组成"},{default:u((()=>[i(E,{"work-order-id":e.pmOrderForm.ruleForm.id},null,8,["work-order-id"])])),_:1})])),_:1})):s("",!0)])),_:1},8,["modelValue","title"])}}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/prodMgmt/pmOrder/components/orderDialog.vue"]]);export{L as default};