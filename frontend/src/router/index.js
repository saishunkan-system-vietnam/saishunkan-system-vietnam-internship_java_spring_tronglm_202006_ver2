import Vue from 'vue'
import Vuelidate from 'vuelidate'
import VueRouter from 'vue-router'
import Login from "../views/Login.vue"
import Regist from "../views/Regist.vue"
import Team from "../views/Team.vue"
import Tournament from "../views/Tournament.vue"
import Member from "../views/Member.vue"
import AdminAccount from "../views/AdminAccount.vue"
import AccountUser from "../views/AccountUser.vue"
import TeamDuel from "../views/TeamDuel.vue"
import LayoutAdmin from "../components/masters/LayoutAdmin"
import LayoutUsers from "../components/masters/LayoutUsers"
import store from "../store/index"
import { callApi } from "../Api/callApi"

Vue.use(Vuelidate)

Vue.use(VueRouter)

const routes = [
  {
    path: '/admin',
    component: LayoutAdmin,
    children: [
      {
        path: "",
        name: "AdminAccount",
        component: AdminAccount
      },
      {
        path: 'team',
        component: Team,
        name: "Team",
      },
      {
        path: 'team-duel/:id_team',
        component: TeamDuel,
        name: "TeamDuel",
        props: true
      },
      {
        path: 'tournament',
        component: Tournament,
        name: "Tournament",
      },
      {
        path: 'member/:id_team',
        component: Member,
        name: "Member",
        props: true
      }  
    ]
  },

  {
    path: '/',
    component: LayoutUsers,
    children: [
      {
        path: '',
        component: AccountUser,
        name: "AccountUser",
        props: true,
      }
    ]
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/regist",
    name: "Regist",
    component: Regist
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach(async (to, from, next) => {
  let response;
  if (store.state.userlogined == null) {
    response = await callApi("GET", "/account/getsession")
    if (!response.data || response.data.code != "0000") {
      if (to.path == '/login' || to.path == '/regist')
        return next()
      return next({ path: '/login' })
    }
    store.commit('increment', response.data.payload);
    if (response.data.payload.role_name == 'admin') {
      if (to.path == '/login' || to.path == '/regist')
        return next({ path: '/admin' });
      return next();
    }
    if (response.data.payload.role_name == 'user') {
      if (to.path == '/login' || to.path.includes('/admin') || to.path == '/regist')
        return next({ path: '/' });
      return next();
    }
  }
  else {
    if (store.state.userlogined.role_name == 'admin') {  
      if(to.path == '/login' || to.path == '/regist') {
        return next({ path: '/admin' });
      }
      return next();
    }
    if (store.state.userlogined.role_name == 'user') {  
      if(to.path == '/login' || to.path.includes('/admin') || to.path == '/regist') {
        return next({ path: '/' });
      }
      return next();
    }
  }
})
export default router
