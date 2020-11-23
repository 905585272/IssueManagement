import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    // 定义状态
    state: {
        enterable: true,
        entersuccess: false,
        changeable: false,
        registerable: true,
        createissue: false,
        issuereport: false,
        changeissue: false,
        issuelist: false,
        iconable: false,
        quitable: false,
        // 全局用户数据
        rId: '',
        rName: '',
        rEmail: '',
        rPwd: '',
        rUserid: '',
        rState: '',
        rCissue: 0,
        rRissue: 0,
        rMissue: 0,
        // 全局报表信息
        iCreator: '',
        iTitle: '',
        iNo: '',
        iCdate: '',
        iType: '',
        iLevel: '',
        iVesion: '',
        iPlantime: '',
        iFinishtime: '',
        iReappear: '',
        iChangeperson: '',
        iHandlemethod: '',
        iIssuestate: '',
    }
})

export default store