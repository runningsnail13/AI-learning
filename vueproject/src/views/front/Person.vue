<template>
  <div class="main-content" style="width: 50%">

      <el-tabs v-model="activeName" @tab-click="clickTab">
          <el-tab-pane label="个人资料" name="个人资料"><person-page /></el-tab-pane>
          <el-tab-pane label="我的文章" name="我的文章">
              <div class="card" style="padding: 5px">
                  <el-button type="primary" @click="addBlog">写文章</el-button>
              </div>
              <div style="margin-top: 10px">
                  <blog-list :type="type" :showOpt="true"/>
              </div>
          </el-tab-pane>
          <el-tab-pane label="已报名的活动" name="已报名的活动">
             <div class="card"> <activity-list :type="type" :span="8"/></div>
          </el-tab-pane>

          <el-tab-pane label="我的点赞" name="我的点赞">
              <div class="card" style="padding: 5px;display: flex">
                  <div class="category-btn" :class="{'active': likesCurrent === '博客'}" @click="likesCurrent = '博客'">帖子</div>
                  <div class="category-btn" :class="{'active': likesCurrent === '赛事'}" @click="likesCurrent = '赛事'">赛事</div>
              </div>
              <div style="margin-top: 10px">
                  <blog-list v-if="likesCurrent === '博客'" type="like" />
                  <activity-list :span="8"  v-if="likesCurrent === '赛事'" type="like" />
              </div>
          </el-tab-pane>
          <el-tab-pane label="我的收藏" name="我的收藏">
              <div class="card" style="padding: 5px;display: flex">
                  <div class="category-btn" :class="{'active': collectCurrent === '博客'}" @click="collectCurrent = '博客'">帖子</div>
                  <div class="category-btn" :class="{'active': collectCurrent === '赛事'}" @click="collectCurrent = '赛事'">赛事</div>
              </div>
              <div style="margin-top: 10px">
                  <blog-list v-if="collectCurrent === '博客'" type="collect" />
                  <activity-list v-if="collectCurrent === '赛事'" :span="8" type="collect"/>
              </div>
          </el-tab-pane>
          <el-tab-pane label="我的评论" name="我的评论">
              <div class="card" style="padding: 5px;display: flex">
                  <div class="category-btn" :class="{'active': commentCurrent === '博客'}" @click="commentCurrent = '博客'">帖子</div>
                  <div class="category-btn" :class="{'active': commentCurrent === '赛事'}" @click="commentCurrent = '赛事'">赛事</div>
              </div>
              <div style="margin-top: 10px">
                  <blog-list v-if="commentCurrent === '博客'" type="comment" />
                  <activity-list v-if="commentCurrent === '赛事'" :span="8" type="like"/>
              </div>
          </el-tab-pane>
      </el-tabs>

      <Footer />
  </div>
</template>

<script>
import Footer from "@/components/Footer.vue";
import PersonPage from "@/components/PersonPage.vue";
import BlogList from "@/components/BlogList.vue";
import ActivityList from "@/components/ActivityList.vue";

export default {
  components:{
      Footer,
      PersonPage,
      BlogList,
      ActivityList,
  },
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请确认密码'))
      } else if (value !== this.user.newPassword) {
        callback(new Error('确认密码错误'))
      } else {
        callback()
      }
    }
    return {
      user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
      dialogVisible: false,
      activeName:'个人资料',
      type:'user',
      likesCurrent:'博客',
      collectCurrent:'博客',
      commentCurrent:'博客',
      rules: {
        password: [
          { required: true, message: '请输入原始密码', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
        ],
        confirmPassword: [
          { validator: validatePassword, required: true, trigger: 'blur' },
        ],
      }
    }
  },
  created() {

  },
  methods: {
    addBlog(){
        window.open('/front/newBlog')
    },
    clickTab(tab){
        // console.log(tab)
    },
    update() {
      // 保存当前的用户信息到数据库
      this.$request.put('/user/update', this.user).then(res => {
        if (res.code === '200') {
          // 成功更新
          this.$message.success('保存成功')
          // 更新浏览器缓存里的用户信息
          localStorage.setItem('xm-user', JSON.stringify(this.user))

          // 触发父级的数据更新
          this.$emit('update:user')
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把user的头像属性换成上传的图片的链接
      this.$set(this.user, 'avatar', response.data)
    },
    // 修改密码
    updatePassword() {
      this.dialogVisible = true
    },
    save() {
      this.$refs.formRef.validate((valid) => {
        if (valid) {
          this.$request.put('/updatePassword', this.user).then(res => {
            if (res.code === '200') {
              // 成功更新
              this.$message.success('修改密码成功')
              this.$router.push('/login')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
}
.category-btn{
    width: fit-content;
    padding: 5px 10px;
    border-radius: 5px;
    cursor: pointer;
}
.active{
    background-color:#2a60c9;
    color: white !important;
}
</style>