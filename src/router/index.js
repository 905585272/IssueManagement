import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import vueResource from 'vue-resource';
import enterPage from "../components/enterPage.vue";
import registerPage from "../components/registerPage.vue";
import changePage from '../components/changePage.vue';
import createIssue from '../components/createIssue.vue';
import issueReport from '../components/issueReport.vue';
import changeIssue from '../components/changeIssue.vue';
import viewIssue from '../components/viewIssue.vue';
import issueList from '../components/issueList.vue';
import axios from 'axios';
Vue.prototype.$axios = axios;

Vue.use(VueRouter);

Vue.use(vueResource);

const routes = [{
        path: "/",
        name: "Home",
        component: Home
    },
    {
        path: '/enterpage',
        name: 'enterPage',
        component: enterPage
    },
    {
        path: '/registerpage',
        name: 'registerPage',
        component: registerPage
    },
    {
        path: '/changepage',
        name: 'changePage',
        component: changePage
    },
    {
        path: '/createissue',
        name: 'createIssue',
        component: createIssue
    },
    {
        path: '/issuereport',
        name: 'issueReport',
        component: issueReport
    },
    {
        path: '/changeissue',
        name: 'changeIssue',
        component: changeIssue
    },
    {
        path: '/viewIssue',
        name: 'viewIssue',
        component: viewIssue
    },
    {
        path: '/issuelist',
        name: 'issueList',
        component: issueList
    },

];

const router = new VueRouter({
    routes
});

export default router;