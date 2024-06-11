import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/HomeView.vue'
import TestView from '../views/TestView.vue'
import MainView from '../views/Main.vue'
import LoginView from '../views/Login'
import TrackingRFIDView from '../views/TrackingRFID.vue'
import CreateRFIDView from '../views/CreateRFID.vue'
import ManageRFIDView from '../views/ManageRFID.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },

  
  {
    path: '/main',
    name: 'main',
    component: MainView,
    children:[
      {
        path: '/trackingRFID',
        name: 'trackingRFID',
        component: TrackingRFIDView
      },
     {
        path: '/createRFID',
        name: 'createRFID',
        component: CreateRFIDView
      },
    
      {
        path: '/manageRFID',
        name: 'manageRFID',
        component: ManageRFIDView
      }
    
    ]
  },


  {
    path: '/test',
    name: 'test',
    component: TestView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
