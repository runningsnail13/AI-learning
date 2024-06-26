<template>
    <div style="width: 100%;margin: 5px auto">
        <div class="card" style="margin-bottom: 100px">
            <div style="font-weight: bold;font-size: 24px;margin-bottom: 50px">发表/编辑</div>
            <el-form :model="form" label-width="100px" style="padding-right: 50px" :rules="rules" ref="formRef">
                <el-form-item label="标题" prop="title">
                    <el-input v-model="form.title" placeholder="标题"></el-input>
                </el-form-item>
                <el-form-item label="简介" prop="descr">
                    <el-input type="textarea" v-model="form.descr" placeholder="简介"></el-input>
                </el-form-item>
                <el-form-item label="封面" prop="cover">
                    <el-upload
                            :action="$baseUrl + '/files/upload'"
                            :headers="{ token: user.token }"
                            list-type="picture"
                            :on-success="handleCoverSuccess"
                    >
                        <el-button type="primary">上传封面</el-button>
                    </el-upload>
                </el-form-item>
                <el-form-item label="分类" prop="categoryId">
                    <el-select v-model="form.categoryId" style="width: 100%">
                        <el-option v-for="item in categoryList" :key="item.id" :value="item.id"
                                   :label="item.name"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="标签" prop="tags">
                    <el-select v-model="tagsArr" multiple filterable allow-create default-first-option
                               style="width: 100%">
                        <el-option value="Python"></el-option>
                        <el-option value="Java"></el-option>
                        <el-option value="C/C++"></el-option>
                        <el-option value="求助贴"></el-option>
                        <el-option value="经验帖"></el-option>
                        <el-option value="互动贴"></el-option>
                        <el-option value="笔记"></el-option>
                        <el-option value="初中组"></el-option>
                        <el-option value="高中组"></el-option>
                        <el-option value="大学组"></el-option>
                        <el-option value="大神文章"></el-option>
                        <el-option value="小白入门"></el-option>
                        <el-option value="NOIP"></el-option>
                        <el-option value="ICPC"></el-option>
                        <el-option value="蓝桥杯"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="内容" prop="content">
                    <div id="editor"></div>
                </el-form-item>
            </el-form>
            <div style="text-align: center">
                <el-button type="primary" size="medium" style="width: 100px" @click="save">保 存</el-button>
                <el-button size="medium" style="width: 100px" @click="unDo">取 消</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import E from "wangeditor";
import hljs from "highlight.js";

export default {
    name: "NewBlog",
    data() {
        return {
            form: {},
            user: JSON.parse(localStorage.getItem('xm-user') || '{}'),
            rules: {
                title: [
                    {required: true, message: '请输入标题', trigger: 'blur'},
                ],
                categoryId: [
                    {required: true, message: '请选择分类', trigger: 'blur'},
                ],
            },
            tagsArr: [],
            categoryList: [],
            editor: null,
            blogId: this.$route.query.blogId
        }
    },
    mounted() {
        this.$request.get('/category/selectAll').then(res => {
            this.categoryList = res.data || []
        })
        this.$request.get('/blog/selectById/' + this.blogId).then(res => {
            this.form = res.data || {}
            if (this.form.id) {
                this.tagsArr = JSON.parse(this.form.tags || '[]')
                setTimeout(() => {
                    this.editor.txt.html(this.form.content)
                }, 0)
            }
        })
        this.setRichText()

    },
    methods: {
        unDo() {
            this.$router.push('person')
        },
        save() {   // 保存按钮触发的逻辑  它会触发新增或者更新
            this.$refs.formRef.validate((valid) => {
                if (valid) {
                    this.form.tags = JSON.stringify(this.tagsArr)  // 把json数组转换成json字符串存到数据库
                    this.form.content = this.editor.txt.html()
                    this.$request({
                        url: this.form.id ? '/blog/update' : '/blog/add',
                        method: this.form.id ? 'PUT' : 'POST',
                        data: this.form
                    }).then(res => {
                        if (res.code === '200') {  // 表示成功保存
                            this.$message.success('保存成功')
                        } else {
                            this.$message.error(res.msg)  // 弹出错误的信息
                        }
                    })
                }
            })
        },
        handleCoverSuccess(res) {
            this.form.cover = res.data
        },
        setRichText() {
            this.$nextTick(() => {
                this.editor = new E(`#editor`)
                this.editor.highlight = hljs

                // 设置图片上传的配置
                this.editor.config.uploadImgServer = this.$baseUrl + '/files/editor/upload'
                this.editor.config.uploadFileName = 'file'
                this.editor.config.uploadImgHeaders = {
                    token: this.user.token
                }
                this.editor.config.zIndex = 0;
                this.editor.config.uploadImgParams = {
                    type: 'img',
                }

                // 设置视频上传的配置
                this.editor.config.uploadVideoServer = this.$baseUrl + '/files/editor/uploadVideo' // 你处理视频上传的服务器接口
                this.editor.config.uploadVideoName = 'file' // 服务端接收的文件参数名
                // 可以设置视频相关的headers和params，根据你的需要来配置
                this.editor.config.uploadVideoHeaders = {
                    token: this.user.token
                }
                // 上传视频附带的参数，可以根据需要添加
                this.editor.config.uploadVideoParams = {
                    type: 'video'
                }
                this.editor.config.uploadVideoHooks = {
                    success: function (xhr, editor, result) {
                        console.log(result)
                        // 处理视频上传成功的回调
                    },
                    fail: function (xhr, editor, resData) {
                        alert(resData.message)
                        // 处理视频上传失败的回调
                    },
                }
                // 最后创建编辑器
                this.editor.create()
            })
        }
    }
}
</script>

<style scoped>

</style>