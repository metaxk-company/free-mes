import{r as e,q as t,cW as a,D as l,f as i,d as o,K as s,e as n,C as r,o as d,c,k as u,w as p,a as m,I as f,X as y,t as h,F as S,n as T,G as k,J as b,z as _,L as w,aq as v,M as C,N as V,H as x,aJ as g,a1 as I,a0 as P,_ as U}from"./index-4bfb878d.js";import{r as z}from"./formRules-eb36c657.js";import{u as G}from"./useVxeCrudSchemas-092b8963.js";import{u as A}from"./useXTable-309dffed.js";const F=t=>e.get({url:"/admin-api/system/oauth2-client/page",params:t}),O=t=>e.get({url:"/admin-api/system/oauth2-client/get?id="+t}),R=t=>e.delete({url:"/admin-api/system/oauth2-client/delete?id="+t}),{t:M}=t(),j=a(l.SYSTEM_OAUTH2_GRANT_TYPE),N=i({clientId:[z],secret:[z],name:[z],status:[z],accessTokenValiditySeconds:[z],refreshTokenValiditySeconds:[z],redirectUris:[z],authorizedGrantTypes:[z]}),D=i({primaryKey:"clientId",primaryType:null,action:!0,columns:[{title:"客户端端号",field:"clientId"},{title:"客户端密钥",field:"secret"},{title:"应用名",field:"name",isSearch:!0},{title:"应用图标",field:"logo",table:{cellRender:{name:"XImg"}},form:{component:"UploadImg"}},{title:M("common.status"),field:"status",dictType:l.COMMON_STATUS,dictClass:"number",isSearch:!0},{title:"访问令牌的有效期",field:"accessTokenValiditySeconds",form:{component:"InputNumber"},table:{slots:{default:"accessTokenValiditySeconds_default"}}},{title:"刷新令牌的有效期",field:"refreshTokenValiditySeconds",form:{component:"InputNumber"},table:{slots:{default:"refreshTokenValiditySeconds_default"}}},{title:"授权类型",field:"authorizedGrantTypes",table:{width:400,slots:{default:"authorizedGrantTypes_default"}},form:{component:"Select",componentProps:{options:j,multiple:!0,filterable:!0}}},{title:"授权范围",field:"scopes",isTable:!1,form:{component:"Select",componentProps:{options:[],multiple:!0,filterable:!0,allowCreate:!0,defaultFirstOption:!0}}},{title:"自动授权范围",field:"autoApproveScopes",isTable:!1,form:{component:"Select",componentProps:{options:[],multiple:!0,filterable:!0,allowCreate:!0,defaultFirstOption:!0}}},{title:"可重定向的 URI 地址",field:"redirectUris",isTable:!1,form:{component:"Select",componentProps:{options:[],multiple:!0,filterable:!0,allowCreate:!0,defaultFirstOption:!0}}},{title:"权限",field:"authorities",isTable:!1,form:{component:"Select",componentProps:{options:[],multiple:!0,filterable:!0,allowCreate:!0,defaultFirstOption:!0}}},{title:"资源",field:"resourceIds",isTable:!1,form:{component:"Select",componentProps:{options:[],multiple:!0,filterable:!0,allowCreate:!0,defaultFirstOption:!0}}},{title:"附加信息",field:"additionalInformation",isTable:!1,form:{component:"Input",componentProps:{type:"textarea",rows:4},colProps:{span:24}}},{title:M("common.createTime"),field:"createTime",formatter:"formatDate",isForm:!1}]}),{allSchemas:E}=G(D),q=o({name:"Client"}),X=U(o({...q,setup(a){const{t:l}=t(),i=s(),[o,{reload:U,deleteData:z}]=A({allSchemas:E,getListApi:F,deleteApi:R}),G=n(!1),M=n("edit"),j=n(""),D=n(!1),q=n(),X=n(),H=e=>{M.value=l("action."+e),j.value=e,G.value=!0},J=async()=>{var t;const a=null==(t=m(q))?void 0:t.getElFormRef();a&&a.validate((async t=>{var a;if(t){D.value=!0;try{const t=null==(a=m(q))?void 0:a.formModel;"create"===j.value?(await(t=>e.post({url:"/admin-api/system/oauth2-client/create",data:t}))(t),i.success(l("common.createSuccess"))):(await(t=>e.put({url:"/admin-api/system/oauth2-client/update",data:t}))(t),i.success(l("common.updateSuccess"))),G.value=!1}finally{D.value=!1,await U()}}}))};return(e,t)=>{const a=w,i=v,s=C,n=V,U=x,A=g,F=I,R=P,K=r("hasPermi");return d(),c(S,null,[u(U,null,{default:p((()=>[u(n,{onRegister:m(o)},{toolbar_buttons:p((()=>[f(u(a,{type:"primary",preIcon:"ep:zoom-in",title:m(l)("action.add"),onClick:t[0]||(t[0]=e=>{H("create")})},null,8,["title"]),[[K,["system:oauth2-client:create"]]])])),accessTokenValiditySeconds_default:p((({row:e})=>[y(h(e.accessTokenValiditySeconds+"秒"),1)])),refreshTokenValiditySeconds_default:p((({row:e})=>[y(h(e.refreshTokenValiditySeconds+"秒"),1)])),authorizedGrantTypes_default:p((({row:e})=>[(d(!0),c(S,null,T(e.authorizedGrantTypes,((e,t)=>(d(),k(i,{"disable-transitions":!0,key:t,index:t},{default:p((()=>[y(h(e),1)])),_:2},1032,["index"])))),128))])),actionbtns_default:p((({row:e})=>[f(u(s,{preIcon:"ep:edit",title:m(l)("action.edit"),onClick:t=>(async e=>{var t;H("update");const a=await O(e);null==(t=m(q))||t.setValues(a)})(e.id)},null,8,["title","onClick"]),[[K,["system:oauth2-client:update"]]]),f(u(s,{preIcon:"ep:view",title:m(l)("action.detail"),onClick:t=>(async e=>{H("detail");const t=await O(e);X.value=t})(e.id)},null,8,["title","onClick"]),[[K,["system:oauth2-client:query"]]]),f(u(s,{preIcon:"ep:delete",title:m(l)("action.del"),onClick:t=>m(z)(e.id)},null,8,["title","onClick"]),[[K,["system:oauth2-client:delete"]]])])),_:1},8,["onRegister"])])),_:1}),u(R,{id:"postModel",modelValue:m(G),"onUpdate:modelValue":t[3]||(t[3]=e=>_(G)?G.value=e:null),title:m(M)},{footer:p((()=>[["create","update"].includes(m(j))?(d(),k(a,{key:0,type:"primary",title:m(l)("action.save"),loading:m(D),onClick:t[1]||(t[1]=e=>J())},null,8,["title","loading"])):b("",!0),u(a,{loading:m(D),title:m(l)("dialog.close"),onClick:t[2]||(t[2]=e=>G.value=!1)},null,8,["loading","title"])])),default:p((()=>[["create","update"].includes(m(j))?(d(),k(A,{key:0,ref_key:"formRef",ref:q,schema:m(E).formSchema,rules:m(N)},null,8,["schema","rules"])):b("",!0),"detail"===m(j)?(d(),k(F,{key:1,schema:m(E).detailSchema,data:m(X)},{accessTokenValiditySeconds:p((({row:e})=>[y(h(e.accessTokenValiditySeconds+"秒"),1)])),refreshTokenValiditySeconds:p((({row:e})=>[y(h(e.refreshTokenValiditySeconds+"秒"),1)])),authorizedGrantTypes:p((({row:e})=>[(d(!0),c(S,null,T(e.authorizedGrantTypes,((e,t)=>(d(),k(i,{"disable-transitions":!0,key:t,index:t},{default:p((()=>[y(h(e),1)])),_:2},1032,["index"])))),128))])),scopes:p((({row:e})=>[(d(!0),c(S,null,T(e.scopes,((e,t)=>(d(),k(i,{"disable-transitions":!0,key:t,index:t},{default:p((()=>[y(h(e),1)])),_:2},1032,["index"])))),128))])),autoApproveScopes:p((({row:e})=>[(d(!0),c(S,null,T(e.autoApproveScopes,((e,t)=>(d(),k(i,{"disable-transitions":!0,key:t,index:t},{default:p((()=>[y(h(e),1)])),_:2},1032,["index"])))),128))])),redirectUris:p((({row:e})=>[(d(!0),c(S,null,T(e.redirectUris,((e,t)=>(d(),k(i,{"disable-transitions":!0,key:t,index:t},{default:p((()=>[y(h(e),1)])),_:2},1032,["index"])))),128))])),_:1},8,["schema","data"])):b("",!0)])),_:1},8,["modelValue","title"])],64)}}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/system/oauth2/client/index.vue"]]);export{X as default};