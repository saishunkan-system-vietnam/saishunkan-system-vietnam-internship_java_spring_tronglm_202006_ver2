import Vue from 'vue'
import Vuelidate from 'vuelidate'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from "../views/Login.vue"
import Regist from "../views/Regist.vue"
import AdminHome from "../views/AdminHome.vue"
import AdminAccount from "../views/AdminAccount.vue"
import AccountUser from "../views/AccountUser.vue"
import store from "../store/index"
import { callApi } from "../Api/callApi"

Vue.use(Vuelidate)

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
    beforeEnter: (to, from, next) => {
      console.log("Enter")
      next()
    //   if(store.state.userlogined != null) next({name: "AdminHome"})
    //  // console.log(store.state.userlogined, "beforeEnter")
    //  else next()
    }
  },
  {
    path: "/home/admin",
    name: "AdminHome",
    component: AdminHome
  },
  {
    path: "/home/accountUser/:id",
    name: "AccountUser",
    props: true,
    component: AccountUser,
  },
  {
    path: "/home/admin/account",
    name: "AdminAccount",
    component: AdminAccount
  },
  {
    path: "/regist",
    name: "Regist",
    component: Regist
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

 router.beforeEach(async (to, from, next) =>  {
  let response;
   if(store.state.userlogined == null ){
    response = await callApi("GET", "/account/getsession")
    if(response.status){
      if(to.name == 'Login'){
      next({ name: 'AdminHome' });
      } else{
        next();
      }
    }
    else {
      if(to.name == 'Login'){
        console.log(22)
        next()
      } else{
        next({name: 'Login' })
      }
    }
  }
  else {
    if(to.name == 'Login'){
      next({ name: 'AdminHome' })
    } else{
      next();
    }
  }
})

export default router
