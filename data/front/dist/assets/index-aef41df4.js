import{c as e}from"./index-6c8a85ac.js";import{d as a}from"./data-6c43379c.js";import{d as r}from"./pmOrder-563fbf59.js";import{P as o}from"./const-f443e30b.js";import{d as s,e as t,f as n,g as i,o as d,G as p,w as m,X as l,t as u,a as g,_ as c}from"./index-4bfb878d.js";const f=c(s({__name:"index",props:{workOrderId:{type:[Number,String],required:!0}},setup(s){const c=s,f=t([]),_=t(!1),v=n({total:0,pageSize:10,pageSizes:[5,10,15,20],currentPage:1}),w=()=>{(()=>{_.value=!0;const e={pageNo:v.currentPage,pageSize:v.pageSize,workorderId:c.workOrderId};r(e).then((e=>{f.value=e})).finally((()=>{_.value=!1}))})()};return i((()=>{w()})),(r,s)=>(d(),p(e,{loading:_.value,columns:g(a),tableData:f.value,pagination:g(v)},{itemOrProduct:m((({scope:e})=>[l(u(g(o)[e.row.itemOrProduct]),1)])),_:1},8,["loading","columns","tableData","pagination"]))}}),[["__file","/Users/alex/Downloads/万界星空/产品研发/MES框架/dev/xiejinrun/new_open_mes_front/src/views/prodMgmt/pmOrder/components/materialModel/index.vue"]]);export{f as default};