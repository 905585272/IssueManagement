import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    // 定义状态
    state: {
        username: '',
        enterable: true,
        entersuccess: false,
        registerable: false,
        createissue: false,
        issuereport: false,
    }
})

export default store