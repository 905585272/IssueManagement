import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    // 定义状态
    state: {
        username: '',
        enterable: true,
        entersuccess: false,
        changeable: false,
        registerable: true,
        createissue: false,
        issuereport: false,
        changeissue: false,
    }
})

export default store