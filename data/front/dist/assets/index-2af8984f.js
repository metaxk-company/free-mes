import{r as a}from"./index-4bfb878d.js";const s=async s=>await a.get({url:"/admin-api/system/post/page",params:s}),t=async()=>await a.get({url:"/admin-api/system/post/list-all-simple"}),i=async s=>await a.get({url:"/admin-api/system/post/get?id="+s}),e=async s=>await a.post({url:"/admin-api/system/post/create",data:s}),p=async s=>await a.put({url:"/admin-api/system/post/update",data:s}),m=async s=>await a.delete({url:"/admin-api/system/post/delete?id="+s}),d=async s=>await a.download({url:"/admin-api/system/post/export",params:s});export{s as a,e as c,m as d,d as e,i as g,t as l,p as u};