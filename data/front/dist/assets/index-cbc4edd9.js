import{r as a}from"./index-4bfb878d.js";const s=async s=>await a.get({url:"/admin-api/mes/md/workstation/list",params:s}),i=async()=>await a.get({url:"/admin-api/mes/md/workshop/listAll"}),t=async()=>await a.get({url:"/admin-api/mes/pro/process/listAll"}),m=async s=>await a.post({url:"/admin-api/mes/md/workstation/save",data:s}),n=async s=>await a.put({url:"/admin-api/mes/md/workstation/update",data:s}),e=async s=>await a.get({url:`/admin-api/mes/md/workstation/get/${s}`}),d=async s=>await a.get({url:"/admin-api/mes/md/workstationMachine/list",params:s}),r=async s=>await a.post({url:"/admin-api/mes/md/workstationMachine/save",data:s}),o=async s=>await a.delete({url:"/admin-api/mes/md/workstationMachine/batch",data:s}),p=async()=>await a.get({url:"/admin-api/mes/dv/machineryType/list"}),l=async s=>await a.get({url:"/admin-api/mes/dv/machinery/list",params:s}),w=async s=>await a.delete({url:"/admin-api/mes/md/workstation/batch",data:s}),c=async()=>await a.download({url:"/admin-api/mes/md/workstation/export"});export{l as a,r as b,d as c,o as d,c as e,w as f,e as g,m as h,s as i,i as j,t as k,p as q,n as u};