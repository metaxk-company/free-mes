import{q as e,f as a,D as t,r as l,d as i,K as s,e as r,C as o,o as c,c as d,k as n,w as m,a as u,I as p,G as y,J as f,z as v,F as h,L as C,M as g,N as k,H as S,aJ as _,a1 as w,a0 as R,_ as T}from"./index-4bfb878d.js";import{r as E}from"./formRules-eb36c657.js";import{u as x}from"./useVxeCrudSchemas-092b8963.js";import{u as D}from"./useXTable-309dffed.js";const{t:b}=e(),j=a({applicationName:[E],code:[E],message:[E]}),I=a({primaryKey:"id",primaryType:"id",primaryTitle:"编号",action:!0,columns:[{title:"错误码类型",field:"type",dictType:t.SYSTEM_ERROR_CODE_TYPE,dictClass:"number",isSearch:!0},{title:"应用名",field:"applicationName",isSearch:!0},{title:"错误码编码",field:"code",isSearch:!0},{title:"错误码错误提示",field:"message",isSearch:!0},{title:b("common.createTime"),field:"createTime",formatter:"formatDate",isForm:!1,search:{show:!0,itemRender:{name:"XDataTimePicker"}}}]}),{allSchemas:M}=x(I),V=e=>l.get({url:"/admin-api/system/error-code/page",params:e}),F=e=>l.get({url:"/admin-api/system/error-code/get?id="+e}),N=e=>l.delete({url:"/admin-api/system/error-code/delete?id="+e}),P=i({name:"ErrorCode"}),q=T(i({...P,setup(a){const{t:t}=e(),i=s(),[T,{reload:E,deleteData:x}]=D({allSchemas:M,getListApi:V,deleteApi:N}),b=r(!1),I=r("edit"),P=r(""),q=r(!1),z=r(),A=r(),J=e=>{I.value=t("action."+e),P.value=e,b.value=!0},K=async()=>{var e;const a=null==(e=u(z))?void 0:e.getElFormRef();a&&a.validate((async e=>{var a;if(e){q.value=!0;try{const e=null==(a=u(z))?void 0:a.formModel;"create"===P.value?(await(e=>l.post({url:"/admin-api/system/error-code/create",data:e}))(e),i.success(t("common.createSuccess"))):(await(e=>l.put({url:"/admin-api/system/error-code/update",data:e}))(e),i.success(t("common.updateSuccess"))),b.value=!1}finally{q.value=!1,await E()}}}))};return(e,a)=>{const l=C,i=g,s=k,r=S,E=_,D=w,V=R,N=o("hasPermi");return c(),d(h,null,[n(r,null,{default:m((()=>[n(s,{onRegister:u(T)},{toolbar_buttons:m((()=>[p(n(l,{type:"primary",preIcon:"ep:zoom-in",title:u(t)("action.add"),onClick:a[0]||(a[0]=e=>{J("create")})},null,8,["title"]),[[N,["system:error-code:create"]]])])),actionbtns_default:m((({row:e})=>[p(n(i,{preIcon:"ep:edit",title:u(t)("action.edit"),onClick:a=>(async e=>{var a;J("update");const t=await F(e);null==(a=u(z))||a.setValues(t)})(e.id)},null,8,["title","onClick"]),[[N,["system:error-code:update"]]]),p(n(i,{preIcon:"ep:view",title:u(t)("action.detail"),onClick:a=>(async e=>{J("detail");const a=await F(e);A.value=a})(e.id)},null,8,["title","onClick"]),[[N,["system:error-code:query"]]]),p(n(i,{preIcon:"ep:delete",title:u(t)("action.del"),onClick:a=>u(x)(e.id)},null,8,["title","onClick"]),[[N,["system:error-code:delete"]]])])),_:1},8,["onRegister"])])),_:1}),n(V,{id:"errorCodeModel",modelValue:u(b),"onUpdate:modelValue":a[3]||(a[3]=e=>v(b)?b.value=e:null),title:u(I)},{footer:m((()=>[["create","update"].includes(u(P))?(c(),y(l,{key:0,type:"primary",title:u(t)("action.save"),loading:u(q),onClick:a[1]||(a[1]=e=>K())},null,8,["title","loading"])):f("",!0),n(l,{loading:u(q),title:u(t)("dialog.close"),onClick:a[2]||(a[2]=e=>b.value=!1)},null,8,["loading","title"])])),default:m((()=>[["create","update"].includes(u(P))?(c(),y(E,{key:0,schema:u(M).formSchema,rules:u(j),ref_key:"formRef",ref:z},null,8,["schema","rules"])):f("",!0),"detail"===u(P)?(c(),y(D,{key:1,schema:u(M).detailSchema,data:u(A)},null,8,["schema","data"])):f("",!0)])),_:1},8,["modelValue","title"])],64)}}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/system/errorCode/index.vue"]]);export{q as default};