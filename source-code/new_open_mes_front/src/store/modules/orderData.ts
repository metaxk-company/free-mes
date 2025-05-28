import { store } from '../index'
import { defineStore } from 'pinia'

export const orderNumStore = defineStore('order-num-store', {
  state: () => ({
    orderCount: 0
  }),
  getters: {},

  actions: {}
})

export const useOrderNumStore = () => {
  return orderNumStore(store)
}
