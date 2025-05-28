<template>
  <ContentWrap>
    <div class="cards">
      <div
        :class="['card', item.tips]"
        @click="handleRouter(item.url)"
        v-for="(item, index) in routerList"
        :key="index"
      >
        <p class="tip">{{ item.label }}</p>
        <p class="second-text">{{ item.enLabel }}</p>
      </div>
    </div>
  </ContentWrap>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
const router = useRouter()

const routerList = [
  {
    url: '/prodMgmt/reportWorkTwo',
    label: '生产报工2',
    enLabel: 'Production report',
    tips: 'red'
  },
  {
    url: '/prodMgmt/reportWork',
    label: '报工设备',
    enLabel: 'Reporting equipment',
    tips: 'blue'
  },
  {
    url: '/prodMgmt/workHours',
    label: '工时管理',
    enLabel: 'Working hours management',
    tips: 'green'
  }
]

const handleRouter = (value) => {
  router.push(value)
}
</script>

<style scoped lang="scss">
.cards {
  display: flex;
  // flex-direction: column;
  justify-content: space-around;
  gap: 15px;
}

.cards .red {
  background-color: #f43f5e;
}

.cards .blue {
  background-color: #3b82f6;
}

.cards .green {
  background-color: #22c55e;
}

.cards .card {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  text-align: center;
  height: 100px;
  width: 250px;
  border-radius: 10px;
  color: white;
  cursor: pointer;
  transition: 400ms;
}

.cards .card p.tip {
  font-size: 2em;
  font-weight: 700;
}

.cards .card p.second-text {
  font-size: 0.7em;
}

.cards .card:hover {
  transform: scale(1.1, 1.1);
  // filter: blur(10px);
}

.cards:hover > .card:not(:hover) {
  filter: blur(10px);
  transform: scale(0.9, 0.9);
}
</style>
