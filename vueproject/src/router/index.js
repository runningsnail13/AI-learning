import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 解决导航栏或者底部导航tabBar中的vue-router在3.0版本以上频繁点击菜单报错的问题。
const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (location) {
  return originalPush.call(this, location).catch(err => err)
}

const routes = [
  {
    path: '/',
    name: 'Manager',
    component: () => import('../views/Manager.vue'),
    //在这个路由配置中，当用户访问根路径（'/'）时，由于设置了redirect: '/home'，所以会立刻重定向到'/home'路径。
    // 然而，'/home'实际上是根路径下的一个子路由，这意味着它仍然是'/'路径下的一部分。
    redirect: '/home',  // 重定向到主页
    children: [
      { path: '403', name: 'NoAuth', meta: { name: '无权限' }, component: () => import('../views/manager/403') },
      { path: 'home', name: 'Home', meta: { name: '系统首页' }, component: () => import('../views/manager/Home') },
      { path: 'admin', name: 'Admin', meta: { name: '管理员信息' }, component: () => import('../views/manager/Admin') },
      { path: 'adminPerson', name: 'AdminPerson', meta: { name: '个人信息' }, component: () => import('../views/manager/AdminPerson') },
      { path: 'password', name: 'Password', meta: { name: '修改密码' }, component: () => import('../views/manager/Password') },
      { path: 'notice', name: 'Notice', meta: { name: '公告信息' }, component: () => import('../views/manager/Notice') },
      { path: 'user', name: 'User', meta: { name: '用户信息' }, component: () => import('../views/manager/User') },
      { path: 'category', name: 'Category', meta: { name: '帖子分类' }, component: () => import('../views/manager/Category') },
      { path: 'blog', name: 'Blog', meta: { name: '帖子信息' }, component: () => import('../views/manager/Blog.vue') },
      { path: 'activity', name: 'Activity', meta: { name: '赛事信息' }, component: () => import('../views/manager/Activity.vue') },
      { path: 'comment', name: 'Comment', meta: { name: '评论信息' }, component: () => import('../views/manager/Comment.vue') },
      { path: 'activitySign', name: 'ActivitySign', meta: { name: '报名信息' }, component: () => import('../views/manager/ActivitySign.vue') },
    ]
  },
  {
    path: '/front',
    name: 'Front',
    component: () => import('../views/Front.vue'),
    children: [
      { path: 'home', name: 'FHome', meta: { name: '系统首页' }, component: () => import('../views/front/Home') },
      { path: 'person', name: 'Person', meta: { name: '个人信息' }, component: () => import('../views/front/Person') },
      { path: 'blogDetail', name: 'BlogDetail', meta: { name: '详情页面' }, component: () => import('../views/front/BlogDetail.vue') },
      { path: 'search', name: 'Search', meta: { name: '博客搜索' }, component: () => import('../views/front/Search.vue') },
      { path: 'activity', name: 'FActivity', meta: { name: '赛事中心' }, component: () => import('../views/front/Activity.vue') },
      { path: 'activityDetail', name: 'ActivityDetail', meta: { name: '赛事详情' }, component: () => import('../views/front/ActivityDetail.vue') },
    ]
  },
  { path: '/login', name: 'Login', meta: { name: '登录' }, component: () => import('../views/Login.vue') },
  { path: '/register', name: 'Register', meta: { name: '注册' }, component: () => import('../views/Register.vue') },
  { path: '*', name: 'NotFound', meta: { name: '无法访问' }, component: () => import('../views/404.vue') },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 注：不需要前台的项目，可以注释掉该路由守卫
// 路由守卫
// router.beforeEach((to ,from, next) => {
//   let user = JSON.parse(localStorage.getItem("xm-user") || '{}');
//   if (to.path === '/') {
//     if (user.role) {
//       if (user.role === 'USER') {
//         next('/front/home')
//       } else {
//         next('/home')
//       }
//     } else {
//       next('/login')
//     }
//   } else {
//     next()
//   }
// })

export default router
