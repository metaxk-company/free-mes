import{r as e,d as a,q as l,K as t,e as s,cm as n,o,c as u,k as d,w as r,X as i,t as m,a as f,j as v,I as p,G as c,D as _,J as y,z as h,F as b,cn as w,co as k,l as g,v as x,E as T,b7 as S,ap as I,b8 as R,a9 as C,M as E,a0 as F,aI as j,x as K,y as O,_ as D}from"./index-4bfb878d.js";import"./index-eba8b5f4.js";import{ew as U}from"./install-0b998475.js";const V=e=>(K("data-v-5a94aa7c"),e=e(),O(),e),M={key:0},z=V((()=>v("div",{class:"card-header"},[v("span",null,"键名列表")],-1))),A={class:"card-header"},B=V((()=>v("span",null,"缓存内容",-1))),P=a({name:"Redis"}),X=D(a({...P,setup(a){const{t:K}=l(),O=t(),D=s(),V=s(!0),P=s([]),X=async()=>{const a=await e.get({url:"/admin-api/infra/redis/get-monitor-info"});D.value=a,J(a.commandStats);const l=await e.get({url:"/admin-api/infra/redis/get-key-define-list"});P.value=l,V.value=!1},q=s(),G=s(),J=e=>{const a=[],l=[];e.forEach((e=>{a.push({name:e.command,value:e.calls}),l.push(e.command)}));U(q.value,"macarons").setOption({title:{text:"命令统计",left:"center"},tooltip:{trigger:"item",formatter:"{a} <br/>{b} : {c} ({d}%)"},legend:{type:"scroll",orient:"vertical",right:30,top:10,bottom:20,data:l,textStyle:{color:"#a1a1a1"}},series:[{name:"命令",type:"pie",radius:[20,120],center:["40%","60%"],data:a,roseType:"radius",label:{show:!0},emphasis:{label:{show:!0},itemStyle:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}]});U(G.value,"macarons").setOption({title:{text:"内存使用情况",left:"center"},tooltip:{formatter:"{b} <br/>{a} : "+D.value.info.used_memory_human},series:[{name:"峰值",type:"gauge",min:0,max:100,progress:{show:!0},detail:{formatter:D.value.info.used_memory_human},data:[{value:parseFloat(D.value.info.used_memory_human),name:"内存消耗"}]}]})},L=s(!1),N=s(""),Y=s(),H=s({key:"",value:""}),Q=async a=>{N.value=a.keyTemplate,Y.value=await(a=>e.get({url:"/admin-api/infra/redis/get-key-list",params:{keyTemplate:a}}))(a.keyTemplate),L.value=!0},W=async a=>{var l;l=a,e.delete({url:"/admin-api/infra/redis/delete-key?key="+l}),O.success(K("common.delSuccess"))},Z=async a=>{(a=>{e.delete({url:"/admin-api/infra/redis/delete-keys?",params:{keyTemplate:a}})})(a),O.success(K("common.delSuccess"))},$=async a=>{const l=await(t=a,e.get({url:"/admin-api/infra/redis/get-key-value?key="+t}));var t;H.value=l};return n((()=>{X()})),(e,a)=>{const l=w,t=k,s=g,n=x,K=T,O=S,U=I,X=R,J=C,ee=E,ae=F,le=j;return o(),u(b,null,[d(J,{height:"calc(100vh - 88px - 40px - 50px)"},{default:r((()=>[d(K,null,{default:r((()=>[d(n,{span:24,class:"card-box",shadow:"hover"},{default:r((()=>[d(s,null,{default:r((()=>[d(t,{title:"基本信息",column:6,border:""},{default:r((()=>[d(l,{label:"Redis版本 :"},{default:r((()=>{var e,a;return[i(m(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.redis_version),1)]})),_:1}),d(l,{label:"运行模式 :"},{default:r((()=>{var e,a;return[i(m("standalone"==(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.redis_mode)?"单机":"集群"),1)]})),_:1}),d(l,{label:"端口 :"},{default:r((()=>{var e,a;return[i(m(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.tcp_port),1)]})),_:1}),d(l,{label:"客户端数 :"},{default:r((()=>{var e,a;return[i(m(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.connected_clients),1)]})),_:1}),d(l,{label:"运行时间(天) :"},{default:r((()=>{var e,a;return[i(m(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.uptime_in_days),1)]})),_:1}),d(l,{label:"使用内存 :"},{default:r((()=>{var e,a;return[i(m(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.used_memory_human),1)]})),_:1}),d(l,{label:"使用CPU :"},{default:r((()=>{var e,a,l;return[i(m((null==(e=f(D))?void 0:e.info)?parseFloat(null==(l=null==(a=f(D))?void 0:a.info)?void 0:l.used_cpu_user_children).toFixed(2):""),1)]})),_:1}),d(l,{label:"内存配置 :"},{default:r((()=>{var e,a;return[i(m(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.maxmemory_human),1)]})),_:1}),d(l,{label:"AOF是否开启 :"},{default:r((()=>{var e,a;return[i(m("0"==(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.aof_enabled)?"否":"是"),1)]})),_:1}),d(l,{label:"RDB是否成功 :"},{default:r((()=>{var e,a;return[i(m(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.rdb_last_bgsave_status),1)]})),_:1}),d(l,{label:"Key数量 :"},{default:r((()=>{var e;return[i(m(null==(e=f(D))?void 0:e.dbSize),1)]})),_:1}),d(l,{label:"网络入口/出口 :"},{default:r((()=>{var e,a,l,t;return[i(m(null==(a=null==(e=f(D))?void 0:e.info)?void 0:a.instantaneous_input_kbps)+"kps/ "+m(null==(t=null==(l=f(D))?void 0:l.info)?void 0:t.instantaneous_output_kbps)+"kps ",1)]})),_:1})])),_:1})])),_:1})])),_:1}),d(n,{span:12,class:"mt-3"},{default:r((()=>[d(s,{gutter:12,shadow:"hover"},{default:r((()=>[v("div",{ref_key:"commandStatsRef",ref:q,class:"h-88"},null,512)])),_:1})])),_:1}),d(n,{span:12,class:"mt-3"},{default:r((()=>[d(s,{class:"ml-3",gutter:12,shadow:"hover"},{default:r((()=>[v("div",{ref_key:"usedmemory",ref:G,class:"h-88"},null,512)])),_:1})])),_:1})])),_:1}),d(K,{class:"mt-3"},{default:r((()=>[d(n,{span:24,class:"card-box",shadow:"hover"},{default:r((()=>[d(s,null,{default:r((()=>[p((o(),c(X,{data:f(P),"row-key":"id",onRowClick:Q},{default:r((()=>[d(O,{prop:"keyTemplate",label:"Key 模板",width:"200"}),d(O,{prop:"keyType",label:"Key 类型",width:"100"}),d(O,{prop:"valueType",label:"Value 类型"}),d(O,{prop:"timeoutType",label:"超时时间",width:"200"},{default:r((({row:e})=>[d(U,{type:f(_).INFRA_REDIS_TIMEOUT_TYPE,value:null==e?void 0:e.timeoutType},null,8,["type","value"]),(null==e?void 0:e.timeout)>0?(o(),u("span",M,"("+m((null==e?void 0:e.timeout)/1e3)+" 秒)",1)):y("",!0)])),_:1}),d(O,{prop:"memo",label:"备注"})])),_:1},8,["data"])),[[le,f(V)]])])),_:1})])),_:1})])),_:1})])),_:1}),d(ae,{modelValue:f(L),"onUpdate:modelValue":a[1]||(a[1]=e=>h(L)?L.value=e:null),title:f(N)+" 模板"},{default:r((()=>[d(K,null,{default:r((()=>[d(n,{span:14,class:"mt-3"},{default:r((()=>[d(s,{shadow:"always"},{header:r((()=>[z])),default:r((()=>[d(X,{data:f(Y),style:{width:"100%"},onRowClick:$},{default:r((()=>[d(O,{label:"缓存键名",align:"center","show-overflow-tooltip":!0},{default:r((({row:e})=>[i(m(e),1)])),_:1}),d(O,{label:"操作",align:"right",width:"60"},{default:r((({row:e})=>[d(ee,{preIcon:"ep:delete",onClick:a=>W(e)},null,8,["onClick"])])),_:1})])),_:1},8,["data"])])),_:1})])),_:1}),d(n,{span:10,class:"mt-3"},{default:r((()=>[d(s,{shadow:"always"},{header:r((()=>[v("div",A,[B,d(ee,{preIcon:"ep:refresh",title:"清理全部",onClick:a[0]||(a[0]=e=>Z(f(N))),class:"float-right p-1"})])])),default:r((()=>[d(t,{column:1},{default:r((()=>[d(l,{label:"缓存键名:"},{default:r((()=>[i(m(f(H).key),1)])),_:1}),d(l,{label:"缓存内容:"},{default:r((()=>[i(m(f(H).value),1)])),_:1})])),_:1})])),_:1})])),_:1})])),_:1})])),_:1},8,["modelValue","title"])],64)}}}),[["__scopeId","data-v-5a94aa7c"],["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/infra/redis/index.vue"]]);export{X as default};