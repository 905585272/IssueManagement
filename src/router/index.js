import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import enterPage from "../components/enterPage.vue";
import registerPage from "../components/registerPage.vue";
import axios from 'axios';
Vue.prototype.$axios = axios;

Vue.use(VueRouter);

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

];

const router = new VueRouter({
    routes
});

export default router;