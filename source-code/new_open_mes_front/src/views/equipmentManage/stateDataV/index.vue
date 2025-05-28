<template>
  <div className="screen-wrapper">
    <div className="screen" id="screen">
      <div className="demo-root">
        <header>头部</header>
        <main>
          <div className="demo-left">123</div>
          <div className="demo-center">123</div>
          <div className="demo-right">123</div>
        </main>
        <footer>底部</footer>
      </div>
    </div>
    <footer className="large-footer"></footer>
  </div>
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
const handleScreenAuto = (): void => {
  const designDraftWidth = 1920 //设计稿的宽度
  const designDraftHeight = 960 //设计稿的高度
  //根据屏幕的变化适配的比例
  const scale =
    document.documentElement.clientWidth / document.documentElement.clientHeight <
    designDraftWidth / designDraftHeight
      ? document.documentElement.clientWidth / designDraftWidth
      : document.documentElement.clientHeight / designDraftHeight
  //缩放比例
  ;(document.querySelector('#screen') as any).style.transform = `scale(${scale}) translate(-50%)`
}

onMounted(() => {
  handleScreenAuto()
})
</script>

<style lang="scss" scoped>
/*
css
*/
.screen-wrapper {
  height: 100%;
  width: 100%;
  .screen {
    display: inline-block;
    width: 1920px;
    height: 960px;
    transform-origin: 0 0;
    position: absolute;
    left: 50%;
    .demo-root {
      header {
        width: 1920px;
        height: 200px;
        background: rgba(53, 150, 206, 0.3);
        font-size: 40px;
        text-align: center;
        line-height: 200px;
      }
      main {
        display: flex;
        height: 660px;
        div {
          width: 640px;
          height: 100%;
        }
        .demo-left {
          background: rgba(206, 52, 154, 0.3);
        }
        .demo-center {
          background: rgba(13, 30, 179, 0.3);
        }
        .demo-right {
          background: rgba(64, 163, 6, 0.849);
        }
      }
      footer {
        width: 100%;
        height: 100px;
        font-size: 40px;
        text-align: center;
        line-height: 100px;
        background: rgba(19, 211, 115, 0.3);
      }
    }
  }
}
</style>
