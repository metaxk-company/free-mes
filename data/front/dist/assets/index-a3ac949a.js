import{q as e,f as l,D as a,d as t,K as o,u as i,e as s,g as n,a6 as d,C as u,o as r,G as m,w as p,k as c,a as f,I as y,X as v,t as h,J as b,z as _,c as k,F as V,n as w,ar as C,j as g,ao as x,aM as I,aN as T,L as P,M as U,ap as D,aq as S,aO as M,N as q,aa as R,aP as E,ab as F,at as j,au as O,ac as L,ad as B,ae as A,a0 as Y,Y as G,aQ as N,H as z,_ as X}from"./index-4bfb878d.js";import{b as K,g as Q}from"./index-cfea5fec.js";import{a as W,g as H,c as J,u as Z,d as $,b as ee,e as le}from"./index-c4ec4324.js";import{r as ae}from"./formRules-eb36c657.js";import{u as te}from"./useVxeCrudSchemas-092b8963.js";import{b as oe}from"./formCreate-a3356cdc.js";import{u as ie}from"./useXTable-309dffed.js";const{t:se}=e(),ne=l({key:[ae],name:[ae],category:[ae],formType:[ae],formId:[ae],formCustomCreatePath:[ae],formCustomViewPath:[ae]}),de=l({primaryKey:"key",primaryType:null,action:!0,actionWidth:"540px",columns:[{title:"流程标识",field:"key",isSearch:!0,table:{width:120}},{title:"流程名称",field:"name",isSearch:!0,table:{width:120,slots:{default:"name_default"}}},{title:"流程分类",field:"category",dictType:a.BPM_MODEL_CATEGORY,dictClass:"number",isSearch:!0,table:{slots:{default:"category_default"}}},{title:"表单信息",field:"formId",table:{width:180,slots:{default:"formId_default"}}},{title:"最新部署的流程定义",field:"processDefinition",isForm:!1,table:{children:[{title:"流程版本",field:"version",slots:{default:"version_default"},width:80},{title:"激活状态",field:"status",slots:{default:"status_default"},width:80},{title:"部署时间",field:"processDefinition.deploymentTime",formatter:"formatDate",width:180}]}},{title:se("common.createTime"),field:"createTime",isForm:!1,formatter:"formatDate",table:{width:180}}]}),{allSchemas:ue}=te(de),re=g("i",{style:{"padding-left":"5px"},class:"el-icon-question"},null,-1),me=g("i",{style:{"padding-left":"5px"},class:"el-icon-question"},null,-1),pe={key:1},ce=g("i",{style:{"padding-left":"5px"},class:"el-icon-question"},null,-1),fe=g("i",{style:{"padding-left":"5px"},class:"el-icon-question"},null,-1),ye=g("div",{class:"el-upload__text"},[v(" 将文件拖到此处，或 "),g("em",null,"点击上传")],-1),ve=g("div",{class:"el-upload__tip",style:{color:"red"}}," 提示：仅允许导入“bpm”或“xml”格式文件！ ",-1),he=X(t({__name:"index",setup(l){const{t:t}=e(),X=o(),ae=i(),te=s(!1),se=s(null),de=s({prefix:"flowable"}),[he,{reload:be}]=ie({allSchemas:ue,getListApi:W}),_e=s(),ke=s(!1),Ve=s({rule:[],option:{}}),we=async e=>{if(10==e.formType){const l=await Q(e.formId);oe(Ve,l.conf,l.fields),ke.value=!0}else await ae.push({path:e.formCustomCreatePath})},Ce=s(!1),ge=s("新建模型"),xe=s(!1),Ie=s(),Te=s(),Pe=async e=>{ge.value=t("action."+e),Ce.value=!0},Ue=async()=>{Se(),await Pe("create")},De=async()=>{const e=f(Te);if(!e)return;if(await e.validate()){xe.value=!0;try{const e=Ie.value;e.id?(await Z(e),X.success(t("common.updateSuccess"))):(await J(e),X.success(t("common.createSuccess"))),Ce.value=!1}finally{await be(),xe.value=!1}}},Se=()=>{var e;Ie.value={formType:10,name:"",courseSort:"",description:"",formId:"",formCustomCreatePath:"",formCustomViewPath:""},null==(e=Te.value)||e.resetFields()},Me=s();const qe=s(),Re=s(!1),Ee=s(!1),Fe=s(),je=s({key:"",name:"",description:""}),Oe=()=>{Re.value=!0},Le=()=>{X.error("最多只能上传一个文件！")},Be=()=>{X.error("导入流程失败，请您重新上传！")},Ae=()=>{qe.value={Authorization:"Bearer "+I(),"tenant-id":T()},Ee.value=!0,Me.value.submit()},Ye=async e=>{0===e.code?(Ge(),X.success("导入流程成功！请点击【设计流程】按钮，进行编辑保存后，才可以进行【发布流程】"),await be()):X.error(e.msg)},Ge=()=>{var e;Re.value=!1,Ee.value=!1,Me.value.clearFiles(),je.value={key:"",name:"",description:""},null==(e=Fe.value)||e.resetFields()};return n((()=>{K().then((e=>{_e.value=e}))})),(e,l)=>{const o=P,i=U,s=D,n=S,I=M,T=q,K=R,Q=E,W=F,J=j,Z=O,oe=L,ie=B,ue=A,Ne=Y,ze=G,Xe=N,Ke=d("form-create"),Qe=d("my-process-viewer"),We=z,He=u("hasPermi");return r(),m(We,null,{default:p((()=>[c(T,{onRegister:f(he)},{toolbar_buttons:p((()=>[y(c(o,{type:"primary",preIcon:"ep:zoom-in",title:"新建流程",onClick:Ue},null,512),[[He,["bpm:model:create"]]]),c(o,{type:"warning",preIcon:"ep:upload",title:"导入流程",onClick:Oe,style:{"margin-left":"10px"}})])),name_default:p((({row:e})=>[c(i,{title:e.name,onClick:l=>(e=>{console.log(e),H(e).then((e=>{console.log(e,"response"),se.value=e.bpmnXml,te.value=!0}))})(e.id)},null,8,["title","onClick"])])),category_default:p((({row:e})=>[c(s,{type:f(a).BPM_MODEL_CATEGORY,value:Number(null==e?void 0:e.category)},null,8,["type","value"])])),formId_default:p((({row:e})=>{var l;return[10===e.formType?(r(),m(i,{key:0,title:(null==(l=f(_e).find((l=>l.id===e.formId)))?void 0:l.name)||e.formId,onClick:l=>we(e)},null,8,["title","onClick"])):(r(),m(i,{key:1,title:e.formCustomCreatePath,onClick:l=>we(e)},null,8,["title","onClick"]))]})),version_default:p((({row:e})=>[e.processDefinition?(r(),m(n,{key:0},{default:p((()=>[v("v"+h(e.processDefinition.version),1)])),_:2},1024)):(r(),m(n,{key:1,type:"warning"},{default:p((()=>[v("未部署")])),_:1}))])),status_default:p((({row:e})=>[e.processDefinition?(r(),m(I,{key:0,modelValue:e.processDefinition.suspensionState,"onUpdate:modelValue":l=>e.processDefinition.suspensionState=l,"active-value":1,"inactive-value":2,onChange:l=>(e=>{const l=e.id,a=e.processDefinition.suspensionState,o="是否确认"+(1===a?"激活":"挂起")+'流程名字为"'+e.name+'"的数据项?';X.confirm(o).then((async()=>{await ee(l,a),X.success(t("部署成功")),be()})).catch((()=>{e.processDefinition.suspensionState=1===a?2:1}))})(e)},null,8,["modelValue","onUpdate:modelValue","onChange"])):b("",!0)])),actionbtns_default:p((({row:e})=>[y(c(i,{preIcon:"ep:edit",title:"修改流程",onClick:l=>(async e=>{Se(),await Pe("edit"),Ie.value=await H(e),null==Ie.value.category?Ie.value.category=1:Ie.value.category=Number(Ie.value.category)})(e.id)},null,8,["onClick"]),[[He,["bpm:model:update"]]]),y(c(i,{preIcon:"ep:setting",title:"设计流程",onClick:l=>(e=>{console.log(e,"设计流程"),ae.push({name:"modelEditor",query:{modelId:e.id}})})(e)},null,8,["onClick"]),[[He,["bpm:model:update"]]]),y(c(i,{preIcon:"ep:user",title:"分配规则",onClick:l=>(e=>{ae.push({name:"BpmTaskAssignRuleList",query:{modelId:e.id}})})(e)},null,8,["onClick"]),[[He,["bpm:task-assign-rule:query"]]]),y(c(i,{preIcon:"ep:position",title:"发布流程",onClick:l=>(e=>{X.confirm("是否部署该流程！！").then((async()=>{await le(e.id),X.success(t("部署成功")),be()}))})(e)},null,8,["onClick"]),[[He,["bpm:model:deploy"]]]),y(c(i,{preIcon:"ep:aim",title:"流程定义",onClick:l=>(e=>{ae.push({name:"BpmProcessDefinitionList",query:{key:e.key}})})(e)},null,8,["onClick"]),[[He,["bpm:process-definition:query"]]]),y(c(i,{preIcon:"ep:delete",title:f(t)("action.del"),onClick:l=>{return a=e.id,void X.delConfirm("是否删除该流程！！").then((async()=>{await $(a),X.success(t("common.delSuccess")),be()}));var a}},null,8,["title","onClick"]),[[He,["bpm:model:delete"]]])])),_:1},8,["onRegister"]),c(Ne,{modelValue:f(Ce),"onUpdate:modelValue":l[9]||(l[9]=e=>_(Ce)?Ce.value=e:null),title:f(ge),width:"600"},{footer:p((()=>[c(o,{type:"primary",loading:f(xe),onClick:De,title:f(t)("action.save")},null,8,["loading","title"]),c(o,{loading:f(xe),onClick:l[8]||(l[8]=e=>Ce.value=!1),title:f(t)("dialog.close")},null,8,["loading","title"])])),default:p((()=>[c(ue,{loading:f(xe),"el-form":"",ref_key:"saveFormRef",ref:Te,model:f(Ie),rules:f(ne),"label-width":"110px"},{default:p((()=>[c(W,{label:"流程标识",prop:"key"},{default:p((()=>[c(K,{modelValue:f(Ie).key,"onUpdate:modelValue":l[0]||(l[0]=e=>f(Ie).key=e),placeholder:"请输入流标标识",style:{width:"330px"},disabled:!!f(Ie).id},null,8,["modelValue","disabled"]),f(Ie).id?(r(),m(Q,{key:1,class:"item",effect:"light",content:"流程标识不可修改！",placement:"top"},{default:p((()=>[me])),_:1})):(r(),m(Q,{key:0,class:"item",effect:"light",content:"新建后，流程标识不可修改！",placement:"top"},{default:p((()=>[re])),_:1}))])),_:1}),c(W,{label:"流程名称",prop:"name"},{default:p((()=>[c(K,{modelValue:f(Ie).name,"onUpdate:modelValue":l[1]||(l[1]=e=>f(Ie).name=e),placeholder:"请输入流程名称",disabled:!!f(Ie).id,clearable:""},null,8,["modelValue","disabled"])])),_:1}),f(Ie).id?(r(),m(W,{key:0,label:"流程分类",prop:"category"},{default:p((()=>[c(Z,{modelValue:f(Ie).category,"onUpdate:modelValue":l[2]||(l[2]=e=>f(Ie).category=e),placeholder:"请选择流程分类",clearable:"",style:{width:"100%"}},{default:p((()=>[(r(!0),k(V,null,w(f(C)(f(a).BPM_MODEL_CATEGORY),(e=>(r(),m(J,{key:e.value,label:e.label,value:e.value},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1})):b("",!0),c(W,{label:"流程描述",prop:"description"},{default:p((()=>[c(K,{type:"textarea",modelValue:f(Ie).description,"onUpdate:modelValue":l[3]||(l[3]=e=>f(Ie).description=e),clearable:""},null,8,["modelValue"])])),_:1}),f(Ie).id?(r(),k("div",pe,[c(W,{label:"表单类型",prop:"formType"},{default:p((()=>[c(ie,{modelValue:f(Ie).formType,"onUpdate:modelValue":l[4]||(l[4]=e=>f(Ie).formType=e)},{default:p((()=>[(r(!0),k(V,null,w(f(C)(f(a).BPM_MODEL_FORM_TYPE),(e=>(r(),m(oe,{key:parseInt(e.value),label:parseInt(e.value)},{default:p((()=>[v(h(e.label),1)])),_:2},1032,["label"])))),128))])),_:1},8,["modelValue"])])),_:1}),10===f(Ie).formType?(r(),m(W,{key:0,label:"流程表单",prop:"formId"},{default:p((()=>[c(Z,{modelValue:f(Ie).formId,"onUpdate:modelValue":l[5]||(l[5]=e=>f(Ie).formId=e),clearable:"",style:{width:"100%"}},{default:p((()=>[(r(!0),k(V,null,w(f(_e),(e=>(r(),m(J,{key:e.id,label:e.name,value:e.id},null,8,["label","value"])))),128))])),_:1},8,["modelValue"])])),_:1})):b("",!0),20===f(Ie).formType?(r(),m(W,{key:1,label:"表单提交路由",prop:"formCustomCreatePath"},{default:p((()=>[c(K,{modelValue:f(Ie).formCustomCreatePath,"onUpdate:modelValue":l[6]||(l[6]=e=>f(Ie).formCustomCreatePath=e),placeholder:"请输入表单提交路由",style:{width:"330px"}},null,8,["modelValue"]),c(Q,{class:"item",effect:"light",content:"自定义表单的提交路径，使用 Vue 的路由地址，例如说：bpm/oa/leave/create",placement:"top"},{default:p((()=>[ce])),_:1})])),_:1})):b("",!0),20===f(Ie).formType?(r(),m(W,{key:2,label:"表单查看路由",prop:"formCustomViewPath"},{default:p((()=>[c(K,{modelValue:f(Ie).formCustomViewPath,"onUpdate:modelValue":l[7]||(l[7]=e=>f(Ie).formCustomViewPath=e),placeholder:"请输入表单查看路由",style:{width:"330px"}},null,8,["modelValue"]),c(Q,{class:"item",effect:"light",content:"自定义表单的查看路径，使用 Vue 的路由地址，例如说：bpm/oa/leave/view",placement:"top"},{default:p((()=>[fe])),_:1})])),_:1})):b("",!0)])):b("",!0)])),_:1},8,["loading","model","rules"])])),_:1},8,["modelValue","title"]),c(Ne,{modelValue:f(Re),"onUpdate:modelValue":l[13]||(l[13]=e=>_(Re)?Re.value=e:null),width:"400",title:"导入流程"},{footer:p((()=>[c(o,{type:"warning",preIcon:"ep:upload-filled",title:f(t)("action.save"),onClick:Ae},null,8,["title"]),c(o,{title:"取 消",onClick:Ge})])),default:p((()=>[g("div",null,[c(Xe,{ref_key:"uploadRef",ref:Me,action:f("https://mesv2-api.cloudmes.io/admin-api/bpm/model/import"),headers:f(qe),drag:!0,limit:1,multiple:!0,"show-file-list":!0,disabled:f(Ee),"on-exceed":Le,"on-success":Ye,"on-error":Be,"auto-upload":!1,accept:".bpmn, .xml",name:"bpmnFile",data:f(je)},{tip:p((()=>[ve,g("div",null,[c(ue,{ref_key:"importFormRef",ref:Fe,model:f(je),rules:f(ne),"label-width":"120px","status-icon":""},{default:p((()=>[c(W,{label:"流程标识",prop:"key"},{default:p((()=>[c(K,{modelValue:f(je).key,"onUpdate:modelValue":l[10]||(l[10]=e=>f(je).key=e),placeholder:"请输入流标标识",style:{width:"250px"}},null,8,["modelValue"])])),_:1}),c(W,{label:"流程名称",prop:"name"},{default:p((()=>[c(K,{modelValue:f(je).name,"onUpdate:modelValue":l[11]||(l[11]=e=>f(je).name=e),placeholder:"请输入流程名称",clearable:""},null,8,["modelValue"])])),_:1}),c(W,{label:"流程描述",prop:"description"},{default:p((()=>[c(K,{type:"textarea",modelValue:f(je).description,"onUpdate:modelValue":l[12]||(l[12]=e=>f(je).description=e),clearable:""},null,8,["modelValue"])])),_:1})])),_:1},8,["model","rules"])])])),default:p((()=>[c(ze,{class:"el-icon--upload",icon:"ep:upload-filled"}),ye])),_:1},8,["action","headers","disabled","data"])])])),_:1},8,["modelValue"]),c(Ne,{modelValue:f(ke),"onUpdate:modelValue":l[14]||(l[14]=e=>_(ke)?ke.value=e:null),width:"800",title:"表单详情","show-footer":!1},{default:p((()=>[f(ke)?(r(),m(Ke,{key:0,rule:f(Ve).rule,option:f(Ve).option},null,8,["rule","option"])):b("",!0)])),_:1},8,["modelValue"]),c(Ne,{title:"流程图",modelValue:f(te),"onUpdate:modelValue":l[16]||(l[16]=e=>_(te)?te.value=e:null),width:"80%",height:"90%"},{default:p((()=>[c(Qe,x({key:"designer",modelValue:f(se),"onUpdate:modelValue":l[15]||(l[15]=e=>_(se)?se.value=e:null),value:f(se)},f(de),{prefix:f(de).prefix}),null,16,["modelValue","value","prefix"])])),_:1},8,["modelValue"])])),_:1})}}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/bpm/model/index.vue"]]);export{he as default};