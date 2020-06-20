import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userlogined: null,
  },
  mutations: {
   increment (state, payload) {
     state.userlogined = payload;
    }
  },
  actions: {

  },
  modules: {
  }
})
